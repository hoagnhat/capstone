package com.edu.capstone.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecResponse {
	
	private int specId;
	private String name;
	private List<String> subjects;

}
