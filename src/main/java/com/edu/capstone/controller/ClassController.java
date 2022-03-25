package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.request.AddCourseForClassRequest;
import com.edu.capstone.request.AddStudentIntoClassRequest;
import com.edu.capstone.request.ClassRequest;
import com.edu.capstone.response.ClassResponse;
import com.edu.capstone.response.ClassSubjectResponse;
import com.edu.capstone.service.ClassService;
import com.edu.capstone.service.ProfileService;

@RestController
@RequestMapping("/class")
public class ClassController {
	
	@Autowired
	private ClassService classService;
	@Autowired
	private ClassSubjectRepository csRepo;
	@Autowired
	private ProfileService profileService;
	
	@GetMapping
	public List<Classs> getAll() {
		return classService.getAll();
	}
	
	@GetMapping("/{id}")
	public ClassResponse getById(@RequestParam("id") String id) {
		Classs classs = classService.getById(id);
		List<ClassSubjectResponse> classSubjectResponses = new ArrayList<>();
		List<String> students = new ArrayList<>();
		List<ClassSubject> subjects = csRepo.findByKeyClassId(classs.getId());
		for (Account student : classs.getStudents()) {
			students.add(profileService.findByAccountId(id).getName());
		}
		for (ClassSubject subject : subjects) {
			ClassSubjectResponse classSubjectResponse = ClassSubjectResponse.builder()
					.subjectCode(subject.getSubject().getSubjectCode())
					.teacherName(profileService.findByAccountId(subject.getTeacher().getId()).getName())
					.build();
			classSubjectResponses.add(classSubjectResponse);
		}
		ClassResponse response = ClassResponse.builder()
				.classId(classs.getId())
				.subjects(classSubjectResponses)
				.semester(classs.getSemester())
				.specialization(classs.getSpecialization().getName())
				.students(students)
				.build();
		return response;
	}
	
	@PostMapping("/create")
	public void addClass(@RequestBody ClassRequest request) {
		classService.create(request);
	}
	
	@PostMapping("/addstudent")
	public void addStudent(@RequestBody AddStudentIntoClassRequest request) {
		classService.addStudent(request);
	}
	
	@PostMapping("/addcourse")
	public void addCourseForClass(@RequestBody AddCourseForClassRequest request) {
		classService.addCourse(request);
	}

}
