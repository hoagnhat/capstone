package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.AttendanceLog;
import com.edu.capstone.entity.Profile;
import com.edu.capstone.request.AttendanceLogRequest;
import com.edu.capstone.request.UpdateAttendanceLogRequest;
import com.edu.capstone.response.LogResponse;
import com.edu.capstone.service.AttendanceLogService;
import com.edu.capstone.service.ProfileService;

/**
 * @author NhatHH Date: Feb 20, 2022
 */
@RestController
@RequestMapping("/attendance")
public class AttendanceLogController {

	@Autowired
	private AttendanceLogService logService;
	@Autowired
	private ProfileService profileService;

	@PostMapping(path = "/take")
	public void takeAttendance(@RequestBody List<AttendanceLogRequest> request) {
		for (AttendanceLogRequest rq : request) {
			logService.takeAttendance(rq.getStudentId(), rq.getSlotId(), rq.getStatus(), rq.getDescription());
		}
	}

	@PutMapping("/update")
	public void takAttendance(@RequestBody List<UpdateAttendanceLogRequest> requests) {
		for (UpdateAttendanceLogRequest request : requests) {
			logService.updateLog(request);
		}
	}

	@GetMapping
	public List<LogResponse> getBySlotId(@RequestParam("id") int slotId) {
		List<AttendanceLog> logs = logService.getLogBySlotId(slotId);
		List<LogResponse> responses = new ArrayList<>();
		for (AttendanceLog log : logs) {
			Profile a = profileService.findByAccountId(log.getStudentId());
			LogResponse response = LogResponse.builder().accountId(log.getStudentId()).name(a.getName())
					.avatar(a.getAvatar()).status(log.getStatus()).description(log.getDescription()).build();
			responses.add(response);
		}
		return responses;
	}
	@GetMapping("/bySlotIdAndStudentId")
	public LogResponse getBySlotIdAndStudentId(@RequestParam("id") int slotId, @RequestParam("studentId") String studentId) {				
		AttendanceLog attendanceLog = logService.getBySlotIdAndStudentId(studentId, slotId);
		LogResponse responses = LogResponse.builder().status(attendanceLog.getStatus()).description(attendanceLog.getDescription()).build();
		return responses;
	}
}
