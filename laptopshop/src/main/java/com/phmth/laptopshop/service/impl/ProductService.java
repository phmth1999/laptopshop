package com.phmth.laptopshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.dto.request.FilterProductRequest;
import com.phmth.laptopshop.dto.request.SearchProductRequest;
import com.phmth.laptopshop.entity.BrandEntity;
import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.exception.ProductException;
import com.phmth.laptopshop.mapper.ProductMapper;
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

//	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private IProductRepository productRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private IBrandRepository brandRepository;
	
	private ProductMapper productMapper = new ProductMapper();

	@Override
	public Page<ProductDto> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		
		Page<ProductEntity> listProductEntity = productRepository.findAll(pageable);
		if(listProductEntity.isEmpty()) {
			return null;
		}
		
		Page<ProductDto> listProductDto = listProductEntity.map(new Function<ProductEntity, ProductDto>(){
			@Override
			public ProductDto apply(ProductEntity productEntity) {
				return productMapper.entityToDto(productEntity);
			}
		});
		
		return listProductDto;
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
	public Page<ProductDto> findAll(FilterProductRequest formFilterProduct, int page, int limit) {

		Sort sort = checkSortBy(formFilterProduct.getSort());
		Pageable pageable = PageRequest.of(page - 1, limit, sort);

		Specification<ProductEntity> specification = new Specification<ProductEntity>() {
			private static final long serialVersionUID = 1L;
			@Override
			public Predicate toPredicate(Root<ProductEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();

				if (!formFilterProduct.getCateogryName().equals("all")) {
					CategoryEntity categoryEntity = categoryRepository.findByName(formFilterProduct.getCateogryName()).get();
					predicates.add(criteriaBuilder.equal(root.get("category"), categoryEntity));
				}

				if (!formFilterProduct.getBrandName().equals("all")) {
					BrandEntity brandEntity = brandRepository.findByName(formFilterProduct.getBrandName()).get();
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

		Page<ProductEntity> listProductEntity = productRepository.findAll(specification, pageable);
		if(listProductEntity.isEmpty()) {
			return null;
		}
		
		Page<ProductDto> listProductDto = listProductEntity.map(new Function<ProductEntity, ProductDto>(){
			@Override
			public ProductDto apply(ProductEntity productEntity) {
				return productMapper.entityToDto(productEntity);
			}
		});
		
		return listProductDto;
	}

	@Override
	public Page<ProductDto> findAll(int page, int limit, SearchProductRequest formSearchProduct) {
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
				
				if (formSearchProduct.getCategoryName() != null && !formSearchProduct.getCategoryName().isEmpty()) {
					predicates.add(criteriaBuilder.equal(root.get("category"),
							categoryRepository.findByName(formSearchProduct.getCategoryName()).get()));
				}
				
				if (formSearchProduct.getBrandName() != null && !formSearchProduct.getBrandName().isEmpty()) {
					predicates.add(criteriaBuilder.equal(root.get("brand"),brandRepository.findByName(formSearchProduct.getBrandName()).get()));
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
		
		Page<ProductEntity> listProductEntity = productRepository.findAll(specification, pageable);
		if(listProductEntity.isEmpty()) {
			return null;
		}
		
		Page<ProductDto> listProductDto = listProductEntity.map(new Function<ProductEntity, ProductDto>(){
			@Override
			public ProductDto apply(ProductEntity productEntity) {
				return productMapper.entityToDto(productEntity);
			}
		});
		
		return listProductDto;
	}

	@Override
	public List<ProductDto> findByNameSearch(String term) {
		List<ProductEntity> listProductEntity = productRepository.findByNameStartsWith(term);
		if(listProductEntity.isEmpty()) {
			return null;
		}
		
		List<ProductDto> listProductDto = new ArrayList<>();
		for (ProductEntity productEntity : listProductEntity) {
			listProductDto.add(productMapper.entityToDto(productEntity));
		}
		
		return listProductDto;
	}

	@Override
	public Optional<ProductDto> findById(long id) {
		Optional<ProductEntity> productEntity = productRepository.findById(id);
		if(productEntity.isEmpty()) {
			return null;
		}
		
		Optional<ProductDto> productDto = productEntity.map(new Function<ProductEntity, ProductDto>(){
			@Override
			public ProductDto apply(ProductEntity productEntity) {
				return productMapper.entityToDto(productEntity);
			}
		});
		
		return productDto;
	}


	@Override
	public ProductDto insert(ProductDto productDto) {
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
//		ProductEntity productEntity = productMapper.dtoToEntity(productDto);
		ProductEntity productEntity = new ProductEntity();
		
		productEntity.setId(productDto.getId());
		Optional<CategoryEntity> categoryEntity = categoryRepository.findByName(productDto.getCategoryName());
		productEntity.setCategory(categoryEntity.get());
		Optional<BrandEntity> brandEntity = brandRepository.findByName(productDto.getBrandName());
		productEntity.setBrand(brandEntity.get());
		productEntity.setName(productDto.getName());
		productEntity.setPrice(productDto.getPrice());
		productEntity.setDiscount(productDto.getDiscount());
		productEntity.setQuantity_in_stock(productDto.getQuantity_in_stock());
		productEntity.setThumbnail(productDto.getThumbnail());
		productEntity.setDescription(productDto.getDescription());
		productEntity.setCreated_at(new Date());
		
		ProductEntity productSave = productRepository.save(productEntity);
		if (!productRepository.existsById(productSave.getId())) {
			return null;
		}
		
		return productMapper.entityToDto(productSave);
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
		Optional<ProductEntity> oldProductEntity = productRepository.findById(productDto.getId());
		if (oldProductEntity.isEmpty()) {
			throw new ProductException("The data to be modified is not found!");
		}

		// If the new product name is different from the old product name and the new
		// product name already exists, throw exception
		if (!oldProductEntity.get().getName().equals(productDto.getName()) && productRepository.existsByName(productDto.getName())) {
			throw new ProductException("The product name already exists!");
		}

		// If saving modification fail, return false
		CategoryEntity categoryEntity = categoryRepository.findByName(productDto.getCategoryName()).get();
		oldProductEntity.get().setCategory(categoryEntity);
		BrandEntity brandEntity = brandRepository.findByName(productDto.getBrandName()).get();
		oldProductEntity.get().setBrand(brandEntity);
		oldProductEntity.get().setName(productDto.getName());
		oldProductEntity.get().setPrice(productDto.getPrice());
		oldProductEntity.get().setDiscount(productDto.getDiscount());
		oldProductEntity.get().setThumbnail(productDto.getThumbnail());
		oldProductEntity.get().setQuantity_in_stock(productDto.getQuantity_in_stock());
		oldProductEntity.get().setDescription(productDto.getDescription());
		oldProductEntity.get().setUpdate_at(new Date());
		
		ProductEntity productSave = productRepository.save(oldProductEntity.get());
		if (productSave == null) {
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
	
	@Override
	public List<ProductDto> findByCategoryId(long id) {
		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
		if(categoryEntity.isEmpty()) {
			return null;
		}
		
		Set<ProductEntity> listProductEntity = categoryEntity.get().getProducts();
		List<ProductDto> listProductDto = new ArrayList<>();
		
		for (ProductEntity productEntity : listProductEntity) {
			listProductDto.add(productMapper.entityToDto(productEntity));
		}
		
		return listProductDto;
	}

}
