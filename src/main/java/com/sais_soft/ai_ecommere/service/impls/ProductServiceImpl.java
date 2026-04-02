package com.sais_soft.ai_ecommere.service.impls;

import java.util.ArrayList;
import java.util.List;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sais_soft.ai_ecommere.Entity.Category;
import com.sais_soft.ai_ecommere.Entity.Product;
import com.sais_soft.ai_ecommere.Repository.Classes.CategoryRepositoryImpl;
import com.sais_soft.ai_ecommere.Repository.Classes.ProductRepositoryImpl;
import com.sais_soft.ai_ecommere.dto.ProductResponseDTO;
import com.sais_soft.ai_ecommere.dto.ProductSearchDTO;
import com.sais_soft.ai_ecommere.dto.saveProductDTO;
import com.sais_soft.ai_ecommere.service.interfaces.ProductService;
@Service
public class ProductServiceImpl implements ProductService {
 
	private static Logger logger=LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepositoryImpl productRepositoryImpl;
	
	@Autowired(required=true)
	private CategoryRepositoryImpl categoryRepositoryImpl;
	
	
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
	         productRepositoryImpl.getProducts(searchDto);
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


	@Override
	public ProductResponseDTO saveProduct(saveProductDTO product) {
		logger.info("Enterd into method to save product by admin ::{}", product.toString());
		Product saveProduct = new Product();
		BeanUtils.copyProperties(product, saveProduct);
		logger.info("saveproduct before saving in db ::{}", saveProduct);
		String category =product.getCategory();
		List<Category> resultCategoryList = categoryRepositoryImpl.getCategoryByType(category);
		logger.info("category type ::{}",resultCategoryList.get(0).toString());
		if(Objects.nonNull(resultCategoryList) && Objects.nonNull(resultCategoryList.get(0)) 
				&& ! resultCategoryList.get(0).getName().isEmpty()) {
			saveProduct.setCategory(resultCategoryList.get(0));
		}
		logger.info("after category setting ::{}",saveProduct);
		productRepositoryImpl.save(saveProduct);
		logger.info("saved product ::{}", saveProduct.toString());
		ProductResponseDTO responseProduct = new ProductResponseDTO();
	     BeanUtils.copyProperties(saveProduct, responseProduct);
	     responseProduct.setCategoryName(saveProduct.getCategory().getName());
	     return responseProduct;
	}
	
	@Transactional
	public ProductResponseDTO getProductById(Long Id) {
	Product	product1 =productRepositoryImpl.findById(Id)
			.orElseThrow(()->new RuntimeException("The Product not found"));
	product1.setBrand("Sony");
	product1.setBrand("sony");
	product1.setBrand("sonY");
	// Business logic
    if (product1.isDeleted()) {
        throw new RuntimeException("Product is deleted");
    }
     ProductResponseDTO response=new ProductResponseDTO();
     BeanUtils.copyProperties(product1, response);
     return response;	

	}
	
	
	

}
