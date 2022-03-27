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

import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Schedule;
import com.edu.capstone.entity.key.CSKey;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.request.CreateScheduleRequest;
import com.edu.capstone.request.ImportScheduleRequest;
import com.edu.capstone.response.ScheResponse;
import com.edu.capstone.response.ScheSubResponse;
import com.edu.capstone.response.ScheduleResponse;
import com.edu.capstone.service.AccountService;
import com.edu.capstone.service.ProfileService;
import com.edu.capstone.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ClassSubjectRepository csRepo;
	
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
	
	@GetMapping("/byaccountid")
	public List<ScheResponse> getScheduleByAccountId(@RequestParam("accountId") String accountId) {
		List<Schedule> list = scheduleService.getByAccountId(accountId);
		List<ScheResponse> responses = new ArrayList<>();
		for (Schedule schedule : list) {
			ClassSubject classSubject = csRepo.findById(CSKey.builder().classsId(schedule.getClasss().getId()).subjectId(schedule.getSubject().getId()).build()).get();
			ScheSubResponse subRes = ScheSubResponse.builder()
					.id(schedule.getSubject().getId())
					.name(schedule.getSubject().getName())
					.code(schedule.getSubject().getSubjectCode())
					.startDate(classSubject.getDateStart())
					.endDate(classSubject.getDateEnd())
					.build();
			ScheResponse response = ScheResponse.builder()
					.id(schedule.getId())
					.timeStart(schedule.getTimeStart())
					.timeEnd(schedule.getTimeEnd())
					.room(schedule.getRoom())
					.classId(schedule.getClasss().getId())
					.teacherName(profileService.findByAccountId(accountId).getName())
					.status(schedule.getStatus())
					.build();
			responses.add(response);
		}
		return responses;
	}
	
	@GetMapping("/byclassid")
	public List<ScheResponse> getScheduleByClassId(@RequestParam("classId") String classId) {
		List<Schedule> list = scheduleService.getByClassId(classId);
		List<ScheResponse> responses = new ArrayList<>();
		for (Schedule schedule : list) {
			ClassSubject classSubject = csRepo.findById(CSKey.builder().classsId(schedule.getClasss().getId()).subjectId(schedule.getSubject().getId()).build()).get();
			ScheSubResponse subRes = ScheSubResponse.builder()
					.id(schedule.getSubject().getId())
					.name(schedule.getSubject().getName())
					.code(schedule.getSubject().getSubjectCode())
					.startDate(classSubject.getDateStart())
					.endDate(classSubject.getDateEnd())
					.build();
			ScheResponse response = ScheResponse.builder()
					.id(schedule.getId())
					.timeStart(schedule.getTimeStart())
					.timeEnd(schedule.getTimeEnd())
					.room(schedule.getRoom())
					.classId(schedule.getClasss().getId())
					.teacherName(profileService.findByAccountId(schedule.getTeacher().getId()).getName())
					.status(schedule.getStatus())
					.build();
			responses.add(response);
		}
		return responses;
	}

}
