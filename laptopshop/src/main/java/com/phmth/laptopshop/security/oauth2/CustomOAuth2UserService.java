package com.phmth.laptopshop.security.oauth2;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.enums.AuthenticationType;
import com.phmth.laptopshop.enums.StateUser;
import com.phmth.laptopshop.repository.IRoleRepository;
import com.phmth.laptopshop.repository.IUserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		String oauth2ClientName = userRequest.getClientRegistration().getClientName();

		OAuth2User user = super.loadUser(userRequest);

		CustomOAuth2User customOAuth2User = new CustomOAuth2User(user);

		AuthenticationType authType = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());
		Optional<UserEntity> entity = userRepository.findByEmailAndStateUserAndAuthType(customOAuth2User.getEmail(), StateUser.ACTIVED, authType);
		if (entity.isEmpty()) {
			customOAuth2User.setId(registerOauth2(customOAuth2User, authType));
		} else {
			customOAuth2User.setId(entity.get().getId());
		}

		return customOAuth2User;
	}

	private long registerOauth2(CustomOAuth2User customOAuth2User, AuthenticationType authType) {
		
		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(customOAuth2User.getEmail());
		userEntity.setFullname(customOAuth2User.getName());
		userEntity.setImg(customOAuth2User.getImg());
		userEntity.setRole(roleRepository.findById((long)2).get());
		userEntity.setCreated_at(new Date());
		userEntity.setStateUser(StateUser.ACTIVED);
		userEntity.setAuthType(authType);
		
		return userRepository.save(userEntity).getId();
	}

}
