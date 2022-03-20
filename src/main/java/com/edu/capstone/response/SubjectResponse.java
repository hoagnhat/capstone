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
public class SubjectResponse {

	private int id;
	private String name;
	private int semester;
	private String subjectCode;
	private List<String> specializations;
	
}
