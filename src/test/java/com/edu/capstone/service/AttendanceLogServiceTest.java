package com.edu.capstone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edu.capstone.common.constant.AppConstant;
import com.edu.capstone.entity.AttendanceLog;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.request.AccountRequest;
import com.edu.capstone.request.CreateScheduleRequest;
import com.edu.capstone.request.SpecializationRequest;
import com.edu.capstone.request.SubjectRequest;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@SpringBootTest
@DisplayName("Test account services")
public class AttendanceLogServiceTest {
	
	@Autowired
	private AttendanceLogService logService;
	@Autowired
	private ClassService classService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SpecializationService specializationService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private SubjectService subjectService;
	
	@Test
	public void testTakeAttendance() throws MessagingException, ParseException {
//		String studentId1;
//		String studentId2;
//		String studentId3;
//		String studentId4;
//		String classId;
//		int slotId;
//		String spec = "SOFTWARE ENGINEER";
//		String personalEmail = "hahng.nhat@gmail.com";
//		roleService.create(AppConstant.ROLE_STUDENT);
//		roleService.create(AppConstant.ROLE_TEACHER);
//		SpecializationRequest rq = SpecializationRequest.builder()
//				.name(spec)
//				.build();
//		specializationService.create(rq, new HashSet<>());
//		AccountRequest request1 = AccountRequest.builder()
//				.roleId(roleService.findByRoleName(AppConstant.ROLE_STUDENT).getId())
//				.specializationId(specializationService.findByName(spec).getId())
//				.personalEmail(personalEmail)
//				.name("Nguyen")
//				.age(21)
//				.phone("0123456789")
//				.gender(0)
//				.build();
//		AccountRequest request2 = AccountRequest.builder()
//				.roleId(roleService.findByRoleName(AppConstant.ROLE_STUDENT).getId())
//				.specializationId(specializationService.findByName(spec).getId())
//				.personalEmail(personalEmail)
//				.name("Thong")
//				.age(21)
//				.phone("0123456789")
//				.gender(0)
//				.build();
//		AccountRequest request3 = AccountRequest.builder()
//				.roleId(roleService.findByRoleName(AppConstant.ROLE_STUDENT).getId())
//				.specializationId(specializationService.findByName(spec).getId())
//				.personalEmail(personalEmail)
//				.name("My")
//				.age(21)
//				.phone("0123456789")
//				.gender(1)
//				.build();
//		AccountRequest request4 = AccountRequest.builder()
//				.roleId(roleService.findByRoleName(AppConstant.ROLE_TEACHER).getId())
//				.specializationId(specializationService.findByName(spec).getId())
//				.personalEmail(personalEmail)
//				.name("Tran")
//				.age(30)
//				.phone("0123456789")
//				.gender(1)
//				.build();
//		studentId1 = accountService.create(request1);
//		studentId2 = accountService.create(request2);
//		studentId3 = accountService.create(request3);
//		studentId4 = accountService.create(request4);
//		
//		classId = classService.create(1, specializationService.findByName(spec).getId());
//		classService.addStudent(studentId1, classId);
//		classService.addStudent(studentId2, classId);
//		classService.addStudent(studentId3, classId);
//		classService.addStudent(studentId4, classId);
//		
//		Set<Specialization> specializations = new HashSet<>();
//		specializations.add(specializationService.findByName(spec));
//		SubjectRequest requestt = SubjectRequest.builder()
//				.name("Hello World")
//				.subjectCode("HW001")
//				.semester(1)
//				.build();
//		int subjectId = subjectService.create(requestt, specializations);
//		
//		CreateScheduleRequest request = CreateScheduleRequest.builder()
//				.timeStart(new Date())
//				.timeEnd(new Date())
//				.classId(classId)
//				.subjectId(subjectId)
//				.teacherId(studentId4)
//				.room(201)
//				.build();
//		slotId = scheduleService.create(request);
//		
//		logService.takeAttendance(studentId1, slotId, AppConstant.ATTENDANCE_ABSENT_STATUS);
//		logService.takeAttendance(studentId2, slotId, AppConstant.ATTENDANCE_ABSENT_STATUS);
//		logService.takeAttendance(studentId3, slotId, AppConstant.ATTENDANCE_PRESENT_STATUS);
//		
//		List<AttendanceLog> logs = logService.getLogBySlotId(slotId);
//		assertEquals(logs.get(0).getStatus(), AppConstant.ATTENDANCE_ABSENT_STATUS);
//		assertEquals(logs.get(1).getStatus(), AppConstant.ATTENDANCE_ABSENT_STATUS);
//		assertEquals(logs.get(2).getStatus(), AppConstant.ATTENDANCE_PRESENT_STATUS);
	}
	
}
