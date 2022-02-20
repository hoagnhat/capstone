package com.edu.capstone.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.capstone.entity.Classs;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@Repository
public interface ClassRepository extends JpaRepository<Classs, String> {
	
	Classs findTop1ByIdIgnoreCaseContains(String id, Sort sort);
	
}
