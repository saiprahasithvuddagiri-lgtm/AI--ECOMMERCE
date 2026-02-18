package com.sais_soft.ai_ecommere.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sais_soft.ai_ecommere.dto.ApiResponse;



@RestController
@RequestMapping("/test")
public class testController {
	
	private static Logger logger=LoggerFactory.getLogger(testController.class);
	
	@GetMapping(
		    value = "/hello")
     public ResponseEntity<ApiResponse> test() {
	   logger.info("Entering into  test method in testController class");
	   ApiResponse response =
               new ApiResponse("Day 3 working!", "200");
	   return ResponseEntity.ok(response);
     }
     
     
     
     
}
