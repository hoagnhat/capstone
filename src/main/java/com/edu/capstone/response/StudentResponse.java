package com.edu.capstone.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
	
	private String accountId;
	private String name;
	private String avatar;
	private String phone;
	private String email;

}
