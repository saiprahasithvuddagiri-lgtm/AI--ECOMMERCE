package com.sais_soft.ai_ecommere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
	private String productId;
	private String productName;
	private String brand;
	private double price;
	private int stock;
	private String categoryName;

}
