package com.phmth.laptopshop.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.enums.AuthenticationType;
import com.phmth.laptopshop.enums.StateUser;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long>{
	
	Page<UserEntity> findAll(Specification<UserEntity> specification, Pageable pageable);
	
	Optional<UserEntity> findByEmail(String email);

	Optional<UserEntity> findByEmailAndStateUserAndAuthType(String email, StateUser actived, AuthenticationType authType);
	
	Optional<UserEntity> findByRegisterToken(String token);
	
	Optional<UserEntity> findByResetPasswordToken(String token);
	
	boolean existsByEmail(String email);

}
