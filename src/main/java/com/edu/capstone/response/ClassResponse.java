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
public class ClassResponse {

	private String classId;
	private List<ClassSubjectResponse> subjects;
	private int semester;
	private String specialization;
	private List<String> students;
	
}
