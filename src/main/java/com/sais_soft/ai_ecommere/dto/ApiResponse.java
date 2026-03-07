package com.sais_soft.ai_ecommere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
	
	private boolean success;
	
	private String message;
	
	private long count;
	
	
	
	private T data;

}
