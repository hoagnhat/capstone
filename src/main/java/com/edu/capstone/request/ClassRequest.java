package com.edu.capstone.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassRequest {

	private int khoa;
	private int specId;
	private int size;
	private int semester;
	
}
