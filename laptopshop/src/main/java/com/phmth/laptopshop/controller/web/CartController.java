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

import com.phmth.laptopshop.dto.CartItem;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.service.IProductService;
import com.phmth.laptopshop.service.IShoppingCartService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private IShoppingCartService shoppingCartService;
	
	private void addCookieCart(HttpServletResponse response, String txt) {
		try {
			
			Cookie cookie = new Cookie("cart", txt); 
			cookie.setHttpOnly(true);
			cookie.setMaxAge(1*24*60*60); 
			cookie.setPath("/");
			response.addCookie(cookie);
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
	}
	
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
	public ModelAndView carts(
						@CookieValue(name = "cart", defaultValue = "") String cookieCart,
						HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("/web/cart/index");
		
		try {
			/* use cookies to create shopping cart list */
			shoppingCartService.readCookieCart(cookieCart);
			
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
			ProductEntity productEntity = productService.findOne(productId).get();
			if(productEntity != null) {
				CartItem cartItem = new CartItem(
						productId, 
						productEntity.getName(), 
						productEntity.getPrice(), 
						productEntity.getDiscount(), 
						productEntity.getQuantity_in_stock(),
						productEntity.getThumbnail(), 
						numProduct, 
						numProduct*(productEntity.getPrice()-(productEntity.getPrice()*productEntity.getDiscount()/100)));
				CartItem cartItems = shoppingCartService.findCartItem(productId);
				if(cartItems != null) {
					if(productEntity.getQuantity_in_stock()-cartItems.getNumProduct()-numProduct >= 0) {
						shoppingCartService.add(cartItem);
					}else {
						mav = new ModelAndView("redirect:/store/"+productId+"?cart="+cartItems.getNumProduct()+"&&err="+productEntity.getQuantity_in_stock()+"&&num="+numProduct);
					}
				}else {
					if(productEntity.getQuantity_in_stock()-numProduct >= 0) {
						shoppingCartService.add(cartItem);
					}else {
						mav = new ModelAndView("redirect:/store/"+productId+"?err="+productEntity.getQuantity_in_stock()+"&&num="+numProduct);
					}
				}
			}
			
			/* set the cookie value  from shopping cart list */
			Collection<CartItem> carts = shoppingCartService.getAllItems();
			if(carts != null && !carts.isEmpty()) {
				for (CartItem item : carts) {
					txt += "_"+item.getProductId()+":"+item.getNumProduct();
				}
				txt = txt.substring(1);
			}
			addCookieCart(response, txt);
			
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
			ProductEntity productEntity = productService.findOne(productId).get();
			if(productEntity != null) {
				CartItem cartItem = new CartItem(
						productId, 
						productEntity.getName(), 
						productEntity.getPrice(), 
						productEntity.getDiscount(), 
						productEntity.getQuantity_in_stock(),
						productEntity.getThumbnail(), 1, 
						1*(productEntity.getPrice()-(productEntity.getPrice()*productEntity.getDiscount()/100)));
				CartItem cartItems = shoppingCartService.findCartItem(productId);
				if(cartItems != null) {
					if(productEntity.getQuantity_in_stock()-cartItems.getNumProduct()-1 >= 0) {
						shoppingCartService.add(cartItem);
					}else {
						mav = new ModelAndView("redirect:/store"+"?cart="+cartItems.getNumProduct()+"&&err="+productEntity.getQuantity_in_stock());
					}
				}else {
					if(productEntity.getQuantity_in_stock()-1 >= 0) {
						shoppingCartService.add(cartItem);
					}else {
						mav = new ModelAndView("redirect:/store"+"?err="+0);
					}
				}
			}
			
			/* set the cookie value  from shopping cart list */
			Collection<CartItem> carts = shoppingCartService.getAllItems();
			if(carts != null && !carts.isEmpty()) {
				for (CartItem item : carts) {
					txt += "_"+item.getProductId()+":"+item.getNumProduct();
				}
				txt = txt.substring(1);
			}
			addCookieCart(response, txt);
			
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
			CartItem cartItem = shoppingCartService.findCartItem(productId);
			if(cartItem != null) {
				if(numProduct == -1) {
					if(cartItem.getNumProduct() <= 1) {
						shoppingCartService.remove(productId);
					}else {
						shoppingCartService.update(productId, cartItem.getNumProduct()-1);
					}
				}
				if(numProduct == 1) {
					ProductEntity productEntity = productService.findOne(productId).get();
					if(productEntity != null && productEntity.getQuantity_in_stock()-cartItem.getNumProduct()-1 >= 0) {
						shoppingCartService.update(productId, cartItem.getNumProduct()+1);
					}else {
						message = "?err="+productEntity.getQuantity_in_stock();
					}
				}
			}
			
			/* set the cookie value  from shopping cart list */
			Collection<CartItem> carts = shoppingCartService.getAllItems();
			if(carts != null && !carts.isEmpty()) {
				for (CartItem item : carts) {
					txt += "_"+item.getProductId()+":"+item.getNumProduct();
				}
				txt = txt.substring(1);
			}
			addCookieCart(response, txt);
			
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
			CartItem cartItem = shoppingCartService.findCartItem(productId);
			if(cartItem != null) {
				shoppingCartService.remove(productId);
			}
			
			/* set the cookie value  from shopping cart list */
			Collection<CartItem> carts = shoppingCartService.getAllItems();
			if(carts != null && !carts.isEmpty()) {
				for (CartItem item : carts) {
					txt += "_"+item.getProductId()+":"+item.getNumProduct();
				}
				txt = txt.substring(1);
			}
			addCookieCart(response, txt);
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return new ModelAndView("redirect:/cart");
	}
	
	@GetMapping("/clear-all-cart")
	public ModelAndView clearCoookieAndCarts(HttpServletResponse response) {
		
		try {
			
			/* delete all cookie with name 'cart' and shopping cart list */
			deleteCookieCart(response);
			shoppingCartService.clear();
			
		} catch (Exception e) {
			logger.error("Message erro --> {}: ", e);
		}
		
		return new ModelAndView("redirect:/cart");
	}
	
}
