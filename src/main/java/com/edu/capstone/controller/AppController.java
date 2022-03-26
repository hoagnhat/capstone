package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.Profile;
import com.edu.capstone.entity.Role;
import com.edu.capstone.request.AccountRequest;
import com.edu.capstone.request.ChangePasswordRequest;
import com.edu.capstone.request.LoginRequest;
import com.edu.capstone.response.AccountResponse;
import com.edu.capstone.service.AccountService;
import com.edu.capstone.service.ProfileService;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@RestController
public class AppController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private ProfileService profileService;
	
	@GetMapping
	public AccountResponse success() {
		Account account = accountService.getCurrentAccount();
		List<String> roles = new ArrayList<>();
		Profile profile = profileService.findByAccountId(account.getId());
		for (Role role : account.getRoles()) {
			roles.add(role.getRoleName());
		}
		return AccountResponse.builder()
				.accountId(account.getId())
				.name(profile.getName())
				.roles(roles)
				.build();
	}
	
	@PostMapping(path = "/register", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void register(@RequestBody AccountRequest request) throws MessagingException {
		accountService.create(request);
	}
		
	@PostMapping(path = "/login", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void fakeLogin(@RequestBody LoginRequest request) {
		// do login
	}
	
	@PostMapping(path = "/logout")
	public void fakeLogout() {
		// do logout
	}
	
	@PostMapping(path = "/changepassword", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void changePassword(@RequestBody ChangePasswordRequest request) {
		accountService.changePassword(request);
	}
	
}
