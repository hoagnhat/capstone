package com.edu.capstone.request;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceLogRequest {
	
	private String studentId;
	private int slotId;
	private String status;

}
