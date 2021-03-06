package com.edu.capstone.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	private ClassSubjectRepository csRepo;
	
	@GetMapping("/ongoing")
	public List<ScheResponse> getOnGoingSchedule() {
		List<Schedule> schedules = scheduleService.getGoingOnSchedule();
		List<ScheResponse> responses = new ArrayList<>();
		for (Schedule schedule : schedules) {			
			ClassSubject classSubject = csRepo.findByKeyClasssIdAndKeySubjectId(schedule.getClasss().getId(), schedule.getSubject().getId());
			ScheSubResponse subRes = ScheSubResponse.builder()
					.id(schedule.getSubject().getId())
					.name(schedule.getSubject().getName())
					.code(schedule.getSubject().getSubjectCode())
					.build();
			if (classSubject.getDateStart() != null) {
				subRes.setStartDate(classSubject.getDateStart());
			}
			if (classSubject.getDateEnd() != null) {
				subRes.setEndDate(classSubject.getDateEnd());
			}
			ScheResponse response = ScheResponse.builder()
					.id(schedule.getId())
					.timeStart(schedule.getTimeStart())
					.timeEnd(schedule.getTimeEnd())
					.room(schedule.getRoom())
					.classId(schedule.getClasss().getId())
					.teacherName(profileService.findByAccountId(schedule.getTeacher().getId()).getName())
					.teacherId(schedule.getTeacher().getId())
					.totalStudents(schedule.getClasss().getStudents().size())
					.status(schedule.getStatus())
					.subject(subRes)
					.build();
			responses.add(response);
		}
		return responses;
	}
	
	@GetMapping("/upcoming")
	public List<ScheResponse> getUpcomingSchedule() {
		List<Schedule> schedules = scheduleService.getUpcomingSchedule();
		List<ScheResponse> responses = new ArrayList<>();
		for (Schedule schedule : schedules) {
			ClassSubject classSubject = csRepo.findByKeyClasssIdAndKeySubjectId(schedule.getClasss().getId(), schedule.getSubject().getId());
			ScheSubResponse subRes = ScheSubResponse.builder()
					.id(schedule.getSubject().getId())
					.name(schedule.getSubject().getName())
					.code(schedule.getSubject().getSubjectCode())
					.build();
			if (classSubject.getDateStart() != null) {
				subRes.setStartDate(classSubject.getDateStart());
			}
			if (classSubject.getDateEnd() != null) {
				subRes.setEndDate(classSubject.getDateEnd());
			}
			ScheResponse response = ScheResponse.builder()
					.id(schedule.getId())
					.timeStart(schedule.getTimeStart())
					.timeEnd(schedule.getTimeEnd())
					.room(schedule.getRoom())
					.classId(schedule.getClasss().getId())
					.teacherName(profileService.findByAccountId(schedule.getTeacher().getId()).getName())
					.status(schedule.getStatus())
					.subject(subRes)
					.build();
			responses.add(response);
		}
		return responses;
	}
	
	@PostMapping
	public void create(@RequestBody CreateScheduleRequest request) throws ParseException {
		scheduleService.create(request);
	}
	
	@PutMapping
	public void update(@RequestParam("slotId") int slotId, @RequestBody CreateScheduleRequest request) throws ParseException {
		scheduleService.update(slotId, request);
	}
	
	@PostMapping("/import")
	public void importSchedule(@RequestBody ImportScheduleRequest request) {
		scheduleService.importSchedule(request.getSlots());
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
					.subject(subRes)
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
					.subject(subRes)
					.build();
			responses.add(response);
		}
		return responses;
	}

	@DeleteMapping
	public void delete(@RequestParam("id") int id) {
		scheduleService.delete(id);
	}
	
}
