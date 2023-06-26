package com.phmth.laptopshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.phmth.laptopshop.dto.UserDto;
import com.phmth.laptopshop.dto.request.AddUserRequest;
import com.phmth.laptopshop.dto.request.EditProfileRequest;
import com.phmth.laptopshop.dto.request.EditUserRequest;
import com.phmth.laptopshop.dto.request.SearchUserRequest;
import com.phmth.laptopshop.dto.request.SignupRequest;

public interface IUserService {

	Page<UserDto> findAll(int page, int limit);
	Page<UserDto> findAll(int page, int limit, SearchUserRequest formSearchUser);
	
	Optional<UserDto> findById(long id);
	Optional<UserDto> findByResetPasswordToken(String token);

	UserDto insert(AddUserRequest formAddUser);
	UserDto register(SignupRequest formSignup, String token, long tokenExpireAt);
	boolean update(EditProfileRequest formProfile);
	boolean update(EditUserRequest formEditUser);
	boolean updateResetPasswordToken(String email, String token, long tokenExpireAt);
	boolean updatePassword(long id, String newPassword);

	boolean clockUser(long id);
	boolean unClockUser(long id);
	
	boolean checkEmailExist(String email);
	boolean checkResetPasswordToken(String token);
	boolean checkRegisterToken(String token);
	
	
}
