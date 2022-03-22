package com.edu.capstone.common.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.edu.capstone.common.security.service.CustomUserDetailsService;

/**
 * @author NhatHH Date: Jan 30, 2022
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Enable CORS and disable CSRF
		http
			.cors()
			.and()
			.csrf().disable();
		// Set unauthorized requests exception handler
		http
			.exceptionHandling()
				.authenticationEntryPoint((request, response, ex) -> {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getLocalizedMessage());
				})
			.and();
		// Set permissions on endpoints
		http
			.authorizeRequests()
				.antMatchers("/register", "/v3/**", "/swagger-ui/**")
					.permitAll()
				.anyRequest()
					.authenticated()
			.and()
				.formLogin()
					.loginProcessingUrl("/login")
					.usernameParameter("email")
					.passwordParameter("password")
					.permitAll()
			.and()
				.logout()
					.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK))
					.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(customUserDetailsService)
			.passwordEncoder(passwordEncoder);
	}	
}
