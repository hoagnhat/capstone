package com.edu.capstone.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Specialization;
import com.edu.capstone.entity.Subject;
import com.edu.capstone.request.SpecializationRequest;
import com.edu.capstone.service.SpecializationService;
import com.edu.capstone.service.SubjectService;

@RestController
@RequestMapping("/specialization")
public class SpecializationController {

	@Autowired
	private SpecializationService specService;
	@Autowired
	private SubjectService subjectService;

	@GetMapping
	public List<Specialization> getAll() {
		return specService.getAll();
	}

	@GetMapping("/{id}")
	public Specialization getById(@RequestParam("id") int id) {
		return specService.findById(id);
	}

	@PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void create(@RequestBody SpecializationRequest request) {
		Set<Subject> subjects = new HashSet<>();
		for (Integer subjectId : request.getSubjectId()) {
			subjects.add(subjectService.findById(subjectId));
		}
		specService.create(request, subjects);
	}

	@PutMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE})
	public void update(@RequestParam("specId") int specId, @RequestBody SpecializationRequest request) {
		Set<Subject> subjects = new HashSet<>();
		for (Integer subjectId : request.getSubjectId()) {
			subjects.add(subjectService.findById(subjectId));
		}
		specService.update(specId, request, subjects);
	}

}
