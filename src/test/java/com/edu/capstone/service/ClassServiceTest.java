package com.edu.capstone.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edu.capstone.entity.Subject;
import com.edu.capstone.request.AddCourseForClassRequest;
import com.edu.capstone.request.ClassRequest;
import com.edu.capstone.request.SpecializationRequest;

@SpringBootTest
@DisplayName("Class service test")
public class ClassServiceTest {
	
	@Autowired
	private ClassService classService;
	@Autowired
	private SpecializationService specService;
	@Autowired
	private SubjectService subjectService;
	
	@Test
	public void createClass() {
		ClassRequest request = ClassRequest.builder()
				.khoa(14)
				.specId(1)
				.size(10)
				.semester(1)
				.build();
		classService.create(request);
	}
	
	
	@Test
	public void createCourse() throws MessagingException {
		AddCourseForClassRequest request = AddCourseForClassRequest.builder()
				.subjectId(1)
				.classId("SE1401")
				.teacherId("1")
				.build();
		classService.addCourse(request);
	}
	
	@Test
	public void createSpec() {
		List<Integer> s = new ArrayList<>();
		s.add(1);
		s.add(2);
		SpecializationRequest request = SpecializationRequest.builder()
				.name("HELLO WORLD 5")
				.subjectId(s)
				.build();
		Set<Subject> subjects = new HashSet<>();
		for (Integer subjectId : request.getSubjectId()) {
			subjects.add(subjectService.findById(subjectId));
		}
		specService.create(request, subjects);
	}
	
	@Test
	public void updateSpec() {
		List<Integer> s = new ArrayList<>();
		s.add(1);
		SpecializationRequest request = SpecializationRequest.builder()
				.specId(13)
				.name("HELLO WORLD 6")
				.subjectId(s)
				.build();
		Set<Subject> subjects = new HashSet<>();
		for (Integer subjectId : request.getSubjectId()) {
			subjects.add(subjectService.findById(subjectId));
		}
		specService.update(request, subjects);
	}
	
	@Test
	public void deleteSubject() {
		int subjectId = 1;
		subjectService.delete(subjectId);
	}

}
