package com.edu.capstone.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.AccountRepository;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.repository.SubjectRepository;
import com.edu.capstone.request.CreateSubjectRequest;
import com.edu.capstone.request.SubjectRequest;

/**
 * @author NhatHH Date: Feb 20, 2022
 */
@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ClassSubjectRepository csRepo;
	
	public int create(CreateSubjectRequest request) {
		Set<Account> teachers = new HashSet<>();
		for (String teacherId : request.getTeacherIds()) {
			Account teacher = accountRepository.findById(teacherId).get();
			teachers.add(teacher);
		}
		Subject subject = Subject.builder()
				.name(request.getName())
				.subjectCode(request.getSubjectCode())
				.teachers(teachers).build();
		subject = subjectRepository.saveAndFlush(subject);
		subjectRepository.saveAndFlush(subject);
		return subject.getId();
	}
	
	public Subject findById(int subjectId) {
		Optional<Subject> optional = subjectRepository.findById(subjectId);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException(ExceptionConstant.SUBJECT_NOT_FOUND);
		}
		return optional.get();
	}
	
	public List<Subject> getAll() {
		return subjectRepository.findAll();
	}
	
	public void update(int subjectId, SubjectRequest request, Set<Specialization> specializations) {
		Subject subject = findById(subjectId);
		subject.setName(request.getName());
		subject.setSubjectCode(request.getSubjectCode());
		subject.setSpecializations(specializations);
		subjectRepository.saveAndFlush(subject);
	}
	
	@Transactional
	public void delete(int subjectId) {
		Subject subject = findById(subjectId);
		csRepo.deleteByKeySubjectId(subjectId);
		subjectRepository.delete(subject);
	}
	
	public void save(Subject subject) {
		subjectRepository.save(subject);
	}

}
