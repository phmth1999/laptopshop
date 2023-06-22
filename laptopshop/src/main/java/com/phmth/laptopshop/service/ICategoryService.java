package com.phmth.laptopshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.FormAddCategory;
import com.phmth.laptopshop.dto.FormEditCategory;
import com.phmth.laptopshop.entity.CategoryEntity;

public interface ICategoryService{

	Iterable<CategoryEntity> findAll();
	Page<CategoryEntity> findAll(int page, int limit);
	Page<CategoryEntity> findAll(int page, int limit, String name);
	
	Optional<CategoryEntity> findOne(long id);
	Optional<CategoryEntity> findOne(String name);
	
	CategoryEntity insert(CategoryEntity categoryEntity);
	CategoryEntity insert(FormAddCategory formAddCategory);
	
	boolean update(CategoryEntity categoryEntity);
	boolean update(FormEditCategory formEditCategory);

	boolean remove(long id);
	
	boolean existsById(long id);
	boolean existsByName(String name);
	
}
