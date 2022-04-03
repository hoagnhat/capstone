package com.edu.capstone.repository;

import java.util.List;

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
	Account findTop1ByEmailIgnoreCaseContainsOrderByEmailDesc(String email);
	Account findTop1ByIdIgnoreCaseContains(String id, Sort sort);
	List<Account> findByIsActived(int isActived);
	
}
