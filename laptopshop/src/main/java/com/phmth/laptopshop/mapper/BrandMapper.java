package com.phmth.laptopshop.mapper;

import com.phmth.laptopshop.dto.BrandDto;
import com.phmth.laptopshop.entity.BrandEntity;

public class BrandMapper {

	public BrandDto entityToDto(BrandEntity brandEntity) {
		BrandDto brandDto = new BrandDto();

		brandDto.setId(brandEntity.getId());
		brandDto.setName(brandEntity.getName());

		return brandDto;
	}

	public BrandEntity dtoToEntity(BrandDto brandDto) {
		BrandEntity brandEntity = new BrandEntity();

		brandEntity.setId(brandDto.getId());
		brandEntity.setName(brandDto.getName());

		return brandEntity;
	}
}
