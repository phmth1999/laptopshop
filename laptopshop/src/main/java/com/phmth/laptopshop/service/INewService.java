package com.phmth.laptopshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.entity.NewEntity;

public interface INewService {
	
	Page<NewEntity> findAll(int page, int limit);
	
	Optional<NewEntity> findOne(long id);
	
	NewEntity insert(NewEntity newEntity);
	
	boolean update(NewEntity newEntity);
}
