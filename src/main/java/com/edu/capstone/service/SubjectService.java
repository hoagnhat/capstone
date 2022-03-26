package com.edu.capstone.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.exception.EntityNotFoundException;
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
	
	public int create(CreateSubjectRequest request) {
		Subject subject = Subject.builder()
				.name(request.getName())
				.subjectCode(request.getSubjectCode())
				.totalSlot(request.getTotalSlot()).build();
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
	
	public void delete(int subjectId) {
		subjectRepository.deleteById(subjectId);
	}

}
