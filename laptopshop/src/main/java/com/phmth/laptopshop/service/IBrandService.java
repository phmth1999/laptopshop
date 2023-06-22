package com.phmth.laptopshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.FormAddBrand;
import com.phmth.laptopshop.dto.FormEditBrand;
import com.phmth.laptopshop.entity.BrandEntity;

public interface IBrandService{

	Iterable<BrandEntity> findAll();
	Page<BrandEntity> findAll(int page, int limit);
	Page<BrandEntity> findAll(int page, int limit, String name);
	
    Optional<BrandEntity> findOne(long id);
    Optional<BrandEntity> findOne(String name);

    BrandEntity insert(BrandEntity brandEntity);
    BrandEntity insert(FormAddBrand formAddBrand);

    boolean update(BrandEntity brandEntity);
    boolean update(FormEditBrand formEditBrand);

	boolean remove(long id);
	
	boolean existsById(long id);
	boolean existsByName(String name);

}
