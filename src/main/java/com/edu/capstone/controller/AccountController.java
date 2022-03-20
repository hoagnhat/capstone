package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.entity.Role;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.response.AccountResponse;
import com.edu.capstone.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping
	public AccountResponse getCurrentAccountInformation() {
		Account account = accountService.getCurrentAccount();
		List<String> roles = new ArrayList<>();
		List<String> classs = new ArrayList<>();
		List<String> subjects = new ArrayList<>();
		for (Role role : account.getRoles()) {
			roles.add(role.getRoleName());
		}
		for (Classs sclass : account.getClasses()) {
			classs.add(sclass.getId());
			for (Subject subject : sclass.getSubjects()) {
				subjects.add(subject.getSubjectCode());
			}
		}
		AccountResponse response = AccountResponse.builder()
				.accountId(account.getId())
				.email(account.getEmail())
				.isActived(account.getIsActived())
				.specialization(account.getSpecialization().getName())
				.roles(roles)
				.classs(classs)
				.subjects(subjects)
				.build();
		return response;
	}

}
