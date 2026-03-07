package com.sais_soft.ai_ecommere.controller;

import java.net.http.HttpResponse.BodyHandler;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sais_soft.ai_ecommere.dto.ApiResponse;
import com.sais_soft.ai_ecommere.dto.ProductResponseDTO;
import com.sais_soft.ai_ecommere.dto.ProductSearchDTO;
import com.sais_soft.ai_ecommere.service.impls.ProductServiceImpl;

@RestController
@RequestMapping("/ai-ecommerce")
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductServiceImpl productServiceImpl;


	@GetMapping("/products")
	public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts() {

	    logger.info("Entered into getAllProducts in ProductController Class");

	    try {

	        List<ProductResponseDTO> products = productServiceImpl.getAllProducts();

	        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
	        response.setSuccess(true);
	        response.setMessage("Products fetched successfully");
	        response.setData(products);

	        logger.info("Existing from getAllProducts Method in controller ::{}", products.size());

	        return ResponseEntity.ok(response);   // 200 OK

	    } catch (Exception e) {

	        logger.error("Exception while fetching records in Controller ", e);

	        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
	        response.setSuccess(false);
	        response.setMessage("Fetching failed");
	        response.setData(new ArrayList<>());

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500
	    }
	}
	
	@PostMapping("/search")
	public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> searchproducts(
			@RequestBody ProductSearchDTO searchDTO) {
	logger.info("Entered into searching of product method with minimal filters ::{}",searchDTO.toString());
	try {
	List<ProductResponseDTO> productList= productServiceImpl.searchProducts(searchDTO);
	long TotalCountOfProducts = productServiceImpl.CountOfProducts(searchDTO);

    ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
    response.setSuccess(true);
    response.setMessage("Products fetched successfully");
    response.setData(productList);
    response.setCount(TotalCountOfProducts);
    
    logger.info("Existing from these searchProducts method ::{}",productList.size());
    return ResponseEntity.ok(response);
	}
	catch (Exception e) {
		 logger.error("Exception while fetching records in Controller ", e);

	        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
	        response.setSuccess(false);
	        response.setMessage("Fetching failed");
	        response.setData(new ArrayList<>());
	        response.setCount(0);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

	}

	}
	
	
	
	
}
