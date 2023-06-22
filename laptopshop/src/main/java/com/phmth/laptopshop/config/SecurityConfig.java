package com.phmth.laptopshop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.phmth.laptopshop.security.DatabaseLoginSuccessHandler;
import com.phmth.laptopshop.security.CustomUserService;
import com.phmth.laptopshop.security.oauth2.CustomOAuth2UserService;
import com.phmth.laptopshop.security.oauth2.OAuthLoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	
	
	@Autowired
	private CustomUserService  userDetailsService;
	
	@Autowired
    private DatabaseLoginSuccessHandler databaseLoginSuccessHandler;
	
	@Autowired
    private CustomOAuth2UserService oauth2UserService;
     
    @Autowired
    private OAuthLoginSuccessHandler oauthLoginSuccessHandler;
	
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	      auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	 }

	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        return http.csrf().disable()
	                .authorizeHttpRequests()
	                	.requestMatchers(
	                	"/home", "/store", "/store/**", "/cart/**", "/search/**", "/new/**",
	                	"/contact", "/about",
	                	"/auth/**", "/oauth2/**",
	                	"/logo.svg", "/images/**", "/web/css/**", "/web/js/**",  "/admin/css/**", "/admin/js/**")
	                	.permitAll()
	                	.requestMatchers("/admin/**").hasRole("ADMIN")
	                	.requestMatchers("/**").hasAnyRole("ADMIN", "USER")
	                	.anyRequest().authenticated()
	                .and()
	                	.formLogin()
	                	.loginPage("/auth/sign-in")
	                	.loginProcessingUrl("/j_spring_security_check")
	                	.usernameParameter("username")
	                	.passwordParameter("password")
	                	.successHandler(databaseLoginSuccessHandler)
	                .and()
		            	.oauth2Login()
		            	.loginPage("/auth/sign-in")
		            	.userInfoEndpoint()
	                    	.userService(oauth2UserService)
	                    .and()
	                	.successHandler(oauthLoginSuccessHandler)
	                .and()
		                .logout()
		                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		                .logoutSuccessUrl("/auth/sign-in?logout")
		                .invalidateHttpSession(true)
		                .deleteCookies("JSESSIONID")
		            .and()
		            	.exceptionHandling().accessDeniedPage("/auth/sign-in?accessDenied")
	                .and().build();
	    }

}
