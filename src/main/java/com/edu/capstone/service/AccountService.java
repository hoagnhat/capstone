
package com.edu.capstone.service;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.edu.capstone.common.constant.AppConstant;
import com.edu.capstone.common.constant.ExceptionConstant;
import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.ClassSubject;
import com.edu.capstone.entity.Classs;
import com.edu.capstone.entity.Profile;
import com.edu.capstone.entity.Role;
import com.edu.capstone.entity.Specialization;
import com.edu.capstone.exception.EntityNotFoundException;
import com.edu.capstone.repository.AccountRepository;
import com.edu.capstone.repository.ClassSubjectRepository;
import com.edu.capstone.repository.ProfileRepository;
import com.edu.capstone.request.AccountRequest;
import com.edu.capstone.request.ChangePasswordRequest;
import com.edu.capstone.response.AccountResponse;

/**
 * @author NhatHH Date: Jan 30, 2022
 */
@Service
public class AccountService {
	
	private Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private SpecializationService specializationService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ClassSubjectRepository csRepo;
	@Autowired
	private ExcelHelper excelHelper;
	
	public List<Account> getAll() {
		return accountRepository.findAll();
	}

	/**
	 * Tìm tài khoản bằng email
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	@Transactional
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	/**
	 * Tạo tài khoản mới
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 * @throws MessagingException 
	 */
	public String create(AccountRequest request) throws MessagingException {
		logger.info("Start create");
		String accountId = generateAccountId(request.getKhoa() == null ? 0 : request.getKhoa(), request.getRoleId());
		String email = generateEmail(request.getName().trim());
		String password = generateRandomString();
		String avatar = request.getAvatar();
		String address = request.getAddress();
		// Tạo account
		Account account = Account.builder()
				.id(accountId)
				.email(email)
				.password(passwordEncoder.encode(password))
				.isActived(1)
				.build();
		// Set chuyên ngành cho account
		Specialization specialization = specializationService.findById(request.getSpecializationId());
		account.setSpecialization(specialization);
		// Set role cho account
		Role role = roleService.findById(request.getRoleId());
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		account.setRoles(roles);
		// Update account
		accountRepository.saveAndFlush(account);
		// Tạo profile cho account
		Profile profile = Profile.builder().accountId(accountId).account(account).name(request.getName().trim()).age(request.getAge())
				.avatar(avatar != null ? avatar.trim() : null).personalEmail(request.getPersonalEmail().trim())
				.phone(request.getPhone().trim()).gender(request.getGender())
				.address(address != null ? address.trim() : null).build();
		profileRepository.saveAndFlush(profile);
		// Gửi email, password qua gmail
		emailService.sendMail(request.getPersonalEmail(), "Email Password nè", email + "-" + password);
		return accountId;
	}
	
	@SuppressWarnings("static-access")
	public void importRegister(MultipartFile file) throws MessagingException, IOException {
		List<AccountRequest> requests = excelHelper.excelToTutorials(file.getInputStream());
		for (AccountRequest rq : requests) {
			create(rq);
		}
	}

	/**
	 * Tạo email cho tài khoản dựa theo tên
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public String generateEmail(String name) {
		logger.info("Start generateEmail()");
		String shortName = generateShortName(name);
		Account account = accountRepository.findTop1ByEmailIgnoreCaseContains(shortName + "@", Sort.by("email").descending());
		// Check whether account is null
		if (account == null) {
			return shortName + AppConstant.EMAIL_DOMAIN;
		}
		String email = account.getEmail();
		// Lấy số thứ tự của email
		String number = email.substring(email.indexOf(shortName) + shortName.length(), email.indexOf("@"));
		int newNumber;
		if (number.equals("")) {
			newNumber = 1;
		} else {
			newNumber = Integer.parseInt(number) + 1;
		}
		return shortName + newNumber + AppConstant.EMAIL_DOMAIN;
	}

	/**
	 * Tạo tên rút gọn cho tài khoản mới
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public String generateShortName(String name) {
		String[] nameArray = removeAccent(name).split(" ");
		// Get the name first
		String result = nameArray[nameArray.length - 1];
		// Get the first character of each string
		for (int i = 0; i < nameArray.length - 1; i++) {
			result += nameArray[i].charAt(0);
		}
		return result;
	}

	/**
	 * Loại bỏ dấu tiếng việt
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	/**
	 * Tạo mật khẩu ngẫu nhiên
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public String generateRandomString() {
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = false;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		return generatedString; 
	}

	/**
	 * Tạo id cho tài khoản mới
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public String generateAccountId(int khoa, int roleId) {
		logger.info("Start generateAccountId()");
		Role role = roleService.findById(roleId);
		switch (role.getRoleName()) {
		case AppConstant.ROLE_STUDENT:
			String idStart = AppConstant.STUDENT_CODE + khoa;
			return idStart + generateNumberId(idStart);
		case AppConstant.ROLE_TEACHER:
			return AppConstant.TEACHER_CODE + generateNumberId(AppConstant.TEACHER_CODE);
		case AppConstant.ROLE_MANAGER:
			return AppConstant.MANAGER_CODE + generateNumberId(AppConstant.MANAGER_CODE);
		default:
			return null;
		}
	}

	/**
	 * Tạo số id cho tài khoản mới
	 * 
	 * @version 1.0 - Initiation (Feb 1, 2022 by <b>NhatHH</b>)
	 */
	public String generateNumberId(String searchCode) {
		String lastAccountId = findLastAccountId(searchCode);
		String number;
		if (lastAccountId.equals("")) {
			number = "1";
		} else {
			number = String.valueOf(
					Integer.parseInt(lastAccountId.substring(lastAccountId.indexOf(searchCode) + searchCode.length()))
							+ 1);
		}
		DecimalFormat df = new DecimalFormat(AppConstant.ACCOUNT_ID_FORMAT);
		return df.format(Double.parseDouble(number));
	}

