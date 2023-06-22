package com.phmth.laptopshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.entity.FeedbackEntity;
import com.phmth.laptopshop.repository.IFeedbackRepository;
import com.phmth.laptopshop.service.IFeedbackService;

@Service
@Transactional
public class FeedbackService implements IFeedbackService{
	
	@Autowired
	private IFeedbackRepository feedbackRepository;


	@Override
	public Page<FeedbackEntity> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.ASC, "id");
		Pageable pageable = PageRequest.of(page-1, limit, sort);
		return feedbackRepository.findAll(pageable);
	}


	@Override
	public Iterable<FeedbackEntity> findAll() {
		return feedbackRepository.findAll();
	}


	@Override
	public Optional<FeedbackEntity> findOne(long id) {
		return feedbackRepository.findById(id);
	}


	@Override
	public FeedbackEntity insert(FeedbackEntity feedbackEntity) {
		//If the input is null, return null
		if(feedbackEntity == null) {
			return null;
		}
		return feedbackRepository.save(feedbackEntity);
	}


	@Override
	public boolean update(FeedbackEntity feedbackEntity) {
		//If the input is null, return false
		if(feedbackEntity == null) {
			return false;
		}
		//If the data to be modified is not found, return false
		FeedbackEntity feedbackUpdate = feedbackRepository.findById(feedbackEntity.getId()).get();
		if(feedbackUpdate == null) {
			return false;
		}
		//If saving modification fail, return false
		if(feedbackRepository.save(feedbackEntity) == null) {
			return false;
		}
		return true;
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


	@Override
	public boolean existsById(long id) {
		//If id is less than or equal to 0, return false
		if( id <= 0) {
			return false;
		}
		return feedbackRepository.existsById(id);
	}

}
