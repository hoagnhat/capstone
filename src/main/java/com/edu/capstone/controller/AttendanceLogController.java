package com.edu.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.request.AttendanceLogRequest;
import com.edu.capstone.request.UpdateAttendanceLogRequest;
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
	
	@PostMapping(path = "/take", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void takeAttendance(@RequestBody AttendanceLogRequest request) {
		logService.takeAttendance(request.getStudentId(), request.getSlotId(), request.getStatus(), request.getDescription());
	}
	
	@PutMapping("/update")
	public void takAttendance(@RequestBody List<UpdateAttendanceLogRequest> requests) {
		for (UpdateAttendanceLogRequest request : requests) {
			logService.updateLog(request.getStudentId(), request.getSlotId(), request.getStatus());
		}
	}

}
