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

import com.phmth.laptopshop.dto.FormAddBrand;
import com.phmth.laptopshop.dto.FormEditBrand;
import com.phmth.laptopshop.entity.BrandEntity;
import com.phmth.laptopshop.exception.BrandException;
import com.phmth.laptopshop.repository.IBrandRepository;
import com.phmth.laptopshop.service.IBrandService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
public class BrandService implements IBrandService {
	
	@Autowired
	private IBrandRepository brandRepository;

	@Override
	public Iterable<BrandEntity> findAll() {
		return brandRepository.findAll();
	}

	@Override
	public Page<BrandEntity> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);

		return brandRepository.findAll(pageable);
	}

	@Override
	public Page<BrandEntity> findAll(int page, int limit, String name) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);

		Specification<BrandEntity> specification = new Specification<BrandEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<BrandEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (!name.isBlank()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
		return brandRepository.findAll(specification, pageable);
	}

	@Override
	public Optional<BrandEntity> findOne(long id) {
		return brandRepository.findById(id);
	}

	@Override
	public Optional<BrandEntity> findOne(String name) {
		return brandRepository.findByName(name);
	}

	@Override
	public BrandEntity insert(BrandEntity brandEntity) {
		// If the input is null, throw exception
		if (brandEntity == null) {
			throw new BrandException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (brandEntity.isEmpty()) {
			throw new BrandException("The input is empty!");
		}

		// If the brand name already exists, throw exception
		if (brandRepository.existsByName(brandEntity.getName())) {
			throw new BrandException("The brand name already exists!");
		}

		// If insert data failed, return null
		BrandEntity brandSave = brandRepository.save(brandEntity);
		if (brandSave == null) {
			return null;
		}

		return brandSave;
	}
	
	@Override
	public BrandEntity insert(FormAddBrand formAddBrand) {
		// If the input is null, throw exception
		if (formAddBrand == null) {
			throw new BrandException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formAddBrand.isEmpty()) {
			throw new BrandException("The input is empty!");
		}

		// If the brand name already exists, throw exception
		if (brandRepository.existsByName(formAddBrand.getName())) {
			throw new BrandException("The brand name already exists!");
		}

		// If insert data failed, return null
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setName(formAddBrand.getName());
		BrandEntity brandSave = brandRepository.save(brandEntity);
		if (brandSave == null) {
			return null;
		}

		return brandSave;
	}
	
	@Override
	public boolean update(BrandEntity brandEntity) {
		// If the input is null, throw exception
		if (brandEntity == null) {
			throw new BrandException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (brandEntity.isEmpty()) {
			throw new BrandException("The input is empty!");
		}

		// If the data to be modified is not found, throw exception
		Optional<BrandEntity> brandFind = brandRepository.findById(brandEntity.getId());
		if (brandFind.isEmpty()) {
			throw new BrandException("The data to be modified is not found!");
		}
		
		// If the new brand name is different from the old brand name and the new brand name already exists, throw exception
		if(!brandFind.get().getName().equals(brandEntity.getName()) && brandRepository.existsByName(brandEntity.getName())) {
			throw new BrandException("The brand name already exists!");
		}
		
		// If saving modification fail, return false
		if (brandRepository.save(brandEntity) == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean update(FormEditBrand formEditBrand) {
		// If the input is null, throw exception
		if (formEditBrand == null) {
			throw new BrandException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formEditBrand.isEmpty()) {
			throw new BrandException("The input is empty!");
		}

		// If the data to be modified is not found, throw exception
		Optional<BrandEntity> brandUpdate = brandRepository.findById(formEditBrand.getId());
		if (brandUpdate.isEmpty()) {
			throw new BrandException("The data to be modified is not found!");
		}
		
		// If the new brand name is different from the old brand name and the new brand name already exists, throw exception
		if(!brandUpdate.get().getName().equals(formEditBrand.getName()) && brandRepository.existsByName(formEditBrand.getName()) ) {
			throw new BrandException("The brand name already exists!");
		}

		// If saving modification fail, return false
		brandUpdate.get().setName(formEditBrand.getName());
		if (brandRepository.save(brandUpdate.get()) == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean remove(long id) {

		// If the data does not exist, throw exception
		if (!brandRepository.existsById(id)) {
			throw new BrandException("The data does not exist!");
		}

		// Clear data based on input
		brandRepository.deleteById(id);

		// If the deleted data still exists, return false
		if (brandRepository.existsById(id)) {
			return false;
		}

		return true;
	}

	@Override
	public boolean existsById(long id) {
		return brandRepository.existsById(id);
	}

	@Override
	public boolean existsByName(String name) {
		return brandRepository.existsByName(name);
	}

}
