package com.edu.capstone.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.capstone.entity.Schedule;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	
	List<Schedule> findByTimeEndBetween(Date startTime, Date endTime);
	List<Schedule> findByTimeStartAfter(Date startTime);
	List<Schedule> findByTeacherId(String teacherId);
	List<Schedule> findByClasssId(String classId);

}
