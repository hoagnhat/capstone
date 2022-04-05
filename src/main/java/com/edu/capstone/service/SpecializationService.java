package com.edu.capstone.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.SpecializationRepository;
import com.edu.capstone.request.CreateSpecRequest;
import com.edu.capstone.request.SpecializationRequest;

/**
 * @author NhatHH Date: Jan 31, 2022
 */
@Service
public class SpecializationService {

	@Autowired
	private SpecializationRepository specializationRepository;
	@Autowired
	private SubjectService subjectService;

	/**
	 * Tìm chuyên ngành theo id
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Specialization findById(int id) {
		Optional<Specialization> optional = specializationRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new EntityNotFoundException(ExceptionConstant.SPECIALIZATION_NOT_FOUND);
		}
	}
	
	/**
	 * Get name code của chuyên ngành
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public String getNameCode(int id) {
		Specialization specialization = findById(id);
		// Check whether specialization is null
		if (specialization == null) {
			throw new EntityNotFoundException(ExceptionConstant.SPECIALIZATION_NOT_FOUND);
		}
		String[] nameArray = specialization.getName().split(" "); 
		// Get name code
		String result = "";
		for (int i = 0; i < nameArray.length; i++) {
			result += nameArray[i].charAt(0);
		}
		return result;
	}
	/**
	 * Get name code của chuyên ngành bằng name của chuyên ngành
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public String getNameCodeBySpecName(String name) {		
		String[] nameArray = name.split(" "); 
		// Get name code
		String result = "";
		for (int i = 0; i < nameArray.length; i++) {
			result += nameArray[i].charAt(0);
		}
		return result;
	}
	
	/**
	 * Tạo chuyên ngành mới 
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public void create(CreateSpecRequest request, Set<Subject> subjects) {
		// TODO: Check validate of name
		Specialization specialization = Specialization.builder()
				.name(request.getName())
				.build();
		specialization = specializationRepository.saveAndFlush(specialization);
		for (Subject subject : subjects) {
			specialization.addSubjects(subject);
		}
		specializationRepository.saveAndFlush(specialization);
	}
	
	/**
	 * Cập nhật chuyên ngành theo id
	 * 
	 * @version 1.0 - Initiation (Feb 1, 2022 by <b>NhatHH</b>)
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public void update(SpecializationRequest request, Set<Subject> subjects) {
		List<Subject> sss = new ArrayList<>();
		// TODO: Need validate name
		Specialization specialization = findById(request.getSpecId());
		if (specialization == null) {
			throw new EntityNotFoundException(ExceptionConstant.SPECIALIZATION_NOT_FOUND);
		}
		for (Subject ssbt : specialization.getSubjects()) {
			sss.add(ssbt);
		}
		specialization.setName(request.getName());
		for (Subject ssbt : sss) {
			specialization.removeSubjects(ssbt);
		}
		for (Subject subject : subjects) {
			specialization.addSubjects(subject);
		}
		specializationRepository.saveAndFlush(specialization);
	}
	
	/**
	 * Xóa chuyên ngành theo id
	 * 
	 * @version 1.0 - Initiation (Feb 1, 2022 by <b>NhatHH</b>)
	 */
	public void delete(int id) {
		specializationRepository.deleteById(id);
	}
	
	/**
	 * Tìm chuyên ngành theo tên
	 * 
	 * @version 1.0 - Initiation (Feb 2, 2022 by <b>NhatHH</b>)
	 */
	public Specialization findByName(String name) {
		// TODO: Need validate name
		return specializationRepository.findByNameIgnoreCase(name);
	}
	
	public List<Specialization> getAll() {
		return specializationRepository.findAll();
	}
	
	public Specialization getById(int id) {
		Optional<Specialization> optional = specializationRepository.findById(id);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("Specialization not found");
		}
		return optional.get();
	}

}
