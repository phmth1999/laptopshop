package com.phmth.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.CategoryDto;

public interface ICategoryService{

	List<CategoryDto> findAll();
	Page<CategoryDto> findAll(int page, int limit);
	Page<CategoryDto> findAll(int page, int limit, String name);
	
	Optional<CategoryDto> findById(long id);
	Optional<CategoryDto> findByName(String name);
	
	CategoryDto insert(CategoryDto categoryDto);
	
	boolean update(CategoryDto categoryDto);

	boolean remove(long id);
	
}
