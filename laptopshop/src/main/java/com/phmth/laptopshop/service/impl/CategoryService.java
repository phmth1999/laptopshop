package com.phmth.laptopshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.dto.CategoryDto;
import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.exception.CategoryException;
import com.phmth.laptopshop.mapper.CategoryMapper;
import com.phmth.laptopshop.repository.ICategoryRepository;
import com.phmth.laptopshop.service.ICategoryService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
public class CategoryService implements ICategoryService {

	@Autowired
	private ICategoryRepository categoryRepository;
	
	private CategoryMapper categoryMapper = new CategoryMapper();

	@Override
	public List<CategoryDto> findAll() {
		List<CategoryEntity> listCategoryEntity = categoryRepository.findAll();
		
		if(listCategoryEntity.isEmpty()) {
			return null;
		}
		
		List<CategoryDto> listCategoryDto = new ArrayList<>();
		for (CategoryEntity categoryEntity : listCategoryEntity) {
			listCategoryDto.add(categoryMapper.entityToDto(categoryEntity));
		}
		
		return listCategoryDto;
	}

	@Override
	public Page<CategoryDto> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		
		Page<CategoryEntity> pageCategoryEntity = categoryRepository.findAll(pageable);
		
		if(pageCategoryEntity.isEmpty()) {
			return null;
		}
		
		Page<CategoryDto> pageCategoryDto = pageCategoryEntity.map(new Function<CategoryEntity, CategoryDto> (){

			@Override
			public CategoryDto apply(CategoryEntity categoryEntity) {
				return categoryMapper.entityToDto(categoryEntity);
			}
			
		});

		return pageCategoryDto;
	}

	@Override
	public Page<CategoryDto> findAll(int page, int limit, String name) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		Specification<CategoryEntity> specification = new Specification<CategoryEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<CategoryEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				if (!name.isBlank()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
		
		Page<CategoryEntity> pageCategoryEntity = categoryRepository.findAll(specification, pageable);
		
		if(pageCategoryEntity.isEmpty()) {
			return null;
		}
		
		Page<CategoryDto> pageCategoryDto = pageCategoryEntity.map(new Function<CategoryEntity, CategoryDto> (){

			@Override
			public CategoryDto apply(CategoryEntity categoryEntity) {
				return categoryMapper.entityToDto(categoryEntity);
			}
			
		});

		return pageCategoryDto;
	}

	@Override
	public Optional<CategoryDto> findById(long id) {
		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
		
		if(categoryEntity.isEmpty()) {
			return null;
		}
		
		Optional<CategoryDto> categoryDto = categoryEntity.map(new Function<CategoryEntity, CategoryDto>(){

			@Override
			public CategoryDto apply(CategoryEntity categoryEntity) {
				return categoryMapper.entityToDto(categoryEntity);
			}
			
		});
		
		return categoryDto;
	}

	@Override
	public Optional<CategoryDto> findByName(String name) {
		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(name);
		
		if(categoryEntity.isEmpty()) {
			return null;
		}
		
		Optional<CategoryDto> categoryDto = categoryEntity.map(new Function<CategoryEntity, CategoryDto>(){

			@Override
			public CategoryDto apply(CategoryEntity categoryEntity) {
				return categoryMapper.entityToDto(categoryEntity);
			}
			
		});
		
		return categoryDto;
	}

	@Override
	public CategoryDto insert(CategoryDto categoryDto) {
		// If the input is null, throw exception
		if (categoryDto == null) {
			throw new CategoryException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (categoryDto.isEmpty()) {
			throw new CategoryException("The input is empty!");
		}

		// If the brand name already exists, throw exception
		if (categoryRepository.existsByName(categoryDto.getName())) {
			throw new CategoryException("The category name already exists!");
		}

		// If insert data failed, return null
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setName(categoryDto.getName());
		CategoryEntity categorySave = categoryRepository.save(categoryEntity);
		
		if (!categoryRepository.existsById(categorySave.getId())) {
			return null;
		}
		
		return categoryMapper.entityToDto(categorySave);
	}

	@Override
	public boolean update(CategoryDto categoryDto) {
		// If the input is null, throw exception
		if (categoryDto == null) {
			throw new CategoryException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (categoryDto.isEmpty()) {
			throw new CategoryException("The input is empty!");
		}

		// If the data to be modified is not found, throw exception
		Optional<CategoryEntity> oldCategoryEntity = categoryRepository.findById(categoryDto.getId());
		if (oldCategoryEntity.isEmpty()) {
			throw new CategoryException("The data to be modified is not found!");
		}

		// If the new brand name is different from the old brand name and the new brand
		// name already exists, throw exception
		if (!oldCategoryEntity.get().getName().equals(categoryDto.getName()) && categoryRepository.existsByName(categoryDto.getName())) {
			throw new CategoryException("The category name already exists!");
		}

		// If saving modification fail, return false
		oldCategoryEntity.get().setName(categoryDto.getName());
		CategoryEntity categorySave = categoryRepository.save(oldCategoryEntity.get());
		
		if (categorySave == null) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean remove(long id) {

		// If the data does not exist, throw exception
		if (!categoryRepository.existsById(id)) {
			throw new CategoryException("The data does not exist!");
		}

		// Clear data based on input
		categoryRepository.deleteById(id);

		// If the deleted data still exists, return false
		if (categoryRepository.existsById(id)) {
			return false;
		}

		return true;
	}

}
