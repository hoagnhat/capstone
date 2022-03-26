package com.edu.capstone.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
	
	private String accountId;
	private String name;
	private String avatar;
	private int gender;
	private int age;
	private String email;
	private String phone;
	private String address;
	private String personalEmail;
	private List<String> roles;
	private List<String> classs;
	private List<String> subjects;
	private String specialization;

}
