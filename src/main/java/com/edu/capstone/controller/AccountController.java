package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.entity.Role;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.response.AccountResponse;
import com.edu.capstone.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private ClassSubjectRepository csRepo;
	
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
			List<ClassSubject> csubjectss = csRepo.findByKeyClasssId(sclass.getId());
			for (ClassSubject subject : csubjectss) {
				subjects.add(subject.getSubject().getSubjectCode());
			}
		}
		AccountResponse response = AccountResponse.builder()
				.accountId(account.getId())
				.roles(roles)
				.classs(classs)
				.subjects(subjects)
				.build();
		return response;
	}
	
	@PutMapping
	public void inactiveAcount(@RequestBody List<String> accountIds) {
		accountService.inactiveAccount(accountIds);
	}
	
	@GetMapping("/allactive")
	public List<AccountResponse> getActiveAccount() {
		return accountService.getActiveAccounts();
	}
	
	@GetMapping("/teachers")
	public List<AccountResponse> getTeachers() {
		return accountService.getTeachers();
	}
	
	@GetMapping("/students")
	public List<AccountResponse> getStudents() {
		return accountService.getStudents();
	}

}
