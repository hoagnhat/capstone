package com.edu.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.AttendanceLog;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Integer> {
	@Transactional(readOnly = true)
	List<AttendanceLog> findBySlotId(int slotId);
	void deleteBySlotId(int slotId);
	@Transactional(readOnly = true)
	AttendanceLog findBySlotIdAndStudentId(int slotId, String studentId);
	int countBySlotId(int slotId);
	
}
