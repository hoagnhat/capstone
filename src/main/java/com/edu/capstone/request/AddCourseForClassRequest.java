package com.edu.capstone.request;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCourseForClassRequest {
	
	private int subjectId;
	private String classId;
	private String teacherId;
	private Date startDate;
	private Date endDate;

}
