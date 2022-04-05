package com.edu.capstone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.key.CSKey;

public interface ClassSubjectRepository extends JpaRepository<ClassSubject, CSKey> {
	@Transactional(readOnly = true)
	List<ClassSubject> findByKeyClasssId(String classId);
	@Transactional(readOnly = true)
	List<ClassSubject> findByKeySubjectId(int subjectId);
	@Transactional(readOnly = true)
	ClassSubject findByKeyClasssIdAndKeySubjectId(String classid, int subjectId);
	void deleteByKeySubjectId(int subjectId);
	
}
