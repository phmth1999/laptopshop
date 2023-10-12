package com.phmth.laptopshop.security.user;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.enums.AuthenticationType;
import com.phmth.laptopshop.enums.StateUser;
import com.phmth.laptopshop.exception.UserException;
import com.phmth.laptopshop.repository.IUserRepository;

@Service
public class CustomUserService implements UserDetailsService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CustomUserService.class);

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username == null) {
			throw new UserException("The input is null!");
		}

		if (username.isEmpty()) {
			throw new UserException("The input is empty!");
		}

		Optional<UserEntity> user = userRepository.findByEmailAndStateUserAndAuthType(username, StateUser.ACTIVED,AuthenticationType.DATABASE);
		if (user.isEmpty()) {
			throw new UserException("User not found with username: " + username + "!");
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.get().getRole().getName()));
		CustomUser userLogin = new CustomUser(user.get().getEmail(), user.get().getHash_password(), grantedAuthorities);
		userLogin.setId(user.get().getId());
		userLogin.setName(user.get().getFullname());
		userLogin.setThumbnail(user.get().getImg());
		
		return userLogin;
	}

}
