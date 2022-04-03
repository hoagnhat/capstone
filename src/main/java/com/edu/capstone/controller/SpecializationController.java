package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.AttendanceLog;
import com.edu.capstone.entity.Schedule;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.repository.AttendanceLogRepository;
import com.edu.capstone.repository.ScheduleRepository;
import com.edu.capstone.request.CreateSpecRequest;
import com.edu.capstone.request.SpecializationRequest;
import com.edu.capstone.response.SpecResponse;
import com.edu.capstone.service.SpecializationService;
import com.edu.capstone.service.SubjectService;

@RestController
@RequestMapping("/specialization")
public class SpecializationController {

	@Autowired
	private SpecializationService specService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ScheduleRepository scheRepo;
	@Autowired
	private AttendanceLogRepository logRepo;

	@GetMapping
	public List<SpecResponse> getAll() {
		List<SpecResponse> responses = new ArrayList<>();
		List<Specialization> specializations = specService.getAll();
		for (Specialization spec : specializations) {
			Set<Subject> subjects = spec.getSubjects();
			Set<String> classsIds = new HashSet<>();
			Set<String> students = new HashSet<>();
			Set<String> subjectsss = new HashSet<>();
			for (Subject sub : subjects) {
				List<Schedule> schedules = scheRepo.findBySubjectId(sub.getId());
				for (Schedule sc : schedules) {
					classsIds.add(sc.getClasss().getId());
					List<AttendanceLog> logs = logRepo.findBySlotId(sc.getId());
					for (AttendanceLog log : logs) {
						students.add(log.getStudentId());
					}
				}
				subjectsss.add(sub.getSubjectCode());
			}
			SpecResponse response = SpecResponse.builder()
					.specId(spec.getId())
					.name(spec.getName())
					.classesID(classsIds)
					.studentCounts(students.size())
					.subjects(subjectsss)
					.build();
			responses.add(response);
		}
		return responses;
	}

	@GetMapping("/{id}")
	public SpecResponse getById(@RequestParam("id") int id) {
		Specialization spec = specService.findById(id);
		Set<Subject> subjects = spec.getSubjects();
		Set<String> classIds = new HashSet<>();
		Set<String> students = new HashSet<>();
		Set<String> subjectsss = new HashSet<>();
		for (Subject sub : subjects) {
			List<Schedule> schedules = scheRepo.findBySubjectId(sub.getId());
			for (Schedule sc : schedules) {
				classIds.add(sc.getClasss().getId());
				List<AttendanceLog> logs = logRepo.findBySlotId(sc.getId());
				for (AttendanceLog log : logs) {
					students.add(log.getStudentId());
				}
			}
			subjectsss.add(sub.getSubjectCode());
		}
		SpecResponse response = SpecResponse.builder()
				.specId(spec.getId())
				.name(spec.getName())
				.classesID(classIds)
				.studentCounts(students.size())
				.subjects(subjectsss)
				.build();
		return response;
	}

	@PostMapping
	public void create(@RequestBody CreateSpecRequest request) {
		Set<Subject> subjects = new HashSet<>();
		for (Integer subjectId : request.getSubjectId()) {
			subjects.add(subjectService.findById(subjectId));
		}
		specService.create(request, subjects);
	}

	@PutMapping
	public void update(@RequestBody SpecializationRequest request) {
		Set<Subject> subjects = new HashSet<>();
		for (Integer subjectId : request.getSubjectId()) {
			subjects.add(subjectService.findById(subjectId));
		}
		specService.update(request, subjects);
	}

}