package com.sais_soft.ai_ecommere.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sais_soft.ai_ecommere.dto.ApiResponse;
import com.sais_soft.ai_ecommere.dto.ProductResponseDTO;
import com.sais_soft.ai_ecommere.service.impls.ProductServiceImpl;

@RestController
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductServiceImpl productServiceImpl;

	@GetMapping("/products")
	public ApiResponse<List<ProductResponseDTO>> getAllProducts() {
		logger.info("Entered into getAllProducts in ProductController Class");
		try {
			List<ProductResponseDTO> products = productServiceImpl.getAllProducts();
			ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
			response.setSuccess(true);
			response.setMessage("Products fetched successfully");
			response.setData(products);
            logger.info("Existing from getAllProducts Method in controller ::{}",products.size());
			return response;
		} catch (Exception e) {
			logger.error("Exception while fetching records in Controller ", e);
			ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
			response.setSuccess(false);
			response.setMessage("fetching failed ");
			response.setData(new ArrayList<ProductResponseDTO>());
			return response;
		}
	}

}
