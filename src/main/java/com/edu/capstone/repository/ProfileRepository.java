package com.edu.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.capstone.entity.Profile;

/**
 * @author NhatHH
 * Date: Feb 2, 2022
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, String> {

}
