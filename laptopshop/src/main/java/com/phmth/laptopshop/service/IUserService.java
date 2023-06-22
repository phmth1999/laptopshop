package com.phmth.laptopshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.FormAddUser;
import com.phmth.laptopshop.dto.FormEditUser;
import com.phmth.laptopshop.dto.FormProfile;
import com.phmth.laptopshop.dto.FormSearchUser;
import com.phmth.laptopshop.dto.FormSignup;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.enums.AuthenticationType;
import com.phmth.laptopshop.enums.StateUser;

public interface IUserService {

	Page<UserEntity> findAll(int page, int limit);
	Page<UserEntity> findAll(int page, int limit, FormSearchUser formSearchUser);
	
	Optional<UserEntity> findById(long id);
	Optional<UserEntity> findByEmailAndStateUserAndAuthType(String email, StateUser actived, AuthenticationType database);
	Optional<UserEntity> findByResetPasswordToken(String token);

	UserEntity insert(FormAddUser formAddUser);
	UserEntity register(FormSignup formSignup);
	UserEntity update(FormProfile formProfile);

	boolean update(FormEditUser formEditUser);
	void updateResetPasswordToken(String email, String token, long tokenExpireAt);
	void updatePassword(UserEntity userEntity, String newPassword);

	boolean clockUser(long id);
	boolean unClockUser(long id);
	
	boolean checkEmailExist(String email);
	boolean checkResetPasswordToken(String token);
	boolean checkRegisterToken(String token);
	
	
}
