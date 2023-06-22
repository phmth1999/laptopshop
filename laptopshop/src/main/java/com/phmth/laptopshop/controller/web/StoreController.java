package com.phmth.laptopshop.controller.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.FormFilterProduct;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.service.IProductService;

@RestController
@RequestMapping("/store")
public class StoreController {
	
	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired
	private IProductService productService;
	
	
	
	@GetMapping
	public ModelAndView showStore(
				@ModelAttribute("formFilterProduct") FormFilterProduct formFilterProduct, 
				@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("/web/store/index");
		int limit = 4;
		
		try {
			Page<ProductEntity> listPageProduct = productService.findAll(formFilterProduct, page, limit);
			List<ProductEntity> listProduct = listPageProduct.getContent();
			
			mav.addObject("formFilterProduct", formFilterProduct);
			mav.addObject("listProduct", listProduct);
			
			mav.addObject("currentPage", page);
			mav.addObject("totalPage", listPageProduct.getTotalPages());
			
		} catch (Exception e) {
			logger.error("Message: --> {} :", e);
		}
		
		return mav;
	}
	
	@PostMapping
	public ModelAndView processStore(
			@ModelAttribute("formFilterProduct") FormFilterProduct formFilterProduct, 
			@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("/web/store/index");
		int limit = 4;
		
		try {
			Page<ProductEntity> listPageProduct = productService.findAll(formFilterProduct, page, limit);
			List<ProductEntity> listProduct = listPageProduct.getContent();
			
			mav.addObject("formFilterProduct", formFilterProduct);
			mav.addObject("listProduct", listProduct);
			
			mav.addObject("currentPage", page);
			mav.addObject("totalPage", listPageProduct.getTotalPages());
			
		} catch (Exception e) {
			logger.error("Message: --> {} :", e);
		}
		
		return mav;
	}
	
	@GetMapping("/{productId}")
	public ModelAndView storeDetailPage(@PathVariable("productId") long productId) {
		
		ModelAndView mav = new ModelAndView("/web/store/product-detail");
		
		mav.addObject("product", productService.findOne(productId).get());
		
		return mav;
	}
	
}
