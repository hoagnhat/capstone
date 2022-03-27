package com.edu.capstone.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheSubResponse {

	private int id;
	private String name;
	private String code;
	private Date startDate;
	private Date endDate;
	
}
