package com.edu.capstone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.capstone.entity.Role;

/**
 * @author NhatHH
 * Date: Jan 31, 2022
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRoleNameIgnoreCase(String roleName);
	
}
