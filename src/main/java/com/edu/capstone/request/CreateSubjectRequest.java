package com.edu.capstone.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubjectRequest {

	private String name;
	private String subjectCode;
	private List<String> teacherIds;
	
}
