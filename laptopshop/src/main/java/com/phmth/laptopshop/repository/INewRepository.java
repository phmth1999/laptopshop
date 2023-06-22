package com.phmth.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.NewEntity;

@Repository
public interface INewRepository extends JpaRepository<NewEntity, Long>{

}