	/**
	 * Tìm id của account đứng ở cuối cùng theo thứ tự ASC
	 * 
	 * @version 1.0 - Initiation (Jan 31, 2022 by <b>NhatHH</b>)
	 */
	public String findLastAccountId(String id) {
		Account account = accountRepository.findTop1ByIdIgnoreCaseContains(id, Sort.by("id").descending());
		if (account == null) {
			return "";
		}
		return account.getId();
	}
	
	/**
	 * Tìm account theo id
	 * 
	 * @version 1.0 - Initiation (Feb 2, 2022 by <b>NhatHH</b>)
	 */
	public Account findById(String id) {
		Optional<Account> optional = accountRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	/**
	 * Delete account theo id
	 * 
	 * @version 1.0 - Initiation (Feb 3, 2022 by <b>NhatHH</b>)
	 */
	public void delete(String id) {
		accountRepository.deleteById(id);
	}
	
	/**
	 * Thay đổi mật khẩu
	 * 
	 * @version 1.0 - Initiation (Feb 6, 2022 by <b>NhatHH</b>)
	 */
	public void changePassword(ChangePasswordRequest request) {
		Account current = getCurrentAccount();
		// Check new password is match with confirm password
		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			throw new RuntimeException(ExceptionConstant.NEW_PASSWORD_NOT_MATCH_CONFIRM_PASSWORD_MSG);
		}
		// Check whether old password is not match with the one in database
		if (!passwordEncoder.matches(request.getOldPassword(), current.getPassword())) {
			throw new RuntimeException(ExceptionConstant.OLD_PASSWORD_WRONG_MSG); 
		}
		current.setPassword(passwordEncoder.encode(request.getNewPassword()));
		accountRepository.saveAndFlush(current);
	}
	
