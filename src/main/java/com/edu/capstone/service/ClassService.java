package com.edu.capstone.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.AppConstant;
import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.entity.key.CSKey;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.ClassRepository;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.request.AddCourseForClassRequest;
import com.edu.capstone.request.AddStudentIntoClassRequest;
import com.edu.capstone.request.ClassRequest;

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
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ClassSubjectRepository csRepo;

	public Classs getById(String classId) {
		Optional<Classs> optional = classRepository.findById(classId);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException(ExceptionConstant.CLASS_NOT_FOUND);
		}
		return optional.get();
	}

	public void create(ClassRequest request) {
		String lastDigit = generateNumberId(specializationService.getNameCode(request.getSpecId()) + request.getKhoa());
		for (int i = Integer.parseInt(lastDigit); i < Integer.parseInt(lastDigit) + request.getSize(); i++) {
			DecimalFormat df = new DecimalFormat(AppConstant.CLASS_ID_FORMAT);
			String classId = specializationService.getNameCode(request.getSpecId()) + request.getKhoa() + df.format(i);
			Classs classs = Classs.builder().id(classId).semester(request.getSemester())
					.specialization(specializationService.findById(request.getSpecId())).build();
			classRepository.saveAndFlush(classs).getId();
		}
	}

	public void addStudent(AddStudentIntoClassRequest request) {
		for (String studentId : request.getStudentIds()) {
			Account student = accountService.findById(studentId);
			Classs classs = getById(request.getClassId());
			classs.getStudents().add(student);
			classRepository.saveAndFlush(classs);
		}
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

	public Classs findById(String classId) {
		Optional<Classs> optional = classRepository.findById(classId);
		if (!optional.isPresent()) {
			throw new EntityNotFoundException("Class not found");
		}
		return optional.get();
	}

	public List<Classs> getAll() {
		return classRepository.findAll();
	}
	
	public void addCourse(AddCourseForClassRequest request) {
		Account teacher = accountService.findById(request.getTeacherId());
		Subject subject = subjectService.findById(request.getSubjectId());
		CSKey key = CSKey.builder().classId(request.getClassId()).subjectId(request.getSubjectId()).build();
		ClassSubject cs = ClassSubject.builder()
				.key(key)
				.teacher(teacher)
				.subject(subject)
				.build();
		csRepo.saveAndFlush(cs);
	}

}
