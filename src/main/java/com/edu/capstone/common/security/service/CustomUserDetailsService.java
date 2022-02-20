package com.edu.capstone.common.security.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.Role;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.service.AccountService;

/**
 * @author NhatHH
 * Date: Jan 30, 2022
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountService accountService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountService.findByEmail(username);
		// Check whether account is null
		if (account == null) {
			throw new EntityNotFoundException(ExceptionConstant.EMAIL_NOT_FOUND);
		}
		// Convert role string to role object
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : account.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return new User(account.getEmail(),
				account.getPassword(),
				account.getIsActived() == 1 ? true : false,
				true,
				true,
				account.getIsActived() == 1 ? true : false,
				grantedAuthorities);
	}

}
