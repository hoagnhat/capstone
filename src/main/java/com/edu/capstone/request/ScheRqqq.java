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
public class ScheRqqq {
	
	private int room;
	private Date timeStart;
	private Date timeEnd;
	private String classId;
	private int subjectId;
	private String teacherId;
	private String status;

}
