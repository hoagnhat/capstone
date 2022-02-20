package com.edu.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.capstone.entity.AttendanceLog;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Integer> {

	List<AttendanceLog> findBySlotId(int slotId);
	void deleteBySlotId(int slotId);
	AttendanceLog findBySlotIdAndStudentId(int slotId, String studentId);
	
}
