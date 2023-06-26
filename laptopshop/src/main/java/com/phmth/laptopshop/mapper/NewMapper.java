package com.phmth.laptopshop.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.phmth.laptopshop.dto.NewDto;
import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.entity.NewEntity;
import com.phmth.laptopshop.repository.ICategoryRepository;

public class NewMapper {
	
	@Autowired
	private ICategoryRepository categoryRepository;

	public NewDto entityToDto(NewEntity newEntity) {
		NewDto newDto = new NewDto();
		
		newDto.setId(newEntity.getId());
		newDto.setCategoryName(newEntity.getCategory().getName());
		newDto.setTitle(newEntity.getTitle());
		newDto.setThumbnail(newEntity.getThumbnail());
		newDto.setShortDescription(newEntity.getShortDescription());
		newDto.setContent(newEntity.getContent());
		
		return newDto;
	}
	
	public NewEntity dtoToEntity(NewDto newDto) {
		NewEntity newEntity = new NewEntity();
		
		newEntity.setId(newDto.getId());
		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(newDto.getCategoryName());
		if(categoryEntity.isEmpty()) {
			return null;
		}
		newEntity.setCategory(categoryEntity.get());
		newEntity.setTitle(newDto.getTitle());
		newEntity.setThumbnail(newDto.getThumbnail());
		newEntity.setShortDescription(newDto.getShortDescription());
		newEntity.setContent(newDto.getContent());
		
		return newEntity;
	}
}
