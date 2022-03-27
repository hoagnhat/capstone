package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.Profile;
import com.edu.capstone.entity.Schedule;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.request.CreateSubjectRequest;
import com.edu.capstone.request.SubjectRequest;
import com.edu.capstone.response.StudentResponse;
import com.edu.capstone.response.SubjectResponse;
import com.edu.capstone.service.ProfileService;
import com.edu.capstone.service.ScheduleService;
import com.edu.capstone.service.SpecializationService;
import com.edu.capstone.service.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private SpecializationService specService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private ScheduleService scheduleService;
	
	@GetMapping
	public List<SubjectResponse> getAll() {
		List<Subject> subjects = subjectService.getAll();
		List<SubjectResponse> responses = new ArrayList<>();
		for (Subject subject : subjects) {
			List<StudentResponse> teachers = new ArrayList<>();
			for (Account teacher : subject.getTeachers()) {
				Profile profile = profileService.findByAccountId(teacher.getId());
				StudentResponse teacherResponse = StudentResponse.builder()
						.accountId(teacher.getId())
						.name(profile.getName())
						.avatar(profile.getAvatar())
						.build();
				teachers.add(teacherResponse);
			}
			SubjectResponse response = SubjectResponse.builder()
					.id(subject.getId())
					.name(subject.getName())
					.semester(subject.getSemester())
					.subjectCode(subject.getSubjectCode())
					.build();
			List<String> specNameList = new ArrayList<>();
			for (Specialization spec : subject.getSpecializations()) {
				specNameList.add(specService.getNameCode(spec.getId()));
			}
			response.setSpecializations(specNameList);
			response.setTeachers(teachers);
			responses.add(response);
		}
		return responses; 
	}
	
	@GetMapping("/byid/{id}")
	public SubjectResponse getById(@RequestParam("id") int id) {
		Subject subject = subjectService.findById(id);
		List<StudentResponse> teachers = new ArrayList<>();
		for (Account teacher : subject.getTeachers()) {
			Profile profile = profileService.findByAccountId(teacher.getId());
			StudentResponse teacherResponse = StudentResponse.builder()
					.accountId(teacher.getId())
					.name(profile.getName())
					.avatar(profile.getAvatar())
					.build();
			teachers.add(teacherResponse);
		}
		SubjectResponse response = SubjectResponse.builder()
				.id(subject.getId())
				.name(subject.getName())
				.semester(subject.getSemester())
				.subjectCode(subject.getSubjectCode())
				.build();
		List<String> specNameList = new ArrayList<>();
		for (Specialization spec : subject.getSpecializations()) {
			specNameList.add(specService.getNameCode(spec.getId()));
		}
		response.setSpecializations(specNameList);
		response.setTeachers(teachers);
		return response;
	}
	
	@PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void create(@RequestBody CreateSubjectRequest request) {
		subjectService.create(request);
	}
	
	@PutMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void update(@RequestParam("subjectId") int subjectId, @RequestBody SubjectRequest request) {
		Set<Specialization> specializations = new HashSet<>();
		for (Integer i : request.getSpecializations()) {
			specializations.add(specService.findById(i));
		}
		subjectService.update(subjectId, request, specializations);
	}
	
	@DeleteMapping
	public void delete(@RequestParam("subjectId") int subjectId) {
		for (Schedule schedule : subjectService.findById(subjectId).getSchedules()) {
			scheduleService.delete(schedule.getId());
		}
		subjectService.delete(subjectId);
	}

}
