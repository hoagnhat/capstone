package com.edu.capstone.response;

import java.util.List;

import com.edu.capstone.entity.Subject;

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
	private String email;
	private int isActived;
	private List<String> roles;
	private String specialization;
	private List<String> classs;
	private List<String> subjects; 

}
