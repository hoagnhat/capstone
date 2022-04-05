package com.edu.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.Subject;

/**
 * @author NhatHH
 * Date: Feb 20, 2022
 */
@Repository
@Transactional(propagation=Propagation.SUPPORTS)
public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}
