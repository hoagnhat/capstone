package com.edu.capstone.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.Schedule;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@Repository
@Transactional(readOnly = true)
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	
	List<Schedule> findByTimeEndBetween(Date startTime, Date endTime);
	List<Schedule> findByTimeStartBetween(Date startTime, Date endTime);
	List<Schedule> findByTeacherId(String teacherId);
	List<Schedule> findByClasssId(String classId);
	List<Schedule> findBySubjectId(int subjectId);

}
