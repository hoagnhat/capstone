package com.edu.capstone.response;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecResponse {
	
	private int specId;
	private String name;
	private int teacherCounts;
	private int studentCounts;
	private Set<String> subjects;
	
}
