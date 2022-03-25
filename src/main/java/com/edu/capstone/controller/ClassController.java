package com.edu.capstone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.capstone.entity.Classs;
import com.edu.capstone.request.ClassRequest;
import com.edu.capstone.service.ClassService;

@RestController
@RequestMapping("/class")
public class ClassController {
	
	@Autowired
	private ClassService classService;
	
	@GetMapping
	public List<Classs> getAll() {
		return classService.getAll();
	}
	
	@PostMapping("/create")
	public void addClass(@RequestBody ClassRequest request) {
		classService.create(request);
	}

}
