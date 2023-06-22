package com.phmth.laptopshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.dto.FormAddCategory;
import com.phmth.laptopshop.dto.FormEditCategory;
import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.exception.CategoryException;
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

	@Override
	public Iterable<CategoryEntity> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Page<CategoryEntity> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Page<CategoryEntity> findAll(int page, int limit, String name) {
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
		return categoryRepository.findAll(specification, pageable);
	}

	@Override
	public Optional<CategoryEntity> findOne(long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public Optional<CategoryEntity> findOne(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public CategoryEntity insert(CategoryEntity categoryEntity) {
		// If the input is null, throw exception
		if (categoryEntity == null) {
			throw new CategoryException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (categoryEntity.isEmpty()) {
			throw new CategoryException("The input is empty!");
		}
		
		// If the brand name already exists, throw exception
		if (categoryRepository.existsByName(categoryEntity.getName())) {
			throw new CategoryException("The category name already exists!");
		}

		return categoryRepository.save(categoryEntity);
	}

	@Override
	public CategoryEntity insert(FormAddCategory formAddCategory) {
		// If the input is null, throw exception
		if (formAddCategory == null) {
			throw new CategoryException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formAddCategory.isEmpty()) {
			throw new CategoryException("The input is empty!");
		}

		// If the brand name already exists, throw exception
		if (categoryRepository.existsByName(formAddCategory.getName())) {
			throw new CategoryException("The category name already exists!");
		}

		// If insert data failed, return null
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setName(formAddCategory.getName());
		CategoryEntity categorySave = categoryRepository.save(categoryEntity);
		if (categorySave == null) {
			return null;
		}

		return categorySave;
	}

	@Override
	public boolean update(CategoryEntity categoryEntity) {
		// If the input is null, throw exception
		if (categoryEntity == null) {
			throw new CategoryException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (categoryEntity.isEmpty()) {
			throw new CategoryException("The input is empty!");
		}
		
		// If the data to be modified is not found, throw exception
		Optional<CategoryEntity> categoryUpdate = categoryRepository.findById(categoryEntity.getId());
		if (categoryUpdate.isEmpty()) {
			throw new CategoryException("The data to be modified is not found!");
		}

		// If the new brand name is different from the old brand name and the new brand
		// name already exists, throw exception
		if (!categoryUpdate.get().getName().equals(categoryEntity.getName()) && categoryRepository.existsByName(categoryEntity.getName())) {
			throw new CategoryException("The category name already exists!");
		}

		// If saving modification fail, return false
		if (categoryRepository.save(categoryEntity) == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean update(FormEditCategory formEditCategory) {
		// If the input is null, throw exception
		if (formEditCategory == null) {
			throw new CategoryException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formEditCategory.isEmpty()) {
			throw new CategoryException("The input is empty!");
		}

		// If the data to be modified is not found, throw exception
		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(formEditCategory.getId());
		if (categoryEntity.isEmpty()) {
			throw new CategoryException("The data to be modified is not found!");
		}

		// If the new brand name is different from the old brand name and the new brand
		// name already exists, throw exception
		if (!categoryEntity.get().getName().equals(formEditCategory.getName()) && categoryRepository.existsByName(formEditCategory.getName())) {
			throw new CategoryException("The category name already exists!");
		}

		// If saving modification fail, return false
		categoryEntity.get().setName(formEditCategory.getName());
		if (categoryRepository.save(categoryEntity.get()) == null) {
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

	@Override
	public boolean existsById(long id) {
		return categoryRepository.existsById(id);
	}

	@Override
	public boolean existsByName(String name) {
		return categoryRepository.existsByName(name);
	}

}
