package com.edu.capstone.service;

import javax.mail.MessagingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edu.capstone.request.AddCourseForClassRequest;
import com.edu.capstone.request.ClassRequest;

@SpringBootTest
@DisplayName("Class service test")
public class ClassServiceTest {
	
	@Autowired
	private ClassService classService;
	@Autowired
	private AccountService accountService;
	
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

}
