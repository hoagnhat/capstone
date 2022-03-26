package com.edu.capstone.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAttendanceLogRequest {

	private String studentId;
	private int slotId;
	private String status;
	
}
