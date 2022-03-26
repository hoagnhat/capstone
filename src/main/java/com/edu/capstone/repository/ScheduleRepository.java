package com.edu.capstone.repository;

import java.time.LocalDateTime;
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
	
	List<Schedule> findByTimeEndBetween(LocalDateTime startTime, LocalDateTime endTime);
	List<Schedule> findByTimeStartAfter(LocalDateTime startTime);
	List<Schedule> findByTeacherId(String teacherId);

}
