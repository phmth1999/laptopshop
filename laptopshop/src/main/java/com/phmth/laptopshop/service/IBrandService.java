package com.phmth.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.BrandDto;

public interface IBrandService{

	List<BrandDto> findAll();
	Page<BrandDto> findAll(int page, int limit);
	Page<BrandDto> findAll(int page, int limit, String name);
	
    Optional<BrandDto> findById(long id);
    Optional<BrandDto> findByName(String name);

    BrandDto insert(BrandDto brandDto);

    boolean update(BrandDto brandDto);

	boolean remove(long id);
	
}
