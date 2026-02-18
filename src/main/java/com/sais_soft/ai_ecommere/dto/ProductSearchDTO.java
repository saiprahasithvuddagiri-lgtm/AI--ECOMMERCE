package com.sais_soft.ai_ecommere.dto;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProductSearchDTO {

	private String keyword;
	
	private Map<String,Object> filters;
	@NotNull
	@Min(1)
	private int page;
	
	@NotNull
	@Min(15)
	private int size;
	
	private List<SortRequestDTO> sort;
	
}
