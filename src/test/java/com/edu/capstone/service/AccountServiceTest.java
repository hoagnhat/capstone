package com.edu.capstone.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.mail.MessagingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.Profile;
import com.edu.capstone.entity.Role;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.request.AccountRequest;

/**
 * @author NhatHH Date: Jan 31, 2022
 */
@SpringBootTest
@DisplayName("Test account services")
public class AccountServiceTest {

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SpecializationService specializationService;
	@Autowired
	private ProfileService profileService; 

	/**
	 * @version 1.0 - Initiation (Feb 2, 2022 by <b>NhatHH</b>)
	 */
	@ParameterizedTest
	@CsvSource({
		"Hà Hoàng Nhật, NhatHH",
		"Nguyễn Duy Bảo Nguyên, NguyenNDB",
		"Hà Huy Thông, ThongHH",
		"Nguyễn Hồ Phương, PhuongNH",
		"Nguyễn Hoàng My, MyNH",
		"B-Bot John, JohnB"
	})
	public void testGenerateShortName(String input, String expected) {
		assertEquals(expected, accountService.generateShortName(input));
	}

	/**
	 * @version 1.0 - Initiation (Feb 2, 2022 by <b>NhatHH</b>)
	 */
	@ParameterizedTest
	@CsvSource({
		"Hà Hoàng Nhật, Ha Hoang Nhat",
		"Nguyễn Duy Bảo Nguyên, Nguyen Duy Bao Nguyen",
		"Hà Huy Thông, Ha Huy Thong",
		"Nguyễn Hồ Phương, Nguyen Ho Phuong",
		"Nguyễn Hoàng My, Nguyen Hoang My",
		"B-Bot John, B-Bot John"
	})
	public void testRemoveAccent(String input, String expected) {
		assertEquals(expected, accountService.removeAccent(input));
	}
	
	/**
	 * @version 1.0 - Initiation (Feb 2, 2022 by <b>NhatHH</b>)
	 * @throws MessagingException 
	 */
	@ParameterizedTest
	@CsvSource({
		"ROLE_MANAGER, Software Engineer, hahng.nhat@gmail.com, Hà Hoàng Nhật, 22, 0123456789, 0",
		"ROLE_TEACHER, Software Engineer, hahng.nhat@gmail.com, Nguyễn Hoàng My, 7, 0123456789, 1",
		"ROLE_STUDENT, Software Engineer, hahng.nhat@gmail.com, Nguyễn Duy Bảo Nguyên, 8, 0123456789, 0"	
	})
	public void testCreate(String roleName, String specializationName, String personalEmail, String name, int age,
			String phone, int gender) throws MessagingException {
//		Role role = roleService.findByRoleName(roleName);
//		Specialization specialization = specializationService.findByName(specializationName);
//		AccountRequest request = AccountRequest.builder()
//				.roleId(role.getId())
//				.specializationId(specialization.getId())
//				.personalEmail(personalEmail)
//				.name(name)
//				.age(age)
//				.phone(phone)
//				.gender(gender)
//				.build();
//		String id = accountService.create(request);
//		
//		Account account = accountService.findById(id);
//		Profile profile = profileService.findByAccountId(id);
//		Specialization spec = account.getSpecialization();
//		Set<Role> rol = account.getRoles();
//		
//		assertEquals(personalEmail, profile.getPersonalEmail());
//		assertEquals(name, profile.getName());
//		assertEquals(age, profile.getAge());
//		assertEquals(phone, profile.getPhone());
//		assertEquals(gender, profile.getGender());
//		assertEquals(specializationName, spec.getName());
//		assertEquals(roleName, rol.stream().findFirst().get().getRoleName());
//
//		// Delete account after test
//		accountService.delete(id);
	}

}