	/**
	 * Get current account 
	 * 
	 * @version 1.0 - Initiation (Feb 6, 2022 by <b>NhatHH</b>)
	 */
	public Account getCurrentAccount() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		String email = authentication.getName();
		Account account = findByEmail(email);
		// Check whether account is null
		if (account == null) {
			throw new RuntimeException(ExceptionConstant.USER_NOT_LOGIN_MSG);
		}
		return account;
	}
	
	/**
	 * Quên mật khẩu
	 * 
	 * @version 1.0 - Initiation (Feb 6, 2022 by <b>NhatHH</b>)
	 */
	public void setupVerifyToken(String email) {
		Account account = findByEmail(email);
		if (account == null) {
			throw new EntityNotFoundException(ExceptionConstant.EMAIL_NOT_FOUND);
		}
		// Check whether token is available and isn't expired
		if (account.getVerifyToken() != null && account.getTokenExpiredDate().isBefore(LocalDateTime.now())) {
			return;
		}
		String verifyToken = generateRandomString();
		account.setVerifyToken(verifyToken);
		account.setTokenExpiredDate(LocalDateTime.now().plusMinutes(AppConstant.EXPIRED_LIMIT_TIME));
		accountRepository.saveAndFlush(account);
	}
	
	/**
	 * Update password for forget password feature
	 * 
	 * @version 1.0 - Initiation (Feb 6, 2022 by <b>NhatHH</b>)
	 */
	public void updatePassword(AccountRequest request) {
		Account current = getCurrentAccount();
		// Check new password is match with confirm password
		if (request.getNewPassword().equals(request.getConfirmPassword())) {
			throw new RuntimeException(ExceptionConstant.NEW_PASSWORD_NOT_MATCH_CONFIRM_PASSWORD_MSG);
		}
		current.setPassword(request.getNewPassword());
		accountRepository.saveAndFlush(current);
	}
	
	/**
	 * Verify token
	 * 
	 * @version 1.0 - Initiation (Feb 6, 2022 by <b>NhatHH</b>)
	 */
	public boolean verifyToken(String email, String token) {
		Account account = findByEmail(email);
		// Check whether account is null
		if (account == null) {
			throw new EntityNotFoundException(ExceptionConstant.EMAIL_NOT_FOUND);
		}
		// Check whether token is expired 
		if (!account.getTokenExpiredDate().isAfter(LocalDateTime.now())) {
			throw new RuntimeException(ExceptionConstant.TOKEN_IS_EXPIRED_MSG);
		} else {
			// Check whether token is match with the one in database 
			if (!account.getTokenExpiredDate().equals(token)) {
				return false;
			}
			return true;
		}
	}
	
	public void inactiveAccount(List<String> accountIds) {
		for (String id : accountIds) {
			Account account = findById(id);
			account.setIsActived(0);
			accountRepository.saveAndFlush(account);
		}
	}
	
	public List<AccountResponse> getActiveAccounts() {
		List<AccountResponse> responses = new ArrayList<>();
		List<Account> accounts = accountRepository.findByIsActived(1);
		for (Account account : accounts) {
			Profile profile = profileRepository.findById(account.getId()).get();
			List<String> roles = new ArrayList<>();
			List<String> classs = new ArrayList<>();
			List<String> subjects = new ArrayList<>();
			for (Role role : account.getRoles()) {
				roles.add(role.getRoleName());
			}
			for (Classs sclass : account.getClasses()) {
				classs.add(sclass.getId());
				List<ClassSubject> csubjectss = csRepo.findByKeyClasssId(sclass.getId());
				for (ClassSubject subject : csubjectss) {
					subjects.add(subject.getSubject().getSubjectCode());
				}
			}
			AccountResponse response = AccountResponse.builder()
					.accountId(account.getId())
					.name(profile.getName())
					.avatar(profile.getAvatar())
					.age(profile.getAge())
					.address(profile.getAddress())
					.gender(profile.getGender())
					.phone(profile.getPhone())
					.email(account.getEmail())
					.personalEmail(profile.getPersonalEmail())
					.roles(roles)
					.classs(classs)
					.subjects(subjects)
					.specialization(account.getSpecialization().getName())
					.build();
			responses.add(response);
		}
		return responses;
	}
	
	public List<AccountResponse> getTeachers() {
		List<AccountResponse> responses = new ArrayList<>();
		List<Account> accounts = accountRepository.findAll();
		for (Account account : accounts) {
			List<String> roles = new ArrayList<>();
			List<String> classs = new ArrayList<>();
			List<String> subjects = new ArrayList<>();
			for (Role role : account.getRoles()) {
				if (role.getRoleName().equals(AppConstant.ROLE_TEACHER)) {
					Profile profile = profileRepository.findById(account.getId()).get();
					roles.add(role.getRoleName());
					for (Classs sclass : account.getClasses()) {
						classs.add(sclass.getId());
						List<ClassSubject> csubjectss = csRepo.findByKeyClasssId(sclass.getId());
						for (ClassSubject subject : csubjectss) {
							subjects.add(subject.getSubject().getSubjectCode());
						}
					}
					AccountResponse response = AccountResponse.builder()
							.accountId(account.getId())
							.name(profile.getName())
							.avatar(profile.getAvatar())
							.age(profile.getAge())
							.address(profile.getAddress())
							.gender(profile.getGender())
							.phone(profile.getPhone())
							.email(account.getEmail())
							.personalEmail(profile.getPersonalEmail())
							.roles(roles)
							.classs(classs)
							.subjects(subjects)
							.specialization(account.getSpecialization().getName())
							.build();
					responses.add(response);
				}
			}
		}
		return responses;
	}
	
	public List<AccountResponse> getStudents() {
		List<AccountResponse> responses = new ArrayList<>();
		List<Account> accounts = accountRepository.findAll();
		for (Account account : accounts) {
			List<String> roles = new ArrayList<>();
			List<String> classs = new ArrayList<>();
			List<String> subjects = new ArrayList<>();
			for (Role role : account.getRoles()) {
				if (role.getRoleName().equals(AppConstant.ROLE_STUDENT)) {
					Profile profile = profileRepository.findById(account.getId()).get();
					roles.add(role.getRoleName());
					for (Classs sclass : account.getClasses()) {
						classs.add(sclass.getId());
						List<ClassSubject> csubjectss = csRepo.findByKeyClasssId(sclass.getId());
						for (ClassSubject subject : csubjectss) {
							subjects.add(subject.getSubject().getSubjectCode());
						}
					}
					AccountResponse response = AccountResponse.builder()
							.accountId(account.getId())
							.name(profile.getName())
							.avatar(profile.getAvatar())
							.age(profile.getAge())
							.address(profile.getAddress())
							.gender(profile.getGender())
							.phone(profile.getPhone())
							.email(account.getEmail())
							.personalEmail(profile.getPersonalEmail())
							.roles(roles)
							.classs(classs)
							.subjects(subjects)
							.specialization(account.getSpecialization().getName())
							.build();
					responses.add(response);
				}
			}
		}
		return responses;
	}

}
