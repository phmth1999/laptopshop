package com.phmth.laptopshop.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.OrderDto;
import com.phmth.laptopshop.dto.reponse.MessageResponse;
import com.phmth.laptopshop.dto.request.SearchOrderRequest;
import com.phmth.laptopshop.enums.StateOrder;
import com.phmth.laptopshop.exception.OrderException;
import com.phmth.laptopshop.service.IOrderService;

@RestController("adminOrder")
@RequestMapping("/admin/order")
public class OrderController {

	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private IOrderService orderService;

	@GetMapping
	public ModelAndView showOrderPage(@RequestParam(name = "page", defaultValue = "1") int page) {

		int limit = 3;
		
		ModelAndView mav = new ModelAndView("/admin/order/index");
		mav.addObject("formSearchOrder", new SearchOrderRequest());
		
		try {
			Page<OrderDto> listPageOrder = orderService.findAll(page, limit);
			if(listPageOrder != null) {
				List<OrderDto> listOrder = listPageOrder.getContent();
				mav.addObject("order", listOrder);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageOrder.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}

		return mav;
	}

	@PostMapping
	public ModelAndView processOrderPage(
					@ModelAttribute("formSearchOrder") SearchOrderRequest formSearchOrder,
					@RequestParam(name = "page", defaultValue = "1") int page) {

		int limit = 3;
		
		ModelAndView mav = new ModelAndView("/admin/order/index");
		mav.addObject("formSearchOrder", formSearchOrder);
		
		try {
			Page<OrderDto> listPageOrder = orderService.findAll(page, limit, formSearchOrder);
			if(listPageOrder != null) {
				List<OrderDto> listOrder = listPageOrder.getContent();
				mav.addObject("order", listOrder);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageOrder.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}

		return mav;
	}

	@PostMapping("/verify")
	public MessageResponse deleteProduct(
					@RequestParam("id") long id, 
					@RequestParam("status") StateOrder status) {

		String message = "";
		OrderDto data = null;
		
		try {
			boolean verify = orderService.updateStateOrder(id, status);
			
			if (verify) {
				message = "Verify successfully!";
				data = orderService.findById(id).get();
			} else {
				message = "Verify failed!";
			}
		} catch (OrderException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}

		return new MessageResponse(message, data);
	}

}
