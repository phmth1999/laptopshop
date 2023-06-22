package com.phmth.laptopshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.phmth.laptopshop.dto.FormFilterProduct;
import com.phmth.laptopshop.dto.FormSearchProduct;
import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.entity.BrandEntity;
import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.exception.ProductException;
import com.phmth.laptopshop.repository.IBrandRepository;
import com.phmth.laptopshop.repository.ICategoryRepository;
import com.phmth.laptopshop.repository.IProductRepository;
import com.phmth.laptopshop.service.IProductService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Transactional
public class ProductService implements IProductService {

	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private IBrandRepository brandRepository;

	@Override
	public Page<ProductEntity> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		
		return productRepository.findAll(pageable);
	}

	private Sort checkSortBy(String nameSort) {
		Sort sort = Sort.by(Sort.Direction.ASC, "price");
		
		if (nameSort.equals("low-high")) {
			sort = Sort.by(Sort.Direction.ASC, "price");
		} else if (nameSort.equals("high-low")) {
			sort = Sort.by(Sort.Direction.DESC, "price");
		} else if (nameSort.equals("a-z")) {
			sort = Sort.by(Sort.Direction.ASC, "name");
		} else if (nameSort.equals("z-a")) {
			sort = Sort.by(Sort.Direction.DESC, "name");
		}
		
		return sort;
	}

	@Override
	public Page<ProductEntity> findAll(FormFilterProduct formFilterProduct, int page, int limit) {

		Sort sort = checkSortBy(formFilterProduct.getSort());
		Pageable pageable = PageRequest.of(page - 1, limit, sort);

		Specification<ProductEntity> specification = new Specification<ProductEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();

				if (formFilterProduct.getCateogryId() != null && formFilterProduct.getCateogryId().longValue() > 0) {
					CategoryEntity categoryEntity = categoryRepository.findById(formFilterProduct.getCateogryId()).get();
					predicates.add(criteriaBuilder.equal(root.get("category"), categoryEntity));
				}

				if (formFilterProduct.getBrandId() != null && formFilterProduct.getBrandId().longValue() > 0) {
					BrandEntity brandEntity = brandRepository.findById(formFilterProduct.getBrandId()).get();
					predicates.add(criteriaBuilder.equal(root.get("brand"), brandEntity));
				}

				if (!formFilterProduct.getPrice().equals("all")) {
					double startPrice = 0;
					double endPrice = 0;
					if (formFilterProduct.getPrice().equals("1-5")) {
						startPrice = 1000000.0;
						endPrice = 5000000.0;
					} else if (formFilterProduct.getPrice().equals("5-10")) {
						startPrice = 5000000.0;
						endPrice = 10000000.0;
					} else if (formFilterProduct.getPrice().equals("10-100")) {
						startPrice = 10000000.0;
						endPrice = 100000000.0;
					}
					predicates.add(criteriaBuilder.between(root.get("price"), startPrice, endPrice));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};

		return productRepository.findAll(specification, pageable);
	}

	@Override
	public Page<ProductEntity> findAll(int page, int limit, FormSearchProduct formSearchProduct) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		
		Specification<ProductEntity> specification = new Specification<ProductEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<>();

				if (!formSearchProduct.getName().isBlank()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + formSearchProduct.getName() + "%"));
				}
				
				if (formSearchProduct.getCategory() != null && formSearchProduct.getCategory().longValue() > 0) {
					predicates.add(criteriaBuilder.equal(root.get("category"),
							categoryRepository.findById(formSearchProduct.getCategory()).get()));
				}
				
				if (formSearchProduct.getBrand() != null && formSearchProduct.getBrand().longValue() > 0) {
					predicates.add(criteriaBuilder.equal(root.get("brand"),brandRepository.findById(formSearchProduct.getBrand()).get()));
				}
				
				if (!formSearchProduct.getPrice().isBlank()) {
					predicates.add(criteriaBuilder.equal(root.get("price"), Double.parseDouble(formSearchProduct.getPrice())));
				}
				
				if (!formSearchProduct.getDiscount().isBlank()) {
					predicates.add(criteriaBuilder.equal(root.get("discount"),Double.parseDouble(formSearchProduct.getDiscount())));
				}
				
				if (!formSearchProduct.getQuantity().isBlank()) {
					predicates.add(criteriaBuilder.equal(root.get("quantity_in_stock"),Integer.parseInt(formSearchProduct.getQuantity())));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
		
		return productRepository.findAll(specification, pageable);
	}

	@Override
	public List<ProductEntity> findByNameSearch(String term) {
		return productRepository.findByNameStartsWith(term);
	}

	@Override
	public Optional<ProductEntity> findOne(long id) {
		return productRepository.findById(id);
	}


	@Override
	public ProductEntity insert(ProductDto productDto) {
		// If the input is null, throw exception
		if (productDto == null) {
			throw new ProductException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (productDto.isEmpty()) {
			throw new ProductException("The input is empty!");
		}

		// If the product name already exists, throw exception
		if (productRepository.existsByName(productDto.getName())) {
			throw new ProductException("The product name already exists!");
		}

		// If insert data failed, return null
		ProductEntity productEntity = new ProductEntity();
		CategoryEntity categoryEntity = categoryRepository.findById(productDto.getCategoryId()).get();
		productEntity.setCategory(categoryEntity);
		BrandEntity brandEntity = brandRepository.findById(productDto.getBrandId()).get();
		productEntity.setBrand(brandEntity);
		productEntity.setName(productDto.getName());
		productEntity.setPrice(productDto.getPrice());
		productEntity.setDiscount(productDto.getDiscount());
		productEntity.setThumbnail(productDto.getThumbnail());
		productEntity.setQuantity_in_stock(productDto.getQuantity_in_stock());
		productEntity.setDescription(productDto.getDescription());
		productEntity.setCreated_at(new Date());
		ProductEntity productSave = productRepository.save(productEntity);
		if (productSave == null) {
			return null;
		}

		return productSave;
	}

	@Override
	public boolean update(ProductDto productDto) {
		// If the input is null, throw exception
		if (productDto == null) {
			throw new ProductException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (productDto.isEmpty()) {
			throw new ProductException("The input is empty!");
		}

		// If the data to be modified is not found, throw exception
		Optional<ProductEntity> productEntity = productRepository.findById(productDto.getId());
		if (productEntity.isEmpty()) {
			throw new ProductException("The data to be modified is not found!");
		}

		// If the new product name is different from the old product name and the new
		// product name already exists, throw exception
		if (!productEntity.get().getName().equals(productDto.getName())
				&& productRepository.existsByName(productDto.getName())) {
			throw new ProductException("The product name already exists!");
		}

		// If saving modification fail, return false
		CategoryEntity categoryEntity = categoryRepository.findById(productDto.getCategoryId()).get();
		productEntity.get().setCategory(categoryEntity);
		BrandEntity brandEntity = brandRepository.findById(productDto.getBrandId()).get();
		productEntity.get().setBrand(brandEntity);
		productEntity.get().setName(productDto.getName());
		productEntity.get().setPrice(productDto.getPrice());
		productEntity.get().setDiscount(productDto.getDiscount());
		productEntity.get().setThumbnail(productDto.getThumbnail());
		productEntity.get().setQuantity_in_stock(productDto.getQuantity_in_stock());
		productEntity.get().setDescription(productDto.getDescription());
		productEntity.get().setUpdate_at(new Date());
		if (productRepository.save(productEntity.get()) == null) {
			return false;
		}

		return true;
	}


	@Override
	public boolean remove(long id) {

		// If the data does not exist, throw exception
		if (!productRepository.existsById(id)) {
			throw new ProductException("The data does not exist!");
		}

		// Clear data based on input
		productRepository.deleteById(id);

		// If the deleted data still exists, return false
		if (productRepository.existsById(id)) {
			return false;
		}

		return true;
	}

}
