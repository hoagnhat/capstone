package com.edu.capstone.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClasssRes {
	
	private String classId;
	private int room;
	private String teacherName;
	private String subjectName;

}
