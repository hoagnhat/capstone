package com.edu.capstone.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.edu.capstone.common.constant.AppConstant;
import com.edu.capstone.entity.Account;
import com.edu.capstone.entity.Role;
import com.edu.capstone.repository.AccountRepository;
import com.edu.capstone.repository.ClassRepository;
import com.edu.capstone.request.AccountRequest;
import com.edu.capstone.request.ChangePasswordRequest;
import com.edu.capstone.request.LoginRequest;
import com.edu.capstone.response.TotalResponse;
import com.edu.capstone.service.AccountService;
import com.edu.capstone.service.ExcelHelper;

/**
 * @author NhatHH Date: Feb 20, 2022
 */
@RestController
public class AppController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private ClassRepository classRepo;
	@Autowired
	private AccountRepository accountRepository;

	@GetMapping
	public String success() {
		Account account = accountService.getCurrentAccount();
		account.setStatus(AppConstant.ACCOUNT_STATUS_ONLINE);
		accountRepository.saveAndFlush(account);
		return "Login successfull";
	}

	@PostMapping(path = "/register")
	public void register(@RequestBody AccountRequest request) throws MessagingException {
		accountService.create(request);
	}

	@PostMapping(path = "/login", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public void fakeLogin(@RequestBody LoginRequest request) {
		// do login
	}

	@PostMapping(path = "/logout")
	public void fakeLogout() {
		
	}

	@PostMapping(path = "/changepassword")
	public void changePassword(@RequestBody ChangePasswordRequest request) {
		accountService.changePassword(request);
	}

	@PostMapping(path = "/register/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void importRegister(@RequestParam("file") MultipartFile file) {
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				accountService.importRegister(file);
			} catch (MessagingException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GetMapping("/total")
	public TotalResponse getTotal() {
		List<Account> teachers = new ArrayList<>();
		List<Account> students = new ArrayList<>();
		List<Account> accounts = accountService.getAll();
		for (Account acc : accounts) {
			Set<Role> roles = acc.getRoles();
			for (Role role : roles) {
				if (role.getRoleName().equals(AppConstant.ROLE_TEACHER)) {
					teachers.add(acc);
					break;
				} else if (role.getRoleName().equals(AppConstant.ROLE_STUDENT)) {
					students.add(acc);
					break;
				}
			}
		}
		TotalResponse response = TotalResponse.builder().classCount((int) classRepo.count())
				.teacherCount(teachers.size()).studentCount(students.size()).build();
		return response;
	}

	@GetMapping("/download/samplefile")
	public void downloadCsv(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=sample.xlsx");
		ByteArrayInputStream stream = ExcelHelper.registerSample();
		IOUtils.copy(stream, response.getOutputStream());
	}

}
