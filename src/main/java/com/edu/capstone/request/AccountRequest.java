package com.edu.capstone.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import com.edu.capstone.common.constant.RegexConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH 
 * Date: Jan 16, 2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
	
	// For register
	@Nullable
	private Integer khoa; // Khóa sinh viên
	private int roleId; // ID role
	private int specializationId; // ID chuyên ngành
	@Email
	private String personalEmail; // Email cá nhân của user
	@Pattern(regexp = RegexConstant.NAME_REGEXP)
	private String name; // Họ tên đầy đủ
	private int age; // Tuổi
	private String avatar; // Image path của avatar
	@Pattern(regexp = RegexConstant.PHONE_REGEXP)
	private String phone; // Số điện thoại
	private int gender; // Giới tính (0: Nam, 1: Nữ)
	private String address; // Địa chỉ
	
	// For change password
	@Length(min = RegexConstant.PASSWORD_LENGTH)
	private String oldPassword;
	@Length(min = RegexConstant.PASSWORD_LENGTH)
	private String newPassword;
	@Length(min = RegexConstant.PASSWORD_LENGTH)
	private String confirmPassword;

}
