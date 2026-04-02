package com.sais_soft.ai_ecommere.dto;



import com.sais_soft.ai_ecommere.Entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class saveProductDTO {
	
	private String productId;
	private String productName;
	private String brand;
	private double price;
	private int stock;
	private String category;
	private String internalSecretCode;
    private String description;
    

}
