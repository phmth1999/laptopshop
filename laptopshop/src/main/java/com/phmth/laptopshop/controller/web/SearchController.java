package com.phmth.laptopshop.controller.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.service.IProductService;

@RestController
@RequestMapping("/search")
public class SearchController {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private IProductService productService;
	
	@GetMapping
	public List<ProductDto> search(@RequestParam("term") String term){
		List<ProductDto> listSearch = productService.findByNameSearch(term);
		return listSearch;
	}
	
}
