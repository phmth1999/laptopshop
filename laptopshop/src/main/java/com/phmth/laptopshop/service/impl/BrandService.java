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

import com.phmth.laptopshop.dto.BrandDto;
import com.phmth.laptopshop.entity.BrandEntity;
import com.phmth.laptopshop.exception.BrandException;
import com.phmth.laptopshop.mapper.BrandMapper;
import com.phmth.laptopshop.repository.IBrandRepository;
import com.phmth.laptopshop.service.IBrandService;

import groovy.util.logging.Slf4j;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Slf4j
@Transactional
public class BrandService implements IBrandService {
	
	@Autowired
	private IBrandRepository brandRepository;
	
	private BrandMapper brandMapper = new BrandMapper();

	@Override
	public List<BrandDto> findAll() {
		List<BrandEntity> listBrandEntity = brandRepository.findAll();
		
		if(listBrandEntity.isEmpty()) {
			return null;
		}
		
		List<BrandDto> listBrandDto = new ArrayList<>();
		for (BrandEntity brandEntity : listBrandEntity) {
			listBrandDto.add(brandMapper.entityToDto(brandEntity));
		}
		
		return listBrandDto;
	}

	@Override
	public Page<BrandDto> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		
		Page<BrandEntity> pageBrandEntity = brandRepository.findAll(pageable);
		
		if(pageBrandEntity.isEmpty()) {
			return null;
		}
		
		Page<BrandDto> pageBrandDto = pageBrandEntity.map(new Function<BrandEntity, BrandDto> (){

			@Override
			public BrandDto apply(BrandEntity brandEntity) {
				return brandMapper.entityToDto(brandEntity);
			}
			
		});

		return pageBrandDto;
	}

	@Override
	public Page<BrandDto> findAll(int page, int limit, String name) {
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
		
		Page<BrandEntity> pageBrandEntity = brandRepository.findAll(specification, pageable);
		
		if(pageBrandEntity.isEmpty()) {
			return null;
		}
		
		Page<BrandDto> pageBrandDto = pageBrandEntity.map(new Function<BrandEntity, BrandDto>(){
			@Override
			public BrandDto apply(BrandEntity brandEntity) {
				return brandMapper.entityToDto(brandEntity);
			}
		});
		
		return pageBrandDto;
	}

	@Override
	public Optional<BrandDto> findById(long id) {
		Optional<BrandEntity> brandEntity = brandRepository.findById(id);
		
		if(brandEntity.isEmpty()) {
			return null;
		}
		
		Optional<BrandDto> brandDto = brandEntity.map(new Function<BrandEntity, BrandDto>(){
			@Override
			public BrandDto apply(BrandEntity brandEntity) {
				return brandMapper.entityToDto(brandEntity);
			}
		});
		
		return brandDto;
	}

	@Override
	public Optional<BrandDto> findByName(String name) {
		Optional<BrandEntity> brandEntity = brandRepository.findByName(name);
		
		if(brandEntity.isEmpty()) {
			return null;
		}
		
		Optional<BrandDto> brandDto = brandEntity.map(new Function<BrandEntity, BrandDto>(){
			@Override
			public BrandDto apply(BrandEntity brandEntity) {
				return brandMapper.entityToDto(brandEntity);
			}
		});
		
		return brandDto;
	}

	@Override
	public BrandDto insert(BrandDto brandDto) {
		// If the input is null, throw exception
		if (brandDto == null) {
			throw new BrandException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (brandDto.isEmpty()) {
			throw new BrandException("The input is empty!");
		}

		// If the brand name already exists, throw exception
		if (brandRepository.existsByName(brandDto.getName())) {
			throw new BrandException("The brand name already exists!");
		}

		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setName(brandDto.getName());
		BrandEntity brandSave = brandRepository.save(brandEntity);
		
		if (!brandRepository.existsById(brandSave.getId())) {
			return null;
		}
		
		return brandMapper.entityToDto(brandSave);
	}
	
	@Override
	public boolean update(BrandDto brandDto) {
		// If the input is null, throw exception
		if (brandDto == null) {
			throw new BrandException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (brandDto.isEmpty()) {
			throw new BrandException("The input is empty!");
		}

		// If the data to be modified is not found, throw exception
		Optional<BrandEntity> oldBrandEntity = brandRepository.findById(brandDto.getId());
		if (oldBrandEntity.isEmpty()) {
			throw new BrandException("The data to be modified is not found!");
		}
		
		// If the new brand name is different from the old brand name and the new brand name already exists, throw exception
		if(!oldBrandEntity.get().getName().equals(brandDto.getName()) && brandRepository.existsByName(brandDto.getName())) {
			throw new BrandException("The brand name already exists!");
		}
		
		oldBrandEntity.get().setName(brandDto.getName());
		BrandEntity brandSave = brandRepository.save(oldBrandEntity.get());
		
		if (brandSave == null) {
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

}
