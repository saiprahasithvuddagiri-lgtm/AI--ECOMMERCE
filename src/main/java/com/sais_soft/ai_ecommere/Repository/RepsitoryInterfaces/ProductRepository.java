package com.sais_soft.ai_ecommere.Repository.RepsitoryInterfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sais_soft.ai_ecommere.Entity.Product;
import com.sais_soft.ai_ecommere.dto.ProductSearchDTO;

public interface ProductRepository {

	Product save(Product product);
	
	Optional<Product> findById(Long Id);
	
	List<Product> findAllActive();
	
	Page<Product> findAllActive(Pageable pageable);
	
	
	Page<Product> getProducts(ProductSearchDTO searchDto);
	
	List<Product> findByPriceRange(double min,double max);
	
	void softDelete(Long id);
	
	long countActiveProducts();

	
	
}
