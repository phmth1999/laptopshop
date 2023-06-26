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

import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.dto.request.FilterProductRequest;
import com.phmth.laptopshop.service.IBrandService;
import com.phmth.laptopshop.service.ICategoryService;
import com.phmth.laptopshop.service.IProductService;

@RestController
@RequestMapping("/store")
public class StoreController {
	
	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IBrandService brandService;
	
	@GetMapping
	public ModelAndView showStore(
				@ModelAttribute("formFilterProduct") FilterProductRequest formFilterProduct, 
				@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 4;
		
		ModelAndView mav = new ModelAndView("/web/store/index");
		
		try {
			mav.addObject("formFilterProduct", formFilterProduct);
			mav.addObject("category", categoryService.findAll());
			mav.addObject("brand", brandService.findAll());
			
			if(formFilterProduct.isEmpty()) {
				formFilterProduct.setCateogryName("all");
				formFilterProduct.setBrandName("all");
				formFilterProduct.setPrice("all");
				formFilterProduct.setSort("low-high");
			}
			
			Page<ProductDto> listPageProduct = productService.findAll(formFilterProduct, page, limit);
			if(listPageProduct != null) {
				List<ProductDto> listProduct = listPageProduct.getContent();
				mav.addObject("listProduct", listProduct);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageProduct.getTotalPages());
			}
			
		} catch (Exception e) {
			logger.error("Message: --> {} :", e);
		}
		
		return mav;
	}
	
	@PostMapping
	public ModelAndView processStore(
			@ModelAttribute("formFilterProduct") FilterProductRequest formFilterProduct, 
			@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 4;
		
		ModelAndView mav = new ModelAndView("/web/store/index");
		
		try {
			mav.addObject("formFilterProduct", formFilterProduct);
			mav.addObject("category", categoryService.findAll());
			mav.addObject("brand", brandService.findAll());
			
			if(formFilterProduct.isEmpty()) {
				formFilterProduct.setCateogryName("all");
				formFilterProduct.setBrandName("all");
				formFilterProduct.setPrice("all");
				formFilterProduct.setSort("low-high");
			}
			
			Page<ProductDto> listPageProduct = productService.findAll(formFilterProduct, page, limit);
			if(listPageProduct != null) {
				List<ProductDto> listProduct = listPageProduct.getContent();
				mav.addObject("listProduct", listProduct);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageProduct.getTotalPages());
			}
			
		} catch (Exception e) {
			logger.error("Message: --> {} :", e);
		}
		
		return mav;
	}
	
	@GetMapping("/{productId}")
	public ModelAndView storeDetailPage(@PathVariable("productId") long productId) {
		
		ModelAndView mav = new ModelAndView("/web/store/product-detail");
		
		mav.addObject("product", productService.findById(productId).get());
		
		return mav;
	}
	
}
