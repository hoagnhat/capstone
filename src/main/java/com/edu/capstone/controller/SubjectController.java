package com.edu.capstone.controller;

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

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Profile;
import com.edu.capstone.entity.Schedule;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.request.CreateSubjectRequest;
import com.edu.capstone.request.SubjectRequest;
import com.edu.capstone.response.StudentResponse;
import com.edu.capstone.response.SubjectResponse;
import com.edu.capstone.service.ClassService;
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
	@Autowired
	private ClassSubjectRepository csRepo;
	@Autowired
	private ClassService classService;
	
	@GetMapping
	public List<SubjectResponse> getAll() {
		List<Subject> subjects = subjectService.getAll();
		List<SubjectResponse> responses = new ArrayList<>();
		for (Subject subject : subjects) {
			List<StudentResponse> teachers = new ArrayList<>();
			List<String> classes = new ArrayList<>();
			for (ClassSubject cs : csRepo.findByKeySubjectId(subject.getId())) {
				classes.add(classService.findById(cs.getKey().getClasssId()).getId());
			}
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
			response.setClasses(classes);
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
	
	@PostMapping
	public void create(@RequestBody CreateSubjectRequest request) {
		subjectService.create(request);
	}
	
	@PutMapping
	public void update(@RequestParam("subjectId") int subjectId, @RequestBody SubjectRequest request) {
		subjectService.update(subjectId, request);
	}
	
	@DeleteMapping
	public void delete(@RequestParam("subjectId") int subjectId) {
		for (Schedule schedule : subjectService.findById(subjectId).getSchedules()) {
			scheduleService.delete(schedule.getId());
		}
		subjectService.delete(subjectId);
	}

}
