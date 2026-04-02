package com.sais_soft.ai_ecommere.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sais_soft.ai_ecommere.Entity.Product;
import com.sais_soft.ai_ecommere.dto.ApiResponse;
import com.sais_soft.ai_ecommere.dto.ProductResponseDTO;
import com.sais_soft.ai_ecommere.dto.ProductSearchDTO;
import com.sais_soft.ai_ecommere.dto.saveProductDTO;
import com.sais_soft.ai_ecommere.service.impls.ProductServiceImpl;

@RestController
@RequestMapping("/ai-ecommerce")
public class ProductController {

	private static Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductServiceImpl productServiceImpl;
	
	
	
	@PostMapping("/saveProduct")
	public ResponseEntity<ApiResponse<ProductResponseDTO>>  saveProduct(@RequestBody  saveProductDTO product){
		logger.info("Entered into method to save product in Db from controller ::{}",product);
		try {
	    ProductResponseDTO saveProduct = productServiceImpl.saveProduct(product);
	    logger.info("product successfully save in DB::{}",saveProduct.toString());
	    ApiResponse<ProductResponseDTO> response =new ApiResponse<ProductResponseDTO>();
	    response.setMessage("Succcessfully product saved ");
	    response.setSuccess(true);
	    response.setData(saveProduct);
	    return ResponseEntity.ok(response);
		}		
		catch(Exception e) {
			logger.error("Exception retriving while saveing product ::{}",e);
			ApiResponse<ProductResponseDTO> response =new ApiResponse<ProductResponseDTO>();
		    response.setMessage("failed to save Product ");
		    response.setSuccess(false);
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			
		}
		
		
	}


	@GetMapping("/products")
	public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts() {

	    logger.info("Entered into getAllProducts in ProductController Class");

	    try {

	        List<ProductResponseDTO> products = productServiceImpl.getAllProducts();

	        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>();
	        response.setSuccess(true);
	        response.setMessage("Products fetched successfully");
	        response.setData(products);
	        response.setCount(products.size());

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
	@GetMapping("/getProduct/{id}")
	public ResponseEntity< ApiResponse<ProductResponseDTO>> getProductById(@PathVariable Long id){
		logger.info("Entered into method to get product in Db from controller ::{}",id);
		try {
	    ProductResponseDTO getProduct = productServiceImpl.getProductById(id);
	    logger.info("product successfully accessed in DB::{}",getProduct.toString());
	    ApiResponse<ProductResponseDTO> response =new ApiResponse<ProductResponseDTO>();
	    response.setMessage("Succcessfully get product ");
	    response.setSuccess(true);
	    response.setData(getProduct);
	    return ResponseEntity.ok(response);
		}		
		catch(Exception e) {
			logger.error("Exception retriving while saveing product ::{}",e);
			ApiResponse<ProductResponseDTO> response =new ApiResponse<ProductResponseDTO>();
		    response.setMessage(" Product not found");
		    response.setSuccess(false);
		    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			
		}
		
	}
	
	
	
	
}
