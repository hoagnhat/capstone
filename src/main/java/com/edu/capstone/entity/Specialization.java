package com.edu.capstone.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.edu.capstone.common.constant.RegexConstant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH
 * Date: Jan 31, 2022
 */
@Entity
@Table(name = "[SPECIALIZATION]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specialization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Pattern(regexp = RegexConstant.NAME_REGEXP)
	@Column(name = "name")
	private String name; // Tên chuyên ngành
	
}
