package com.edu.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.capstone.entity.Specialization;

/**
 * @author NhatHH Date: Jan 31, 2022
 */
@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

	Specialization findByNameIgnoreCase(String name);
	
}
