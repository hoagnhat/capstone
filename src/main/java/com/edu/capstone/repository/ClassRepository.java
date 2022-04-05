package com.edu.capstone.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.Classs;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@Repository
@Transactional(readOnly = true)
public interface ClassRepository extends JpaRepository<Classs, String> {
	
	Classs findTop1ByIdIgnoreCaseContains(String id, Sort sort);
	List<Classs> findBySpecialization_id(int id);
}
