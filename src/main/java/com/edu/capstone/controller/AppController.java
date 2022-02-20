package com.edu.capstone.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.request.AccountRequest;
import com.edu.capstone.service.AccountService;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@RestController
public class AppController {
	
	@Autowired
	private AccountService accountService;

	@PostMapping("/register")
	public void register(@RequestBody AccountRequest request) throws MessagingException {
		accountService.create(request);
	}
	
}
