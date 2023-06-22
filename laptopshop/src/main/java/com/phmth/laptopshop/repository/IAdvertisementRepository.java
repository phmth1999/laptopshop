package com.phmth.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.AdvertisementEntity;

@Repository
public interface IAdvertisementRepository extends JpaRepository<AdvertisementEntity, Long>{

}
