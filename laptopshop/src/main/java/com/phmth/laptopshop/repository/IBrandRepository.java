package com.phmth.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.BrandEntity;

@Repository
public interface IBrandRepository extends JpaRepository<BrandEntity, Long>{

	Page<BrandEntity> findAll(Specification<BrandEntity> specification, Pageable pageable);

	Optional<BrandEntity> findByName(String brandId);

	boolean existsByName(String name);

}
