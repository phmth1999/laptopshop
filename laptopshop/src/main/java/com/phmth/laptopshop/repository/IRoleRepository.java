package com.phmth.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.RoleEntity;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long>{

	RoleEntity findByName(String role);

}
