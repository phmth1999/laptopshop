package com.phmth.laptopshop.service.impl;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.dto.FeedbackDto;
import com.phmth.laptopshop.entity.FeedbackEntity;
import com.phmth.laptopshop.mapper.FeedbackMapper;
import com.phmth.laptopshop.repository.IFeedbackRepository;
import com.phmth.laptopshop.service.IFeedbackService;

import groovy.util.logging.Slf4j;

@Service
@Slf4j
@Transactional
public class FeedbackService implements IFeedbackService{
	
	@Autowired
	private IFeedbackRepository feedbackRepository;
	
	private FeedbackMapper feedbackMapper = new FeedbackMapper();


	@Override
	public Page<FeedbackDto> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page-1, limit, sort);
		
		Page<FeedbackEntity> listFeedbackEntity = feedbackRepository.findAll(pageable);
		if(listFeedbackEntity.isEmpty()) {
			return null;
		}
		
		Page<FeedbackDto> listFeedbackDto = listFeedbackEntity.map(new Function<FeedbackEntity, FeedbackDto>(){

			@Override
			public FeedbackDto apply(FeedbackEntity feedbackEntity) {
				return feedbackMapper.entityToDto(feedbackEntity);
			}
		});
		
		return listFeedbackDto;
	}



	@Override
	public Optional<FeedbackDto> findById(long id) {
		Optional<FeedbackEntity> feedbackEntity = feedbackRepository.findById(id);
		if(feedbackEntity.isEmpty()) {
			return null;
		}
		
		Optional<FeedbackDto> feedbackDto = feedbackEntity.map(new Function<FeedbackEntity, FeedbackDto>(){

			@Override
			public FeedbackDto apply(FeedbackEntity feedbackEntity) {
				return feedbackMapper.entityToDto(feedbackEntity);
			}
		});
		
		return feedbackDto;
	}


	@Override
	public FeedbackDto insert(FeedbackDto feedbackDto) {
		//If the input is null, return null
		if(feedbackDto == null) {
			return null;
		}
		
		if(feedbackDto.isEmpty()) {
			return null;
		}
		
		FeedbackEntity feedbackSave = feedbackRepository.save(feedbackMapper.dtoToEntity(feedbackDto));
		if(!feedbackRepository.existsById(feedbackSave.getId())) {
			return null;
		}
		
		return feedbackMapper.entityToDto(feedbackSave);
	}

	@Override
	public boolean remove(long id) {
		//If id is less than or equal to 0, return false
		if(id <= 0) {
			return false;
		}
		//If the data does not exist, return false
		if(!feedbackRepository.existsById(id)) {
			return false;
		}
		//Clear data based on input
		feedbackRepository.deleteById(id);
		//If the deleted data still exists, return false
		if(feedbackRepository.existsById(id)) {
			return false;
		}
		return true;
	}

}
