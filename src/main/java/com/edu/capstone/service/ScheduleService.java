package com.edu.capstone.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.entity.Schedule;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.ScheduleRepository;
import com.edu.capstone.request.CreateScheduleRequest;

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

	public int create(CreateScheduleRequest request) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String date = dateFormat.format(request.getTimeStart());
		Schedule schedule = Schedule.builder().timeStart(request.getTimeStart()).timeEnd(request.getTimeEnd()).date(dateFormat.parse(date))
				.classs(classService.getById(request.getClassId())).teacher(accountService.findById(request.getTeacherId()))
				.subject(subjectService.findById(request.getSubjectId())).room(request.getRoom()).build();
		return scheduleRepository.saveAndFlush(schedule).getId();
	}
	
	public void update(int slotId, CreateScheduleRequest request) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
		String date = dateFormat.format(request.getTimeStart());
		Schedule schedule = scheduleRepository.findById(slotId).get();
		if (schedule == null) {
			throw new EntityNotFoundException("Slot not found");
		}
		schedule.setDate(dateFormat.parse(date));
		schedule.setRoom(request.getRoom());
		schedule.setTimeStart(request.getTimeStart());
		schedule.setTimeEnd(request.getTimeEnd());
		schedule.setClasss(classService.findById(request.getClassId()));
		schedule.setTeacher(accountService.findById(request.getTeacherId()));
		schedule.setSubject(subjectService.findById(request.getSubjectId()));
		scheduleRepository.saveAndFlush(schedule).getId();
	}
	
	public List<Schedule> getGoingOnSchedule() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String nowStr = now.format(formatter1);
		LocalDateTime nowEnd = LocalDateTime.parse(nowStr + " 23:59:59");
		return scheduleRepository.findByTimeEndBetween(now, nowEnd);
	}
	
	public List<Schedule> getUpcomingSchedule() {
		return scheduleRepository.findByTimeStartAfter(LocalDateTime.now());
	}

}
