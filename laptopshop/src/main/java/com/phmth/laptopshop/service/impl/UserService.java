package com.phmth.laptopshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

import com.phmth.laptopshop.dto.FormAddUser;
import com.phmth.laptopshop.dto.FormEditUser;
import com.phmth.laptopshop.dto.FormProfile;
import com.phmth.laptopshop.dto.FormSearchUser;
import com.phmth.laptopshop.dto.FormSignup;
import com.phmth.laptopshop.entity.RoleEntity;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.enums.AuthenticationType;
import com.phmth.laptopshop.enums.StateUser;
import com.phmth.laptopshop.exception.UserException;
import com.phmth.laptopshop.repository.IRoleRepository;
import com.phmth.laptopshop.repository.IUserRepository;
import com.phmth.laptopshop.service.IUserService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
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

	@Override
	public Page<UserEntity> findAll(int page, int limit) {
		Sort sort = Sort.by(Sort.Direction.DESC, "id");
		Pageable pageable = PageRequest.of(page - 1, limit, sort);

		return userRepository.findAll(pageable);
	}

	@Override
	public Page<UserEntity> findAll(int page, int limit, FormSearchUser formSearchUser) {
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
		return userRepository.findAll(specification, pageable);
	}

	@Override
	public Optional<UserEntity> findById(long id) {
		return userRepository.findById(id);
	}
	
	@Override
	public Optional<UserEntity> findByEmailAndStateUserAndAuthType(String email, StateUser actived, AuthenticationType database) {
		return userRepository.findByEmailAndStateUserAndAuthType(email, StateUser.ACTIVED, AuthenticationType.DATABASE);
	}
	
	@Override
	public Optional<UserEntity> findByResetPasswordToken(String token) {
		return userRepository.findByResetPasswordToken(token);
	}

	@Override
	public UserEntity update(FormProfile formProfile) {
		// If the input is null, throw exception
		if (formProfile == null) {
			throw new UserException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formProfile.isEmpty()) {
			throw new UserException("The input is empty!");
		}
		
		// If the data to be modified is not found, throw exception
		Optional<UserEntity> userEntity = userRepository.findById(formProfile.getId());
		if (userEntity.isEmpty()) {
			throw new UserException("The data to be modified is not found!");
		}
				
		// If insert data failed, return null
		userEntity.get().setId(formProfile.getId());
		userEntity.get().setFullname(formProfile.getFullname());
		userEntity.get().setAddress(formProfile.getAddress());
		userEntity.get().setImg(formProfile.getImg());
		userEntity.get().setSex(formProfile.getSex());
		userEntity.get().setBirthday(formProfile.getBirthday());
		userEntity.get().setUpdate_at(new Date());
		UserEntity userSave = userRepository.save(userEntity.get());
		if (userSave == null) {
			return null;
		}

		return userSave;
	}
	
	@Override
	public UserEntity insert(FormAddUser formAddUser) {
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
		if (userSave == null) {
			return null;
		}

		return userSave;
	}
	
	@Override
	public UserEntity register(FormSignup formSignup) {
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
			return userPending.get();
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
		String token = UUID.randomUUID().toString();
		user.setRegisterToken(token);
		long tokenExpireAt = new Date().getTime() + TimeUnit.MINUTES.toMillis(5);
		user.setRegisterTokenExpireAt(tokenExpireAt);
		
		return userRepository.save(user);
	}

	@Override
	public boolean update(FormEditUser formEditUser) {
		// If the input is null, throw exception
		if (formEditUser == null) {
			throw new UserException("The input is null!");
		}
		
		// If the input is empty, throw exception
		if (formEditUser.isEmpty()) {
			throw new UserException("The input is empty!");
		}

		// If the data to be modified is not found, throw exception
		Optional<UserEntity> userEntity = userRepository.findById(formEditUser.getId());
		if (userEntity.isEmpty()) {
			throw new UserException("The data to be modified is not found!");
		}

		// If saving modification fail, return false
		userEntity.get().setFullname(formEditUser.getFullname());
		userEntity.get().setSex(formEditUser.getSex());
		userEntity.get().setBirthday(formEditUser.getBirthday());
		userEntity.get().setAddress(formEditUser.getAddress());
		userEntity.get().setStateUser(formEditUser.getStateUser());
		userEntity.get().setAuthType(formEditUser.getAuthType());
		userEntity.get().setRole(roleRepository.findById(formEditUser.getRole()).get());
		userEntity.get().setImg(formEditUser.getImg());
		userEntity.get().setUpdate_at(new Date());
		if (userRepository.save(userEntity.get()) == null) {
			return false;
		}

		return true;
	}
	
	@Override
	public void updateResetPasswordToken(String email, String token, long tokenExpireAt) {
		Optional<UserEntity> userEntity = userRepository.findByEmailAndStateUserAndAuthType(email, StateUser.ACTIVED, AuthenticationType.DATABASE);
		if(!userEntity.isEmpty()) {
			userEntity.get().setResetPasswordToken(token);
			userEntity.get().setResetPasswordTokenExpireAt(tokenExpireAt);
		}else {
			throw new UserException("Could not find any account with the email " + email);
		}
	}

	@Override
	public void updatePassword(UserEntity userEntity, String newPassword) {
		userEntity.setHash_password(passwordEncoder.encode(newPassword));
		userEntity.setResetPasswordToken(null);
		userEntity.setResetPasswordTokenExpireAt(null);
		userRepository.save(userEntity);
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
				userRepository.delete(userEntity.get());
				throw new UserException("Your link is expired!");
			}
		}
		return false;
	}
}
