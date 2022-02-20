package com.edu.capstone.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Role;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.RoleRepository;

/**
 * @author NhatHH Date: Jan 31, 2022
 */
@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Tìm chức vụ theo id
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public Role findById(int id) {
		Optional<Role> optional = roleRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		} else {
			throw new EntityNotFoundException(ExceptionConstant.ROLE_NOT_FOUND);
		}
	}
	
	/**
	 * Tạo chức vụ mới
	 * 
	 * @version 1.0 - Initiation (Feb 1, 2022 by <b>NhatHH</b>)
	 */
	public void create(String roleName) {
		// TODO: Need validate roleName
		Role role = Role.builder()
				.roleName(roleName)
				.build();
		roleRepository.saveAndFlush(role);
	}
	
	/**
	 * Cập nhật chức vụ theo id
	 * 
	 * @version 1.0 - Initiation (Feb 1, 2022 by <b>NhatHH</b>)
	 */
	public void update(int id, String roleName) {
		// TODO: Need validate roleName
		Role role = findById(id);
		role.setRoleName(roleName);
		roleRepository.saveAndFlush(role);
	}
	
	/**
	 * Xóa chức vụ theo id
	 * 
	 * @version 1.0 - Initiation (Feb 1, 2022 by <b>NhatHH</b>)
	 */
	public void delete(int id) {
		roleRepository.deleteById(id);
	}
	
	/**
	 * Tìm nghiệp vụ theo tên
	 * 
	 * @version 1.0 - Initiation (Feb 2, 2022 by <b>NhatHH</b>)
	 */
	public Role findByRoleName(String roleName) {
		// TODO: Need validate name
		return roleRepository.findByRoleNameIgnoreCase(roleName);
	}

}
