package com.phmth.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.FormFilterProduct;
import com.phmth.laptopshop.dto.FormSearchProduct;
import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.entity.ProductEntity;

public interface IProductService{

	Page<ProductEntity> findAll(int page, int limit);
	Page<ProductEntity> findAll(FormFilterProduct formFilterProduct, int page, int limit);
	Page<ProductEntity> findAll(int page, int limit, FormSearchProduct formSearchProduct);

	List<ProductEntity> findByNameSearch(String term);

	Optional<ProductEntity> findOne(long id);
	
	ProductEntity insert(ProductDto productDto);
	
	boolean update(ProductDto productDto);

	boolean remove(long id);
	
}
