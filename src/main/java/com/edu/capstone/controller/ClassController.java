package com.edu.capstone.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.edu.capstone.entity.Schedule;
import com.edu.capstone.entity.key.CSKey;
import com.edu.capstone.repository.AccountRepository;
import com.edu.capstone.repository.ClassRepository;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.repository.ScheduleRepository;
import com.edu.capstone.request.AddCourseForClassRequest;
import com.edu.capstone.request.AddStudentIntoClassRequest;
import com.edu.capstone.request.ClassRequest;
import com.edu.capstone.response.ClassResponse;
import com.edu.capstone.response.ClassSubjectResponse;
import com.edu.capstone.response.ClasssRes;
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
	@Autowired
	private AccountRepository accRepo;
	@Autowired
	private ClassRepository classRepo;
	@Autowired
	private ScheduleRepository scheRepo;
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
						.phone(profile.getPhone())
						.age(profile.getAge())
						.gender(profile.getGender())
						.address(profile.getAddress())
						.email(student.getEmail())
						.personalEmail(profile.getPersonalEmail())
						.build();
				students.add(studentResponse);
			}
			for (ClassSubject subject : subjects) {
				ClassSubjectResponse classSubjectResponse = ClassSubjectResponse.builder()
						.subjectId(subject.getSubject().getId())
						.subjectName(subject.getSubject().getName())
						.subjectCode(subject.getSubject().getSubjectCode())
						.teacherName(profileService.findByAccountId(subject.getTeacher().getId()).getName())
						.startDate(subject.getDateStart())
						.endDate(subject.getDateEnd())
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
					.phone(profile.getPhone())
					.age(profile.getAge())
					.gender(profile.getGender())
					.address(profile.getAddress())
					.email(student.getEmail())
					.personalEmail(profile.getPersonalEmail())
					.build();
			students.add(studentResponse);
		}
		for (ClassSubject subject : subjects) {
			ClassSubjectResponse classSubjectResponse = ClassSubjectResponse.builder()
					.subjectId(subject.getSubject().getId())
					.subjectName(subject.getSubject().getName())
					.subjectCode(subject.getSubject().getSubjectCode())
					.teacherName(profileService.findByAccountId(subject.getTeacher().getId()).getName())
					.startDate(subject.getDateStart())
					.endDate(subject.getDateEnd())
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
	
	@DeleteMapping("/deletecourse")
	public void deleteCourseOutClass(@RequestParam("classId") String classId, @RequestParam("subjectId") int subjectId) {
		ClassSubject cs = csRepo.findByKeyClasssIdAndKeySubjectId(classId, subjectId);
		cs.setSubject(null);
		cs.setTeacher(null);
		csRepo.deleteById(CSKey.builder().classsId(classId).subjectId(subjectId).build());
	}
	
	@DeleteMapping("/deletestudent")
	public void deleteStudentOutClass(@RequestParam("classId") String classId, @RequestParam("studentId") String accountId) {
		Classs classs = classService.findById(classId);
		classs.getStudents().remove(accRepo.findById(accountId).get());
		classRepo.saveAndFlush(classs);
	}
	
	@GetMapping("/ongoing")
	public List<ClasssRes> getOnGoingClass() {
		LocalDateTime now = LocalDateTime.now();		
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowStr = now.format(formatter1);
		LocalDateTime nowEnd = LocalDateTime.parse(nowStr + "T23:59:59");		
		List<Schedule> schedules = scheRepo.findByTimeEndBetween(convertToDateViaInstant(now), convertToDateViaInstant(nowEnd));
		List<ClasssRes> responses = new ArrayList<>();
		for (Schedule schedule : schedules) {
			Classs classs = schedule.getClasss();
			ClasssRes response = ClasssRes.builder()
					.classId(classs.getId())
					.room(schedule.getRoom())
					.teacherName(profileService.findByAccountId(schedule.getTeacher().getId()).getName())
					.subjectName(schedule.getSubject().getName())
					.build();
			responses.add(response);
		}
		return responses;
	}
	
	public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	public Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant());
	}	
	@GetMapping("/simple")
	public List<ClassResponse> getClassSimple() {
		List<ClassResponse> responses = new ArrayList<>();
		for (Classs classs : classService.getAll()) {
			List<ClassSubjectResponse> classSubjectResponses = new ArrayList<>();			
			List<ClassSubject> subjects = csRepo.findByKeyClasssId(classs.getId());			
			for (ClassSubject subject : subjects) {
				ClassSubjectResponse classSubjectResponse = ClassSubjectResponse.builder()
						.subjectId(subject.getSubject().getId())
						.subjectName(subject.getSubject().getName())
						.subjectCode(subject.getSubject().getSubjectCode())
						.teacherName(profileService.findByAccountId(subject.getTeacher().getId()).getName())
						.startDate(subject.getDateStart())
						.endDate(subject.getDateEnd())
						.build();
				classSubjectResponses.add(classSubjectResponse);
			}
			ClassResponse response = ClassResponse.builder()
					.classId(classs.getId())
					.subjects(classSubjectResponses)
					.semester(classs.getSemester())										
					.build();
			responses.add(response);
		}
		return responses;
	}
}
