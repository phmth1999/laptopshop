package com.phmth.laptopshop.mapper;

import com.phmth.laptopshop.dto.FeedbackDto;
import com.phmth.laptopshop.entity.FeedbackEntity;

public class FeedbackMapper {

	public FeedbackDto entityToDto(FeedbackEntity feedbackEntity) {
		FeedbackDto feedbackDto = new FeedbackDto();
		
		feedbackDto.setId(feedbackEntity.getId());
		feedbackDto.setEmail(feedbackEntity.getEmail());
		feedbackDto.setName(feedbackEntity.getName());
		feedbackDto.setMessage(feedbackEntity.getMessage());
		
		return feedbackDto;
	}
	
	public FeedbackEntity dtoToEntity(FeedbackDto feedbackDto) {
		FeedbackEntity feedbackEntity = new FeedbackEntity();
		
		feedbackEntity.setId(feedbackDto.getId());
		feedbackEntity.setEmail(feedbackDto.getEmail());
		feedbackEntity.setName(feedbackDto.getName());
		feedbackEntity.setMessage(feedbackDto.getMessage());
		
		return feedbackEntity;
	}
}
