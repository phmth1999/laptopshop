package com.phmth.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.dto.request.FilterProductRequest;
import com.phmth.laptopshop.dto.request.SearchProductRequest;

public interface IProductService{

	List<ProductDto> findAll();
	Page<ProductDto> findAll(int page, int limit);
	Page<ProductDto> findAll(FilterProductRequest formFilterProduct, int page, int limit);
	Page<ProductDto> findAll(int page, int limit, SearchProductRequest formSearchProduct);
	
	List<ProductDto> findByCategoryId(long id);
	
	List<ProductDto> findByNameSearch(String term);
	
	Optional<ProductDto> findById(long id);
	
	ProductDto insert(ProductDto productDto);
	
	boolean update(ProductDto productDto);

	boolean remove(long id);
	
}
