package com.sais_soft.ai_ecommere.service.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sais_soft.ai_ecommere.Entity.Product;
import com.sais_soft.ai_ecommere.Repository.Classes.ProductRepositoryImpl;
import com.sais_soft.ai_ecommere.dto.ProductResponseDTO;
import com.sais_soft.ai_ecommere.service.interfaces.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
 
	private static Logger logger=LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepositoryImpl productRepositoryImpl;
	
	
	@Override
	public List<ProductResponseDTO> getAllProducts() {
		logger.info("Eneterd into getAllproducts method");
		
		try {

		List<Product> productsList = productRepositoryImpl.findAllActive();
		logger.info("product size ::{}", productsList.size());
		List<ProductResponseDTO> responseDtoList = productsList.stream().map(product -> {
			ProductResponseDTO responseDto = new ProductResponseDTO();
			BeanUtils.copyProperties(product, responseDto);
			if (Objects.nonNull(product.getCategory())) {
				responseDto.setCategoryName(product.getCategory().getName());
			}

			return responseDto;

		}).collect(Collectors.toList());
		
		logger.info("The Final List Size ::{}",responseDtoList.size());
		return responseDtoList;

	}
	catch (Exception e) {
		logger.error("Exception occured while retrieving products", e);
		return new ArrayList<ProductResponseDTO>();
		}
			
	}

}
