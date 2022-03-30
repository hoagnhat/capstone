package com.edu.capstone.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubjectResponse {

	private int subjectId;
	private String subjectCode;
	private String teacherName;
		
}
