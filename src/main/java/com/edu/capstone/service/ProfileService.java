package com.edu.capstone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.entity.Profile;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.ProfileRepository;
import com.edu.capstone.request.AccountRequest;

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
	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public Profile findByAccountId(String accountId) {
		Optional<Profile> optional = profileRepository.findById(accountId);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void update(String accountId, AccountRequest request) {
		Profile profile = findByAccountId(accountId);
		if (profile == null) {
			throw new EntityNotFoundException("Profile not found");
		}
		profile.setName(request.getName());
		profile.setAge(request.getAge());
		profile.setAddress(request.getAddress());
		profile.setAvatar(request.getAvatar());
		profile.setGender(request.getGender());
		profile.setPersonalEmail(request.getPersonalEmail());
		profile.setPhone(request.getPhone());
		profileRepository.saveAndFlush(profile);
	}

}
