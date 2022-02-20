package com.edu.capstone.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.entity.Schedule;
import com.edu.capstone.repository.ScheduleRepository;

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

	public int create(Date timeStart, Date timeEnd, String classId, String teacherId, int subjectId) {
		Schedule schedule = Schedule.builder().timeStart(timeStart).timeEnd(timeEnd)
				.classs(classService.getById(classId)).teacher(accountService.findById(teacherId))
				.subject(subjectService.findById(subjectId)).build();
		return scheduleRepository.saveAndFlush(schedule).getId();
	}

}
