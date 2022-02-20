package com.edu.capstone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.entity.Profile;
import com.edu.capstone.repository.ProfileRepository;

/**
 * @author NhatHH Date: Feb 2, 2022
 */
@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;
	
	/**
	 * Tìm profile theo account id
	 * 
	 * @version 1.0 - Initiation (Feb 3, 2022 by <b>NhatHH</b>)
	 */
	public Profile findByAccountId(String accountId) {
		Optional<Profile> optional = profileRepository.findById(accountId);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

}
