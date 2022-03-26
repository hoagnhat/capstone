package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.entity.Profile;
import com.edu.capstone.entity.Role;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.request.AccountRequest;
import com.edu.capstone.response.AccountResponse;
import com.edu.capstone.service.AccountService;
import com.edu.capstone.service.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ClassSubjectRepository csRepo;
	
	@GetMapping
	public AccountResponse getProfile() {
		Account account = accountService.getCurrentAccount();
		Profile profile = profileService.findByAccountId(account.getId());
		List<String> roles = new ArrayList<>();
		List<String> classs = new ArrayList<>();
		List<String> subjects = new ArrayList<>();
		for (Role role : account.getRoles()) {
			roles.add(role.getRoleName());
		}
		for (Classs sclass : account.getClasses()) {
			classs.add(sclass.getId());
			List<ClassSubject> csubjectss = csRepo.findByKeyClassId(sclass.getId());
			for (ClassSubject subject : csubjectss) {
				subjects.add(subject.getSubject().getSubjectCode());
			}
		}
		AccountResponse response = AccountResponse.builder()
				.accountId(account.getId())
				.name(profile.getName())
				.avatar(profile.getAvatar())
				.gender(profile.getGender())
				.phone(profile.getPhone())
				.address(profile.getAddress())
				.personalEmail(profile.getPersonalEmail())
				.roles(roles)
				.classs(classs)
				.subjects(subjects)
				.specialization(account.getSpecialization().getName())
				.build();
		return response;
	}
	
	@PutMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void update(@RequestParam("accountId") String accountId, @RequestBody AccountRequest request) {
		profileService.update(accountId, request);
	}

}
