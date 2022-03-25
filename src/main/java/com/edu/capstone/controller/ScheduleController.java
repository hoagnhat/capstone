package com.edu.capstone.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Schedule;
import com.edu.capstone.request.CreateScheduleRequest;
import com.edu.capstone.request.ImportScheduleRequest;
import com.edu.capstone.response.ScheduleResponse;
import com.edu.capstone.service.ProfileService;
import com.edu.capstone.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ProfileService profileService;
	
	@GetMapping("/ongoing")
	public List<ScheduleResponse> getOnGoingSchedule() {
		List<Schedule> schedules = scheduleService.getGoingOnSchedule();
		List<ScheduleResponse> responses = new ArrayList<>();
		for (Schedule schedule : schedules) {
			ScheduleResponse response = ScheduleResponse.builder()
					.id(schedule.getId())
					.timeStart(schedule.getTimeStart())
					.timeEnd(schedule.getTimeEnd())
					.room(schedule.getRoom())
					.classId(schedule.getClasss().getId())
					.teacherName(profileService.findByAccountId(schedule.getTeacher().getId()).getName())
					.subjectId(schedule.getSubject().getSubjectCode())
					.build();
			responses.add(response);
		}
		return responses;
	}
	
	@GetMapping("/upcoming")
	public List<ScheduleResponse> getUpcomingSchedule() {
		List<Schedule> schedules = scheduleService.getUpcomingSchedule();
		List<ScheduleResponse> responses = new ArrayList<>();
		for (Schedule schedule : schedules) {
			ScheduleResponse response = ScheduleResponse.builder()
					.id(schedule.getId())
					.timeStart(schedule.getTimeStart())
					.timeEnd(schedule.getTimeEnd())
					.room(schedule.getRoom())
					.classId(schedule.getClasss().getId())
					.teacherName(profileService.findByAccountId(schedule.getTeacher().getId()).getName())
					.subjectId(schedule.getSubject().getSubjectCode())
					.build();
			responses.add(response);
		}
		return responses;
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void create(@RequestBody CreateScheduleRequest request) throws ParseException {
		scheduleService.create(request);
	}
	
	@PutMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void update(@RequestParam("slotId") int slotId, @RequestBody CreateScheduleRequest request) throws ParseException {
		scheduleService.update(slotId, request);
	}
	
	@PostMapping("/import")
	public void importSchedule(@RequestParam("schedules") List<ImportScheduleRequest> request) {
		scheduleService.importSchedule(request);
	}

}
