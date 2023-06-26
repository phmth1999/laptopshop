package com.phmth.laptopshop.controller.web;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.CartItem;
import com.phmth.laptopshop.dto.OrderDto;
import com.phmth.laptopshop.dto.request.OrderInfoRequest;
import com.phmth.laptopshop.service.IOrderDetailService;
import com.phmth.laptopshop.service.IOrderService;
import com.phmth.laptopshop.service.IShoppingCartService;
import com.phmth.laptopshop.service.IUserService;
import com.phmth.laptopshop.utils.IdLogged;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private IShoppingCartService shoppingCartService;
	
	@Autowired
	private IOrderDetailService orderDetailService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IOrderService orderService;
	
	private void deleteCookieCart(HttpServletResponse response) {
		try {
			
			Cookie cookie = new Cookie("cart", ""); 
			cookie.setHttpOnly(true);
			cookie.setMaxAge(0); 
			cookie.setPath("/");
			response.addCookie(cookie);
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
	}
	
	@GetMapping
	public ModelAndView orderPage() {
		ModelAndView mav = new ModelAndView("/web/order/index");
		
		try {
			
			if(shoppingCartService.isCartEmpty()) {
				mav = new ModelAndView("redirect:/store");
			}else {
				mav.addObject("userLogin", userService.findById(IdLogged.getId()).get());
				mav.addObject("orderDate", new Date());
				mav.addObject("listCart", shoppingCartService.getAllItems());
				mav.addObject("subCart", shoppingCartService.getTotalQuantity());
				mav.addObject("totalMoney", shoppingCartService.getTotalMoney());
			}
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return mav;
	}
	
	@PostMapping
	public ModelAndView order(
					@RequestParam("phone") String phone,
					@RequestParam("address") String address,
					@RequestParam("typePayment") String typePayment,
					@RequestParam("bankCode") String bankCode,
					HttpServletResponse response, 
					@ModelAttribute("formOrderInfo") OrderInfoRequest formOrderInfo ) {
		ModelAndView mav = new ModelAndView("redirect:/order");
		try {
			
			formOrderInfo.setUserId(IdLogged.getId());
			formOrderInfo.setNum(shoppingCartService.getTotalQuantity());
			formOrderInfo.setTotalMoney(shoppingCartService.getTotalMoney());
			
			formOrderInfo.setPhone(phone);
			formOrderInfo.setAddress_delivery(address);
			formOrderInfo.setPayment(typePayment);
			
			Collection<CartItem> carts = shoppingCartService.getAllItems();
			
			OrderDto orderEntity = orderService.Order(carts, formOrderInfo);
			if(orderEntity != null) {
				shoppingCartService.clear();
				deleteCookieCart(response);
				
				if(typePayment.equals("TRANSFER")) {
					mav = new ModelAndView("redirect:/checkout/vnpay?codeOrder="+orderEntity.getCodeOrder()+"&bankCode="+bankCode);
				}else if(typePayment.equals("COD")) {
					logger.error(""+orderService.findById(orderEntity.getId()).get());
					mav = new ModelAndView("/web/checkout/bill");
					mav.addObject("order", orderService.findById(orderEntity.getId()).get());
					mav.addObject("orderdetail", orderDetailService.findByOrder(orderEntity.getId()));
				}
			}
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		return mav;
	}
}
