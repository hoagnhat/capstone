package com.edu.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Profile;
import com.edu.capstone.request.AccountRequest;
import com.edu.capstone.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@GetMapping
	public Profile getProfile(@RequestParam("accountId") String accountId) {
		return profileService.findByAccountId(accountId);
	}
	
	@PutMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void update(@RequestParam("accountId") String accountId, @RequestBody AccountRequest request) {
		profileService.update(accountId, request);
	}

}
