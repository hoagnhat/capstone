package com.edu.capstone.service;

import java.text.DecimalFormat;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.AppConstant;
import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.ClassRepository;

/**
 * @author NhatHH Date: Feb 20, 2022
 */
@Service
public class ClassService {

	@Autowired
	private ClassRepository classRepository;
	@Autowired
	private SpecializationService specializationService;
	@Autowired
	private AccountService accountService;

	public Classs getById(String classId) {
		Optional<Classs> optional = classRepository.findById(classId);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException(ExceptionConstant.CLASS_NOT_FOUND);
		}
		return optional.get();
	}

	public String create(int semester, int specializationId) {
		String classId = specializationService.getNameCode(specializationId) + generateNumberId(specializationService.getNameCode(specializationId));
		Classs classs = Classs.builder()
				.id(classId)
				.semester(semester)
				.specialization(specializationService.findById(specializationId)).build();
		return classRepository.saveAndFlush(classs).getId();
	}

	public void addStudent(String studentId, String classId) {
		Account student = accountService.findById(studentId);
		Classs classs = getById(classId);
		classs.getStudents().add(student);
	}

	public String generateNumberId(String searchCode) {
		String lastAccountId = findLastClassId(searchCode);
		String number;
		if (lastAccountId.equals("")) {
			number = "1";
		} else {
			number = String.valueOf(
					Integer.parseInt(lastAccountId.substring(lastAccountId.indexOf(searchCode) + searchCode.length()))
							+ 1);
		}
		DecimalFormat df = new DecimalFormat(AppConstant.CLASS_ID_FORMAT);
		return df.format(Double.parseDouble(number));
	}

	public String findLastClassId(String id) {
		Classs classs = classRepository.findTop1ByIdIgnoreCaseContains(id, Sort.by("id").descending());
		if (classs == null) {
			return "";
		}
		return classs.getId();
	}

}
