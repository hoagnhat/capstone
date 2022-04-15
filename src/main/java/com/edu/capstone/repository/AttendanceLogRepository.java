package com.edu.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.AttendanceLog;

/**
 * @author NhatHH Date: Feb 20, 2022
 */
@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Integer> {
	@Transactional(readOnly = true)
	List<AttendanceLog> findBySlotId(int slotId);

	void deleteBySlotId(int slotId);

	@Transactional(readOnly = true)
	AttendanceLog findBySlotIdAndStudentId(int slotId, String studentId);

	@Transactional
	@Modifying
	@Query(value = "Delete attendance_log from attendance_log left join capstone.schedule"
			+ "	on attendance_log.slot_id = schedule.id" 
			+ " where attendance_log.slot_id = ?1"
			+ "	and attendance_log.student_id = ?2"
			+ "	and schedule.status = 'NOT YET'", nativeQuery = true)
	void deleteByStudentIdAndClassId(int slotId, String studentId);

	int countBySlotId(int slotId);

}
