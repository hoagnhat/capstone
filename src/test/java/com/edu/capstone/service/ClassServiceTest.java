package com.edu.capstone.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.AttendanceLog;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.entity.Role;
import com.edu.capstone.entity.Schedule;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.entity.key.CSKey;
import com.edu.capstone.repository.AccountRepository;
import com.edu.capstone.repository.ClassRepository;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.repository.ScheduleRepository;
import com.edu.capstone.request.AddCourseForClassRequest;
import com.edu.capstone.request.AddStudentIntoClassRequest;
import com.edu.capstone.request.ClassRequest;
import com.edu.capstone.request.CreateSpecRequest;
import com.edu.capstone.request.SpecializationRequest;
import com.edu.capstone.response.ScheResponse;
import com.edu.capstone.response.ScheSubResponse;

@SpringBootTest
@DisplayName("Class service test")
public class ClassServiceTest {
	
	@Autowired
	private ClassService classService;
	@Autowired
	private SpecializationService specService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ScheduleService scheduleService;
	@Autowired
	private ProfileService profileService;
	@Autowired
	private ClassSubjectRepository csRepo;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ScheduleRepository scheRepo;
	@Autowired
	private AttendanceLogService logService;
	@Autowired
	private AccountRepository accRepo;
	@Autowired
	private ClassRepository classRepo;
	
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
		List<Subject> sss = subjectService.getAll();
		List<Integer> s = new ArrayList<>();
		s.add(1);
		s.add(2);
		CreateSpecRequest request = CreateSpecRequest.builder()
				.name("HELLO WORLD adssdaf 7")
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
	
	@Test
	public void addStudent() {
		List<String> studentId = new ArrayList<>();
		studentId.add("1");
		studentId.add("2");
		studentId.add("3");
		AddStudentIntoClassRequest rq = AddStudentIntoClassRequest.builder()
				.classId("SE1401")
				.studentIds(studentId)
				.build();
		classService.addStudent(rq);
	}
	
	@Test
	public void getSchedule() {
		String accountId = "LE00002";
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
					.teacherName(profileService.findByAccountId(schedule.getTeacher().getId()).getName())
					.status(schedule.getStatus())
					.build();
			responses.add(response);
		}
		String a = "a";
	}

	@Test
	public void testOngoing() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowStr = now.format(formatter1);
		LocalDateTime nowEnd = LocalDateTime.parse(nowStr + "T23:59:59");
		List<Schedule> schedules = scheRepo.findByTimeEndBetween(scheduleService.convertToDateViaInstant(now), scheduleService.convertToDateViaInstant(nowEnd));
		List<Schedule> result = new ArrayList<>();
		List<Role> roles = new ArrayList<>();
		roles.add(Role.builder().roleName(AppConstant.ROLE_TEACHER).build());
//		for (Role role : roles) {
//			if (role.getRoleName().equals(AppConstant.ROLE_STUDENT)) {
//				for (Schedule schedule : schedules) {
//					AttendanceLog log = logService.getBySlotIdAndStudentId("LE00002", schedule.getId());
//					if (log != null) {
//						result.add(schedule);
//					}
//				}
//			} else if (role.getRoleName().equals(AppConstant.ROLE_TEACHER)) {
//				for (Schedule schedule : schedules) {
//					if (schedule.getTeacher().getId().equals("LE00002")) {
//						result.add(schedule);
//					}
//				}
//			}
//		}
		
//		LocalDateTime now = LocalDateTime.now();
//		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String nowStr = now.format(formatter1);
//		LocalDateTime nowEnd = LocalDateTime.parse(nowStr + "T23:59:59");
//		List<Schedule> schedules = scheduleRepository.findByTimeEndBetween(convertToDateViaInstant(now), convertToDateViaInstant(nowEnd));
//		Account current = accountService.getCurrentAccount();
//		List<Schedule> result = new ArrayList<>();
		for (Role role : roles) {
			if (role.getRoleName().equals(AppConstant.ROLE_STUDENT)) {
				for (Schedule schedule : schedules) {
					if (schedule.getTimeStart().before(scheduleService.convertToDateViaInstant(LocalDateTime.now())) && schedule.getTimeEnd().after(scheduleService.convertToDateViaInstant(LocalDateTime.now()))) {
						AttendanceLog log = logService.getBySlotIdAndStudentId("LE00002", schedule.getId());
						if (log != null) {
							result.add(schedule);
						}
					}
				}
			} else if (role.getRoleName().equals(AppConstant.ROLE_TEACHER)) {
				for (Schedule schedule : schedules) {
					if (schedule.getTeacher().getId().equals("LE00002")) {
						if (schedule.getTimeStart().before(scheduleService.convertToDateViaInstant(LocalDateTime.now())) && schedule.getTimeEnd().after(scheduleService.convertToDateViaInstant(LocalDateTime.now()))) {
							result.add(schedule);
						}
					}
				}
			}
		}
		
		
		List<ScheResponse> responses = new ArrayList<>();
		for (Schedule schedule : result) {
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
					.teacherName(profileService.findByAccountId("LE00002").getName())
					.status(schedule.getStatus())
					.subject(subRes)
					.build();
			responses.add(response);
		}
		String a = "a";
	}
	
//	@Test
//	public void testUpcoming() {
//		List<Schedule> schedules = scheRepo.findByTimeStartAfter(new Date());
//		List<Schedule> result = new ArrayList<>();
//		List<Role> roles = new ArrayList<>();
//		roles.add(Role.builder().roleName(AppConstant.ROLE_TEACHER).build());
//		for (Role role : roles) {
//			if (role.getRoleName().equals(AppConstant.ROLE_STUDENT)) {
//				for (Schedule schedule : schedules) {
//					AttendanceLog log = logService.getBySlotIdAndStudentId("LE00002", schedule.getId());
//					if (log != null) {
//						result.add(schedule);
//					}
//				}
//			} else if (role.getRoleName().equals(AppConstant.ROLE_TEACHER)) {
//				for (Schedule schedule : schedules) {
//					if (schedule.getTeacher().getId().equals("LE00002")) {
//						result.add(schedule);
//					}
//				}
//			}
//		}
//		String a = "a";
//	}
	
	@Test
	public void testDeleteSchedule() {
		scheduleService.delete(1);
	}
	
	@Test
	public void deleteCourseOutClass() {
		ClassSubject cs = csRepo.findByKeyClasssIdAndKeySubjectId("SE1401", 1);
		cs.setSubject(null);
		cs.setTeacher(null);
		csRepo.deleteById(CSKey.builder().classsId("SE1401").subjectId(1).build());
	}
	
	@Test
	public void deleteStudentOutClass() {
		Classs classs = classService.findById("SE1401");
		Account student = accRepo.findById("1").get();
		classs.removeStudent(student);
		classRepo.save(classs);
	}
	
}
