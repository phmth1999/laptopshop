package com.phmth.laptopshop.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.dto.NewDto;
import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.entity.NewEntity;
import com.phmth.laptopshop.mapper.NewMapper;
import com.phmth.laptopshop.repository.ICategoryRepository;
import com.phmth.laptopshop.repository.INewRepository;
import com.phmth.laptopshop.service.INewService;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
@Transactional
public class NewService implements INewService{
	
	@Autowired
	private INewRepository newRepository;
	
	@Autowired
	private ICategoryRepository categoryRepository;
	
	private NewMapper newMapper = new NewMapper();
	
	@Override
	public Page<NewDto> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);

		Page<NewEntity> pageNewEntity = newRepository.findAll(pageable);
		
		if(pageNewEntity.isEmpty()) {
			return null;
		}
		
		Page<NewDto> pageNewDto = pageNewEntity.map(new Function<NewEntity, NewDto> (){
			@Override
			public NewDto apply(NewEntity newEntity) {
				return newMapper.entityToDto(newEntity);
			}
		});
		
		return pageNewDto;
	}

	@Override
	public Optional<NewDto> findById(long id) {
		Optional<NewEntity> newEntity = newRepository.findById(id);
		
		if(newEntity.isEmpty()) {
			return null;
		}
		
		Optional<NewDto> newDto = newEntity.map(new Function<NewEntity, NewDto>(){
			@Override
			public NewDto apply(NewEntity newEntity) {
				return newMapper.entityToDto(newEntity);
			}
		});
		
		return newDto;
	}

	@Override
	public NewDto insert(NewDto newDto) {
		if(newDto == null) {
			return null;
		}
		
		if(newDto.isEmpty()) {
			return null;
		}
		
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
		newEntity.setCreated_at(new Date());
		
		NewEntity newSave = newRepository.save(newEntity);
		
		if(!newRepository.existsById(newSave.getId())) {
			return null;
		}
		
		return newMapper.entityToDto(newSave);
	}

	@Override
	public boolean update(NewDto newDto) {
		if(newDto == null) {
			return false;
		}
		
		if(newDto.isEmpty()) {
			return false;
		}
		
		Optional<NewEntity> oldNewEntity = newRepository.findById(newDto.getId());
		if(oldNewEntity.isEmpty()) {
			return false;
		}
		
		NewEntity newEntity = new NewEntity();
		
		newEntity.setId(newDto.getId());
		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(newDto.getCategoryName());
		if(categoryEntity.isEmpty()) {
			return false;
		}
		newEntity.setCategory(categoryEntity.get());
		newEntity.setTitle(newDto.getTitle());
		newEntity.setThumbnail(newDto.getThumbnail());
		newEntity.setShortDescription(newDto.getShortDescription());
		newEntity.setContent(newDto.getContent());
		
		NewEntity newSave = newRepository.save(newEntity);
		
		if(newSave == null) {
			return false;
		}
		
		return true;
	}

}
