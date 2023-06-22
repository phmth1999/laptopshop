package com.phmth.laptopshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.FeedbackEntity;

@Repository
public interface IFeedbackRepository extends JpaRepository<FeedbackEntity, Long>{

}
