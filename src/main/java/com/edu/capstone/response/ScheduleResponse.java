package com.edu.capstone.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
	
	private int id;
	private Date timeStart;
	private Date timeEnd;
	private int room;
	private String classId;
	private String teacherName;
	private String subjectId;

}
