package com.phmth.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.GalleryEntity;

@Repository
public interface IGalleryRepository extends JpaRepository<GalleryEntity, Long>{

}
