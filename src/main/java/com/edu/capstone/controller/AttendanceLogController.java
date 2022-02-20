package com.edu.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.request.AttendanceLogRequest;
import com.edu.capstone.service.AttendanceLogService;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceLogController {
	
	@Autowired
	private AttendanceLogService logService;
	
	@PostMapping("/take")
	public void takeAttendance(@RequestBody AttendanceLogRequest request) {
		logService.takeAttendance(request.getStudentId(), request.getSlotId(), request.getStatus());
	}

}
