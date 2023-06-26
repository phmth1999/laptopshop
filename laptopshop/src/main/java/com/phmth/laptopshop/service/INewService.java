package com.phmth.laptopshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.NewDto;

public interface INewService {
	
	Page<NewDto> findAll(int page, int limit);
	
	Optional<NewDto> findById(long id);
	
	NewDto insert(NewDto newDto);
	
	boolean update(NewDto newDto);
}
