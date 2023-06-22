package com.phmth.laptopshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.entity.FeedbackEntity;

public interface IFeedbackService{

	Iterable<FeedbackEntity> findAll();
	Page<FeedbackEntity> findAll(int page, int limit);

	Optional<FeedbackEntity> findOne(long id);

    FeedbackEntity insert(FeedbackEntity feedbackEntity);

    boolean update(FeedbackEntity feedbackEntity);

	boolean remove(long id);
	
	boolean existsById(long id);
}
