package com.edu.capstone.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.edu.capstone.common.constant.RegexConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH 
 * Date: Jan 25, 2022
 */
@Entity
@Table(name = "[PROFILE]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

	@Id
	@Column(name = "account_id")
	private String accountId;
	@Pattern(regexp = RegexConstant.NAME_REGEXP)
	@Column(name = "name", columnDefinition = "nvarchar(255)")
	private String name; // Họ tên đầy đủ
	@NotNull
	@Column(name = "age")
	private int age; // Tuổi
	@Column(name = "avatar")
	private String avatar; // Image path của avatar
	// TODO: Need make person email unique
	@NotNull
	@Email
	@Column(name = "personal_email")
	private String personalEmail; // Email cá nhân của user
	@NotNull
	@Pattern(regexp = RegexConstant.PHONE_REGEXP)
	@Column(name = "phone")
	private String phone; // Số điện thoại
	@Column(name = "gender")
	private int gender; // Giới tính (0: Nam, 1: Nữ)
	@Column(name = "address")
	private String address; // Địa chỉ
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OnDelete(action = OnDeleteAction.CASCADE)
	@MapsId
	private Account account;

}
