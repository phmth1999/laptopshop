package com.phmth.laptopshop.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.entity.NewEntity;
import com.phmth.laptopshop.repository.INewRepository;
import com.phmth.laptopshop.service.INewService;

@Service
public class NewService implements INewService{
	
	@Autowired
	private INewRepository newRepository;
	
	@Override
	public Page<NewEntity> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		return newRepository.findAll(pageable);
	}

	@Override
	public Optional<NewEntity> findOne(long id) {
		return newRepository.findById(id);
	}

	@Override
	public NewEntity insert(NewEntity newEntity) {
		if(newEntity == null) {
			return null;
		}
		if(newEntity.isEmpty()) {
			return null;
		}
		
		return newRepository.save(newEntity);
	}

	@Override
	public boolean update(NewEntity newEntity) {
		if(newEntity == null) {
			return false;
		}
		if(newEntity.isEmpty()) {
			return false;
		}
		Optional<NewEntity> entity = newRepository.findById(newEntity.getId());
		if(entity.isEmpty()) {
			return false;
		}
		newRepository.save(newEntity);
		return true;
	}

}
