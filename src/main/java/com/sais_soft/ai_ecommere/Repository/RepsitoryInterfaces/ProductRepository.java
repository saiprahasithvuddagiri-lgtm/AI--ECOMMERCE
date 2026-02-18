package com.sais_soft.ai_ecommere.Repository.RepsitoryInterfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sais_soft.ai_ecommere.Entity.Product;

public interface ProductRepository {

	Product save(Product product);
	
	Optional<Product> findById(Long Id);
	
	List<Product> findAllActive();
	
	Page<Product> findAllActive(Pageable pageable);
	
	
	List<Product> searchByName(String keyword);
	
	List<Product> findByPriceRange(double min,double max);
	
	void softDelete(Long id);
	
	long countActiveProducts();
	
}
