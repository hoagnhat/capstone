package com.edu.capstone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.SpecializationRepository;

/**
 * @author NhatHH Date: Jan 31, 2022
 */
@Service
public class SpecializationService {

	@Autowired
	private SpecializationRepository specializationRepository;

	/**
	 * Tìm chuyên ngành theo id
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
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
		String[] nameArray = specialization.getName().split(""); 
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
	public void create(String name) {
		// TODO: Check validate of name
		Specialization specialization = Specialization.builder()
				.name(name)
				.build();
		specializationRepository.saveAndFlush(specialization);
	}
	
	/**
	 * Cập nhật chuyên ngành theo id
	 * 
	 * @version 1.0 - Initiation (Feb 1, 2022 by <b>NhatHH</b>)
	 */
	public void update(int id, String name) {
		// TODO: Need validate name
		Specialization specialization = findById(id);
		if (specialization == null) {
			throw new EntityNotFoundException(ExceptionConstant.SPECIALIZATION_NOT_FOUND);
		}
		specialization.setName(name);
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

}
