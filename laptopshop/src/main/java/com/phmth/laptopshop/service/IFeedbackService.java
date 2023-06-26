package com.phmth.laptopshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.FeedbackDto;

public interface IFeedbackService{

	Page<FeedbackDto> findAll(int page, int limit);

	Optional<FeedbackDto> findById(long id);

	FeedbackDto insert(FeedbackDto feedbackDto);

	boolean remove(long id);
	
}
