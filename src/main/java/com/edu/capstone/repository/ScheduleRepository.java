package com.edu.capstone.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.Schedule;

/**
 * @author NhatHH Date: Feb 20, 2022
 */
@Repository

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
	@Transactional(readOnly = true)
	List<Schedule> findByTimeEndBetween(Date startTime, Date endTime);
	@Transactional(readOnly = true)
	List<Schedule> findByTimeStartBetween(Date startTime, Date endTime);
	@Transactional(readOnly = true)
	List<Schedule> findByTeacherId(String teacherId);
	@Transactional(readOnly = true)
	List<Schedule> findByClasssId(String classId);
	@Transactional(readOnly = true)
	List<Schedule> findBySubjectId(int subjectId);
	@Modifying	
	@Transactional
	@Query(value = "Delete from schedule where class_id = ?1 and subject_id = ?2", nativeQuery = true)
	void deleteByClassIdAndSubjectId(String classId, int subjectId);
}
