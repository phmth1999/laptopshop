package com.phmth.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.CategoryEntity;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long>{

	Optional<CategoryEntity> findByName(String name);

	Page<CategoryEntity> findAll(Specification<CategoryEntity> specification, Pageable pageable);

	boolean existsByName(String name);

}
