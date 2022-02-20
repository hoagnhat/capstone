package com.edu.capstone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.SubjectRepository;

/**
 * @author NhatHH Date: Feb 20, 2022
 */
@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private SpecializationService specializationService;

	public int create(int semester, int specializationId) {
		Subject subject = Subject.builder().semester(semester)
				.specialization(specializationService.findById(specializationId)).build();
		return subjectRepository.saveAndFlush(subject).getId();
	}
	
	public Subject findById(int subjectId) {
		Optional<Subject> optional = subjectRepository.findById(subjectId);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException(ExceptionConstant.SUBJECT_NOT_FOUND);
		}
		return optional.get();
	}

}
