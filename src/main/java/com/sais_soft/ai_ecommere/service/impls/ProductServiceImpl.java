package com.sais_soft.ai_ecommere.service.impls;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.sais_soft.ai_ecommere.Entity.Product;
import com.sais_soft.ai_ecommere.Repository.Classes.ProductRepositoryImpl;
import com.sais_soft.ai_ecommere.dto.ProductResponseDTO;
import com.sais_soft.ai_ecommere.dto.ProductSearchDTO;
import com.sais_soft.ai_ecommere.service.interfaces.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
 
	private static Logger logger=LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepositoryImpl productRepositoryImpl;
	
	
	@Override
	public List<ProductResponseDTO> getAllProducts() {
		logger.info("Eneterd into getAllproducts method");
		
	

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


	@Override
	public List<ProductResponseDTO> searchProducts(ProductSearchDTO searchDto) {
	   logger.info("Entered into searchProducts method ::{}, ::{}, ::{}, ::{}",searchDto.getKeyword(),
			      searchDto.getFilters(),searchDto.getPage(),searchDto.getSort());
	         Page<Product>  Pageresult = productRepositoryImpl.getProducts(searchDto);
	         Long Total = Pageresult.getTotalElements();  
	         logger.info("Page Product :: {}",Pageresult.toList().toString());
	         List<ProductResponseDTO> resultList = Pageresult.toList().stream().map(
	        		 product->{
	        			 ProductResponseDTO response = new ProductResponseDTO();
	        			 BeanUtils.copyProperties(product,response);
	        			 if(Objects.nonNull(product.getCategory())) {
	        				 response.setCategoryName(product.getCategory().getName());
	        			 }
	        			 return response;
	        		 }).toList();
    	        		 
	         
	         logger.info("Response Result List ::{}",resultList.size());
	         
	         
	   return resultList;
	   
	   
	}

	public long CountOfProducts(ProductSearchDTO searchDTO) {
		logger.info("Entered into count method ");
		long TotalCount = productRepositoryImpl.countActiveProductsWithFilters(searchDTO);
	    logger.info("Total Count ::{}",TotalCount);
	    return TotalCount;
	}


	
	

}
