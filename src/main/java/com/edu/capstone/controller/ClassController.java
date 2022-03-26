package com.edu.capstone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.entity.Profile;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.request.AddCourseForClassRequest;
import com.edu.capstone.request.AddStudentIntoClassRequest;
import com.edu.capstone.request.ClassRequest;
import com.edu.capstone.response.ClassResponse;
import com.edu.capstone.response.ClassSubjectResponse;
import com.edu.capstone.response.StudentResponse;
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
	public List<ClassResponse> getAll() {
		List<ClassResponse> responses = new ArrayList<>();
		for (Classs classs : classService.getAll()) {
			List<ClassSubjectResponse> classSubjectResponses = new ArrayList<>();
			List<StudentResponse> students = new ArrayList<>();
			List<ClassSubject> subjects = csRepo.findByKeyClasssId(classs.getId());
			for (Account student : classs.getStudents()) {
				Profile profile = profileService.findByAccountId(student.getId());
				StudentResponse studentResponse = StudentResponse.builder()
						.accountId(student.getId())
						.name(profile.getName())
						.avatar(profile.getAvatar())
						.build();
				students.add(studentResponse);
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
			responses.add(response);
		}
		return responses;
	}
	
	@GetMapping("/{id}")
	public ClassResponse getById(@PathVariable("id") String id) {
		Classs classs = classService.findById(id);
		List<ClassSubjectResponse> classSubjectResponses = new ArrayList<>();
		List<StudentResponse> students = new ArrayList<>();
		List<ClassSubject> subjects = csRepo.findByKeyClasssId(classs.getId());
		for (Account student : classs.getStudents()) {
			Profile profile = profileService.findByAccountId(student.getId());
			StudentResponse studentResponse = StudentResponse.builder()
					.accountId(student.getId())
					.name(profile.getName())
					.avatar(profile.getAvatar())
					.build();
			students.add(studentResponse);
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
