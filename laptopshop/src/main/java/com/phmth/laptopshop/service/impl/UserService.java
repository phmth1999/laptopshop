package com.phmth.laptopshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.phmth.laptopshop.dto.UserDto;
import com.phmth.laptopshop.dto.request.AddUserRequest;
import com.phmth.laptopshop.dto.request.EditProfileRequest;
import com.phmth.laptopshop.dto.request.EditUserRequest;
import com.phmth.laptopshop.dto.request.SearchUserRequest;
import com.phmth.laptopshop.dto.request.SignupRequest;
import com.phmth.laptopshop.entity.RoleEntity;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.enums.AuthenticationType;
import com.phmth.laptopshop.enums.StateUser;
import com.phmth.laptopshop.exception.UserException;
import com.phmth.laptopshop.mapper.UserMapper;
import com.phmth.laptopshop.repository.IRoleRepository;
import com.phmth.laptopshop.repository.IUserRepository;
import com.phmth.laptopshop.service.IUserService;

import groovy.util.logging.Slf4j;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
@Slf4j
@Transactional
public class UserService implements IUserService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IRoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private UserMapper userMapper = new UserMapper();

	@Override
	public Page<UserDto> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);
		
		Page<UserEntity> listUserEntity = userRepository.findAll(pageable);
		
		Page<UserDto> listUserDto = listUserEntity.map(new Function<UserEntity, UserDto>(){
			@Override
			public UserDto apply(UserEntity userEntity) {
				return userMapper.entityToDto(userEntity);
			}
		});

		return listUserDto;
	}

	@Override
	public Page<UserDto> findAll(int page, int limit, SearchUserRequest formSearchUser) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);

		Specification<UserEntity> specification = new Specification<UserEntity>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();

				if (!formSearchUser.getFullname().isBlank()) {
					predicates
							.add(criteriaBuilder.like(root.get("fullname"), "%" + formSearchUser.getFullname() + "%"));
				}

				if (!formSearchUser.getSex().isBlank()) {
					predicates.add(criteriaBuilder.equal(root.get("sex"), formSearchUser.getSex()));
				}

				if (!formSearchUser.getBirthday().isBlank()) {
					predicates.add(criteriaBuilder.equal(root.get("birthday"), formSearchUser.getBirthday()));
				}

				if (!formSearchUser.getAddress().isBlank()) {
					predicates.add(criteriaBuilder.like(root.get("address"), "%" + formSearchUser.getAddress() + "%"));
				}

				if (!formSearchUser.getEmail().isBlank()) {
					predicates.add(criteriaBuilder.equal(root.get("email"), formSearchUser.getEmail()));
				}

				if (formSearchUser.getStateUser() != null) {
					predicates.add(criteriaBuilder.equal(root.get("stateUser"), formSearchUser.getStateUser()));
				}

				if (formSearchUser.getAuthType() != null) {
					predicates.add(criteriaBuilder.equal(root.get("authType"), formSearchUser.getAuthType()));
				}

				if (formSearchUser.getRole() != null && formSearchUser.getRole().longValue() > 0) {
					predicates.add(criteriaBuilder.equal(root.get("role"),
							roleRepository.findById(formSearchUser.getRole()).get()));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}

		};
		
		Page<UserEntity> listUserEntity = userRepository.findAll(specification, pageable);
		
		Page<UserDto> listUserDto = listUserEntity.map(new Function<UserEntity, UserDto>(){
			@Override
			public UserDto apply(UserEntity userEntity) {
				return userMapper.entityToDto(userEntity);
			}
		});

		return listUserDto;
	}

	@Override
	public Optional<UserDto> findById(long id) {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		
		if(userEntity.isEmpty()) {
			return null;
		}
		
		Optional<UserDto> userDto = userEntity.map(new Function<UserEntity, UserDto>(){
			@Override
			public UserDto apply(UserEntity userEntity) {
				return userMapper.entityToDto(userEntity);
			}
		});
		
		return userDto;
	}
	
	@Override
	public Optional<UserDto> findByResetPasswordToken(String token) {
		Optional<UserEntity> userEntity = userRepository.findByResetPasswordToken(token);
		
		if(userEntity.isEmpty()) {
			return null;
		}
		
		Optional<UserDto> userDto = userEntity.map(new Function<UserEntity, UserDto>(){
			@Override
			public UserDto apply(UserEntity userEntity) {
				return userMapper.entityToDto(userEntity);
			}
		});
		
		return userDto;
	}

	@Override
	public boolean update(EditProfileRequest formProfile) {
		// If the input is null, throw exception
		if (formProfile == null) {
			throw new UserException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formProfile.isEmpty()) {
			throw new UserException("The input is empty!");
		}
		
		// If the data to be modified is not found, throw exception
		Optional<UserEntity> oldUserEntity = userRepository.findById(formProfile.getId());
		if (oldUserEntity.isEmpty()) {
			throw new UserException("The data to be modified is not found!");
		}
				
		// If insert data failed, return null
		oldUserEntity.get().setId(formProfile.getId());
		oldUserEntity.get().setFullname(formProfile.getFullname());
		oldUserEntity.get().setAddress(formProfile.getAddress());
		oldUserEntity.get().setImg(formProfile.getImg());
		oldUserEntity.get().setSex(formProfile.getSex());
		oldUserEntity.get().setBirthday(formProfile.getBirthday());
		oldUserEntity.get().setUpdate_at(new Date());
		
		UserEntity userSave = userRepository.save(oldUserEntity.get());
		if (userSave == null) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public UserDto insert(AddUserRequest formAddUser) {
		// If the input is null, throw exception
		if (formAddUser == null) {
			throw new UserException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formAddUser.isEmpty()) {
			throw new UserException("The input is empty!");
		}

		// If the email already exists, throw exception
		if (userRepository.existsByEmail(formAddUser.getEmail())) {
			throw new UserException("The email already exists!");
		}

		// If insert data failed, return null
		UserEntity userEntity = new UserEntity();
		userEntity.setFullname(formAddUser.getFullname());
		userEntity.setEmail(formAddUser.getEmail());
		userEntity.setHash_password(passwordEncoder.encode(formAddUser.getPassword()));
		userEntity.setStateUser(StateUser.ACTIVED);
		userEntity.setAuthType(AuthenticationType.DATABASE);
		userEntity.setRole(roleRepository.findById(formAddUser.getRole()).get());
		userEntity.setCreated_at(new Date());
		
		UserEntity userSave = userRepository.save(userEntity);
		if (!userRepository.existsById(userSave.getId())) {
			return null;
		}

		return userMapper.entityToDto(userSave);
	}
	
	@Override
	public UserDto register(SignupRequest formSignup, String token, long tokenExpireAt) {
		// If the input is null, throw exception
		if (formSignup == null) {
			throw new UserException("The input is null!");
		}
				
		// If the input is empty, throw exception
		if (formSignup.isEmpty()) {
			throw new UserException("The input is empty!");
		}
		
		// If the email already exists, throw exception
		Optional<UserEntity> userActive = userRepository.findByEmailAndStateUserAndAuthType(formSignup.getEmail(), StateUser.ACTIVED, AuthenticationType.DATABASE);
		if (!userActive.isEmpty()) {
			throw new UserException("The email already exists!");
		}
		Optional<UserEntity> userPending = userRepository.findByEmailAndStateUserAndAuthType(formSignup.getEmail(), StateUser.PENDING, AuthenticationType.DATABASE);
		if(!userPending.isEmpty()) {
			UserDto userDto = new UserDto();
			
			userDto.setId(userPending.get().getId());
			userDto.setEmail(userPending.get().getEmail());
			userDto.setFullname(userPending.get().getFullname());
			userDto.setSex(userPending.get().getSex());
			userDto.setBirthday(userPending.get().getBirthday());
			userDto.setImg(userPending.get().getImg());
			userDto.setStateUser(userPending.get().getStateUser());
			userDto.setRole(userPending.get().getRole().getName());
			
			return userDto;
		}
		
		// If insert data failed, return false
		UserEntity user = new UserEntity();
		user.setFullname(formSignup.getFullname());
		user.setHash_password(passwordEncoder.encode(formSignup.getPassword()));
		user.setEmail(formSignup.getEmail());
		user.setStateUser(StateUser.PENDING); 
		user.setAuthType(AuthenticationType.DATABASE);
		user.setCreated_at(new Date());
		RoleEntity roleEntity = roleRepository.findById((long) 2).get();
		user.setRole(roleEntity);
		user.setRegisterToken(token);
		user.setRegisterTokenExpireAt(tokenExpireAt);
		
		UserEntity userSave = userRepository.save(user);
		if(!userRepository.existsById(userSave.getId())) {
			return null;
		}
		
		return userMapper.entityToDto(userSave);
	}

	@Override
	public boolean update(EditUserRequest formEditUser) {
		// If the input is null, throw exception
		if (formEditUser == null) {
			throw new UserException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formEditUser.isEmpty()) {
			throw new UserException("The input is empty!");
		}

		// If the data to be modified is not found, throw exception
		Optional<UserEntity> oldUserEntity = userRepository.findById(formEditUser.getId());
		if (oldUserEntity.isEmpty()) {
			throw new UserException("The data to be modified is not found!");
		}

		// If saving modification fail, return false
		oldUserEntity.get().setFullname(formEditUser.getFullname());
		oldUserEntity.get().setSex(formEditUser.getSex());
		oldUserEntity.get().setBirthday(formEditUser.getBirthday());
		oldUserEntity.get().setAddress(formEditUser.getAddress());
		oldUserEntity.get().setStateUser(formEditUser.getStateUser());
		oldUserEntity.get().setAuthType(formEditUser.getAuthType());
		oldUserEntity.get().setRole(roleRepository.findById(formEditUser.getRole()).get());
		oldUserEntity.get().setImg(formEditUser.getImg());
		oldUserEntity.get().setUpdate_at(new Date());
		
		UserEntity userSave = userRepository.save(oldUserEntity.get());
		if (userSave == null) {
			return false;
		}

		return true;
	}
	
	@Override
	public boolean updateResetPasswordToken(String email, String token, long tokenExpireAt) {
		Optional<UserEntity> oldUserEntity = userRepository.findByEmailAndStateUserAndAuthType(email, StateUser.ACTIVED, AuthenticationType.DATABASE);
		if(!oldUserEntity.isEmpty()) {
			oldUserEntity.get().setResetPasswordToken(token);
			oldUserEntity.get().setResetPasswordTokenExpireAt(tokenExpireAt);
			
			UserEntity userSave = userRepository.save(oldUserEntity.get());
			if(userSave == null) {
				return false;
			}
			
			return true;
		}else {
			throw new UserException("Could not find any account with the email " + email);
		}
	}

	@Override
	public boolean updatePassword(long id, String newPassword) {
		UserEntity oldUserEntity = userRepository.findById(id).get();
		
		oldUserEntity.setHash_password(passwordEncoder.encode(newPassword));
		oldUserEntity.setResetPasswordToken(null);
		oldUserEntity.setResetPasswordTokenExpireAt(null);
		
		UserEntity userSave = userRepository.save(oldUserEntity);
		if(userSave == null) {
			return false;
		}
		
		return true;
	}
	

	@Override
	public boolean clockUser(long id) {
		// If the data to be modified is not found, throw exception
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (userEntity.isEmpty()) {
			throw new UserException("The data does not exist!");
		}

		// If saving modification fail, return false
		userEntity.get().setStateUser(StateUser.DISABLED);
		if (userRepository.save(userEntity.get()) == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean unClockUser(long id) {
		// If the data to be modified is not found, throw exception
		Optional<UserEntity> userEntity = userRepository.findById(id);
		if (userEntity.isEmpty()) {
			throw new UserException("The data does not exist!");
		}

		// If saving modification fail, return false
		userEntity.get().setStateUser(StateUser.ACTIVED);
		if (userRepository.save(userEntity.get()) == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkEmailExist(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean checkResetPasswordToken(String token) {
		Optional<UserEntity> userEntity = userRepository.findByResetPasswordToken(token);
		if(!userEntity.isEmpty()) {
			long tokenExpiredAt = userEntity.get().getResetPasswordTokenExpireAt();
			long currentTime = new Date().getTime();
			if(tokenExpiredAt - currentTime > 0) {
				return true;
			}else {
				userEntity.get().setResetPasswordToken(null);
				userEntity.get().setResetPasswordTokenExpireAt(null);
				userRepository.save(userEntity.get());
				throw new UserException("Your link is expired!");
			}
		}
		return false;
	}
	
	@Override
	public boolean checkRegisterToken(String token) {
		Optional<UserEntity> userEntity = userRepository.findByRegisterToken(token);
		if(!userEntity.isEmpty()) {
			long tokenExpiredAt = userEntity.get().getRegisterTokenExpireAt();
			long currentTime = new Date().getTime();
			
			if(tokenExpiredAt - currentTime > 0) {
				userEntity.get().setStateUser(StateUser.ACTIVED);
				userRepository.save(userEntity.get());
				return true;
			}else {
				if(userEntity.get().getStateUser().equals(StateUser.PENDING)) {
					userRepository.delete(userEntity.get());
				}
				throw new UserException("Your link is expired!");
			}
		}
		return false;
	}
}
