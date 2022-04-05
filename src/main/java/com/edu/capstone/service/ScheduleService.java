package com.edu.capstone.service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.AppConstant;
import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.AttendanceLog;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.entity.Role;
import com.edu.capstone.entity.Schedule;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.repository.ScheduleRepository;
import com.edu.capstone.request.CreateScheduleRequest;
import com.edu.capstone.request.ImportScheduleRequest;
import com.edu.capstone.request.ScheRqqq;

/**
 * @author NhatHH Date: Feb 20, 2022
 */
@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired
	private ClassService classService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private AttendanceLogService logService;
	@Autowired
	private ClassSubjectRepository csRepo;

	public int create(CreateScheduleRequest request) throws ParseException {
		Schedule schedule = Schedule.builder().timeStart(request.getTimeStart()).timeEnd(request.getTimeEnd())
				.classs(classService.getById(request.getClassId())).teacher(accountService.findById(request.getTeacherId()))
				.subject(subjectService.findById(request.getSubjectId())).room(request.getRoom()).build();
		return scheduleRepository.saveAndFlush(schedule).getId();
	}
	
	public void update(int slotId, CreateScheduleRequest request) throws ParseException {
		Schedule schedule = scheduleRepository.findById(slotId).get();
		if (schedule == null) {
			throw new EntityNotFoundException("Slot not found");
		}
		schedule.setRoom(request.getRoom());
		schedule.setTimeStart(request.getTimeStart());
		schedule.setTimeEnd(request.getTimeEnd());
		schedule.setClasss(classService.findById(request.getClassId()));
		schedule.setTeacher(accountService.findById(request.getTeacherId()));
		schedule.setSubject(subjectService.findById(request.getSubjectId()));
		schedule.setStatus(request.getStatus());		
		scheduleRepository.saveAndFlush(schedule).getId();
	}
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Schedule> getGoingOnSchedule() {
		LocalDateTime now = LocalDateTime.now();		
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowStr = now.format(formatter1);
		LocalDateTime nowEnd = LocalDateTime.parse(nowStr + "T23:59:59");		
		List<Schedule> schedules = scheduleRepository.findByTimeEndBetween(convertToDateViaInstant(now), convertToDateViaInstant(nowEnd));
		Account current = accountService.getCurrentAccount();
		List<Schedule> result = new ArrayList<>();
		for (Role role : current.getRoles()) {
			if (role.getRoleName().equals(AppConstant.ROLE_STUDENT)) {
				for (Schedule schedule : schedules) {
					if (schedule.getTimeStart().before(convertToDateViaInstant(LocalDateTime.now())) && schedule.getTimeEnd().after(convertToDateViaInstant(LocalDateTime.now()))) {
						AttendanceLog log = logService.getBySlotIdAndStudentId(current.getId(), schedule.getId());
						if (log != null) {
							result.add(schedule);
						}
					}
				}
			} else if (role.getRoleName().equals(AppConstant.ROLE_TEACHER)) {
				for (Schedule schedule : schedules) {
					if (schedule.getTeacher().getId().equals(current.getId())) {
						if (schedule.getTimeStart().before(convertToDateViaInstant(LocalDateTime.now())) && schedule.getTimeEnd().after(convertToDateViaInstant(LocalDateTime.now()))) {
							result.add(schedule);
						}
					}
				}
			}
		}
		return result;
	}
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Schedule> getUpcomingSchedule() {
		Account current = accountService.getCurrentAccount();
		LocalDateTime now = LocalDateTime.now();		
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowStr = now.format(formatter1);
		LocalDateTime nowEnd = LocalDateTime.parse(nowStr + "T23:59:59");	
		List<Schedule> schedules = scheduleRepository.findByTimeStartBetween(convertToDateViaInstant(now), convertToDateViaInstant(nowEnd));
		List<Schedule> result = new ArrayList<>();
		for (Role role : current.getRoles()) {
			if (role.getRoleName().equals(AppConstant.ROLE_STUDENT)) {
				for (Schedule schedule : schedules) {
					AttendanceLog log = logService.getBySlotIdAndStudentId(current.getId(), schedule.getId());
					if (log != null) {
						result.add(schedule);
					}
				}
			} else if (role.getRoleName().equals(AppConstant.ROLE_TEACHER)) {
				for (Schedule schedule : schedules) {
					if (schedule.getTeacher().getId().equals(current.getId())) {
						result.add(schedule);
					}
				}
			}
		}
		return result;
	}
	
	public void importSchedule(List<ScheRqqq> request) {
		for (ScheRqqq schedule : request) {
			for (int i = 0; i < 10; i++) {
				LocalDateTime timeStart = convertToLocalDateTimeViaInstant(schedule.getTimeStart());
				LocalDateTime timeEnd = convertToLocalDateTimeViaInstant(schedule.getTimeEnd());
				timeStart = timeStart.plusDays((long) i * 7);
				timeEnd = timeEnd.plusDays((long) i * 7);
				Schedule s = Schedule.builder()
						.room(schedule.getRoom())
						.timeStart(convertToDateViaInstant(timeStart))
						.timeEnd(convertToDateViaInstant(timeEnd))
						.classs(classService.findById(schedule.getClassId()))
						.subject(subjectService.findById(schedule.getSubjectId()))
						.teacher(accountService.findById(schedule.getTeacherId()))
						.status(AppConstant.SCHEDULE_NOT_YET_STATUS)
						.build();
				s = scheduleRepository.saveAndFlush(s);
				if (i == 0) {
					ClassSubject cs = csRepo.findByKeyClasssIdAndKeySubjectId(schedule.getClassId(), schedule.getSubjectId());
					cs.setDateStart(convertToDateViaInstant(timeStart));
					csRepo.saveAndFlush(cs);
				} else if (i == 9) {
					ClassSubject cs = csRepo.findByKeyClasssIdAndKeySubjectId(schedule.getClassId(), schedule.getSubjectId());
					cs.setDateEnd(convertToDateViaInstant(timeEnd));
					csRepo.saveAndFlush(cs);
				}
				logService.importStudentIntoSlotLog(s.getId(), schedule.getClassId(), schedule.getDescription());
			}
		}
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
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Schedule> getByAccountId(String accountId) {
		List<Schedule> schedules = new ArrayList<>();
		List<String> roles = new ArrayList<>();
		Account account = accountService.findById(accountId);
		for (Role role : account.getRoles()) {
			roles.add(role.getRoleName());
		}
		if (roles.indexOf(AppConstant.ROLE_STUDENT) != -1) {
			List<Classs> classs = classService.getAll();
			for (Classs scl : classs) {
				Set<Account> students = scl.getStudents();
				for (Account acc : students) {
					if (acc.getId().equals(accountId)) {
						schedules.addAll(scl.getSchedules());
					}
				}
			}
		} else if (roles.indexOf(AppConstant.ROLE_TEACHER) != -1) {
			schedules.addAll(scheduleRepository.findByTeacherId(accountId));
		}
		return schedules;
	}
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<Schedule> getByClassId(String classId) {
		return scheduleRepository.findByClasssId(classId);
	}
	
	public void delete(int id) {
		Schedule schedule = scheduleRepository.findById(id).get();
		schedule.getSubject().removeSchedule(schedule);
		schedule.getClasss().removeSchedule(schedule);
		scheduleRepository.delete(schedule);
	}

}
