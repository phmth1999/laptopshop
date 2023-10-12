package com.phmth.laptopshop.controller.web;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.CartItemDto;
import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.service.ICookieService;
import com.phmth.laptopshop.service.IProductService;
import com.phmth.laptopshop.service.IShoppingCartService;
import com.phmth.laptopshop.utils.IdLogged;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IShoppingCartService shoppingCartService;
	
	@Autowired
	private ICookieService cookieService;
	
	@GetMapping
	public ModelAndView carts(
						@CookieValue(name = "cart", defaultValue = "") String cookieCart,
						HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("/web/cart/index");
		
		try {
			logger.error("start read cookie");
			shoppingCartService.readCookieCart(cookieCart, IdLogged.getId());
			logger.error("end read cookie");
			
			if(IdLogged.getId() > 0) {
				shoppingCartService.readDatabaseCart(IdLogged.getId());
				cookieService.remove("cart");
			}
			
			/* Set the value to display on the interface */
			mav.addObject("listCart", shoppingCartService.getAllItems());
			mav.addObject("subCart", shoppingCartService.getTotalQuantity());
			mav.addObject("totalMoney", shoppingCartService.getTotalMoney());
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return mav;
	}
	
	@PostMapping("/add-cart")
	public ModelAndView addCartByPost(
						@RequestParam("productId") long productId,
						@RequestParam(name = "numProduct", defaultValue = "1") int numProduct,
						HttpServletResponse response
						) {
		
		ModelAndView mav = new ModelAndView("redirect:/cart");
		
		String txt = "";
		
		try {
			
			/* add cartItem on shopping cart list */
			ProductDto productEntity = productService.findById(productId).get();
			if(productEntity != null) {
				CartItemDto cartItems = shoppingCartService.findCartItem(productId);
				if(cartItems != null) {
					if(productEntity.getQuantity_in_stock()-cartItems.getNumProduct()-numProduct >= 0) {
						shoppingCartService.add(IdLogged.getId(), productId, numProduct);
					}else {
						mav = new ModelAndView("redirect:/store/"+productId+"?cart="+cartItems.getNumProduct()+"&&err="+productEntity.getQuantity_in_stock()+"&&num="+numProduct);
					}
				}else {
					if(productEntity.getQuantity_in_stock()-numProduct >= 0) {
						shoppingCartService.add(IdLogged.getId(), productId, numProduct);
					}else {
						mav = new ModelAndView("redirect:/store/"+productId+"?err="+productEntity.getQuantity_in_stock()+"&&num="+numProduct);
					}
				}
			}
			
			if(IdLogged.getId() == 0) {
				/* set the cookie value  from shopping cart list */
				Collection<CartItemDto> carts = shoppingCartService.getAllItems();
				if(carts != null && !carts.isEmpty()) {
					for (CartItemDto item : carts) {
						txt += "_"+item.getProductId()+":"+item.getNumProduct();
					}
					txt = txt.substring(1);
				}
				cookieService.add("cart", txt, 1);
			}
			
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return mav;
	}
	
	@GetMapping("/add-cart/{productId}")
	public ModelAndView addCartByGet(
			@PathVariable("productId") long productId,
			HttpServletResponse response
			) {
		
		ModelAndView mav = new ModelAndView("redirect:/cart");
		String txt = "";
		
		try {
			
			/* add cartItem on shopping cart list */
			ProductDto productEntity = productService.findById(productId).get();
			
			if(productEntity != null) {
				CartItemDto cartItems = shoppingCartService.findCartItem(productId);
				if(cartItems != null) {
					if(productEntity.getQuantity_in_stock()-cartItems.getNumProduct()-1 >= 0) {
						shoppingCartService.add(IdLogged.getId(), productId, 1);
					}else {
						mav = new ModelAndView("redirect:/store"+"?cart="+cartItems.getNumProduct()+"&&err="+productEntity.getQuantity_in_stock());
					}
				}else {
					if(productEntity.getQuantity_in_stock()-1 >= 0) {
						shoppingCartService.add(IdLogged.getId(), productId, 1);
					}else {
						mav = new ModelAndView("redirect:/store"+"?err="+0);
					}
				}
			}
			
			if(IdLogged.getId() == 0) {
				/* set the cookie value  from shopping cart list */
				Collection<CartItemDto> carts = shoppingCartService.getAllItems();
				if(carts != null && !carts.isEmpty()) {
					for (CartItemDto item : carts) {
						txt += "_"+item.getProductId()+":"+item.getNumProduct();
					}
					txt = txt.substring(1);
				}
				cookieService.add("cart", txt, 1);
			}
			
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return mav;
	}
	
	@GetMapping("/edit-cart/{productId}/{numProduct}")
	public ModelAndView editCartById(
			@PathVariable("productId") long productId,
			@PathVariable("numProduct") int numProduct,
			HttpServletResponse response
			) {
		
		String txt = "";
		String message = "";
		
		try {
			
			/* increase or decrease the number of cartItems in shopping cart list */
			CartItemDto cartItem = shoppingCartService.findCartItem(productId);
			if(cartItem != null) {
				if(numProduct == -1) {
					if(cartItem.getNumProduct() <= 1) {
						shoppingCartService.remove(IdLogged.getId(), productId);
					}else {
						shoppingCartService.update(IdLogged.getId(), productId, numProduct);
					}
				}
				if(numProduct == 1) {
					ProductDto productEntity = productService.findById(productId).get();
					if(productEntity != null && productEntity.getQuantity_in_stock()-cartItem.getNumProduct()-1 >= 0) {
						shoppingCartService.update(IdLogged.getId() ,productId, numProduct);
					}else {
						message = "?err="+productEntity.getQuantity_in_stock();
					}
				}
			}
			
			if(IdLogged.getId() == 0) {
				/* set the cookie value  from shopping cart list */
				Collection<CartItemDto> carts = shoppingCartService.getAllItems();
				if(carts != null && !carts.isEmpty()) {
					for (CartItemDto item : carts) {
						txt += "_"+item.getProductId()+":"+item.getNumProduct();
					}
					txt = txt.substring(1);
				}
				cookieService.add("cart", txt, 1);
			}
			
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return new ModelAndView("redirect:/cart"+message);
	}
	
	@GetMapping("/delete-cart/{productId}")
	public ModelAndView deleteCartById(
			@CookieValue(name = "cart", defaultValue = "") String cookieCart,
			@PathVariable("productId") long productId,
			HttpServletResponse response
			) {
		
		String txt = "";
		
		try {
			
			/* delete one cartItem in shopping cart list */
			CartItemDto cartItem = shoppingCartService.findCartItem(productId);
			if(cartItem != null) {
				shoppingCartService.remove(IdLogged.getId(), productId);
			}
			
			if(IdLogged.getId() == 0) {
				/* set the cookie value  from shopping cart list */
				Collection<CartItemDto> carts = shoppingCartService.getAllItems();
				if(carts != null && !carts.isEmpty()) {
					for (CartItemDto item : carts) {
						txt += "_"+item.getProductId()+":"+item.getNumProduct();
					}
					txt = txt.substring(1);
				}
				cookieService.add("cart", txt, 1);
			}
		
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return new ModelAndView("redirect:/cart");
	}
	
	@GetMapping("/clear-all-cart")
	public ModelAndView clearCoookieAndCarts(HttpServletResponse response) {
		
		try {
			
			/* delete all cookie with name 'cart' and shopping cart list */
			cookieService.remove("cart");
			shoppingCartService.clear(IdLogged.getId());
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return new ModelAndView("redirect:/cart");
	}
	
}
