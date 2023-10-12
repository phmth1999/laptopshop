package com.phmth.laptopshop.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.entity.ProductEntity;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long>{

	Page<ProductEntity> findAll(Specification<ProductEntity> specification, Pageable pageable);

	List<ProductEntity> findByNameStartsWith(String term);

	boolean existsByName(String name);

	Set<ProductEntity> findTop10ByCategoryOrderByIdDesc(CategoryEntity categoryEntity);

}
