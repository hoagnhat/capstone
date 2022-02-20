package com.edu.capstone.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.capstone.entity.Account;

/**
 * @author NhatHH
 * Date: Jan 30, 2022
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

	Account findByEmail(String email);
	Account findTop1ByEmailIgnoreCaseContains(String email, Sort sort);
	Account findTop1ByIdIgnoreCaseContains(String id, Sort sort);
	
}
