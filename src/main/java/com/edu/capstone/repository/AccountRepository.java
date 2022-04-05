package com.edu.capstone.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.Account;

/**
 * @author NhatHH
 * Date: Jan 30, 2022
 */
@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, String> {

	Account findByEmail(String email);
	Account findTop1ByEmailIgnoreCaseContainsOrderByEmailDesc(String email);
	Account findTop1ByIdIgnoreCaseContains(String id, Sort sort);
	List<Account> findByIsActived(int isActived);
	List<Account> findBySpecialization_id(int specId);
}
