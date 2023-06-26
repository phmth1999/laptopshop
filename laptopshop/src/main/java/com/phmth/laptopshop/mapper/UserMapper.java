package com.phmth.laptopshop.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.phmth.laptopshop.dto.UserDto;
import com.phmth.laptopshop.entity.RoleEntity;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.repository.IRoleRepository;

public class UserMapper {
	
	@Autowired
	private IRoleRepository roleRepository;

	public UserDto entityToDto(UserEntity userEntity) {
		UserDto userDto = new UserDto();
		
		userDto.setId(userEntity.getId());
		userDto.setEmail(userEntity.getEmail());
		userDto.setFullname(userEntity.getFullname());
		userDto.setSex(userEntity.getSex());
		userDto.setBirthday(userEntity.getBirthday());
		userDto.setAddress(userEntity.getAddress());
		userDto.setImg(userEntity.getImg());
		userDto.setStateUser(userEntity.getStateUser());
		userDto.setRole(userEntity.getRole().getName());
		
		return userDto;
	}
	
	public UserEntity entityToDto(UserDto userDto) {
		UserEntity userEntity = new UserEntity();
		
		userEntity.setId(userDto.getId());
		userEntity.setEmail(userDto.getEmail());
		userEntity.setFullname(userDto.getFullname());
		userEntity.setSex(userDto.getSex());
		userEntity.setBirthday(userDto.getBirthday());
		userEntity.setImg(userDto.getImg());
		userEntity.setStateUser(userDto.getStateUser());
		RoleEntity roleEntity = roleRepository.findByName(userDto.getRole());
		userEntity.setRole(roleEntity);
		
		return userEntity;
	}
}
