package com.sais_soft.ai_ecommere.Repository.Classes;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sais_soft.ai_ecommere.Entity.Category;
import com.sais_soft.ai_ecommere.Repository.RepsitoryInterfaces.CategoryRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public  class CategoryRepositoryImpl implements CategoryRepository {

	
	private static Logger logger =LoggerFactory.getLogger(CategoryRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	

@Override
public List<Category> getCategoryByType(String category) {
	
	logger.info("Entered into method to get category ::{}",category);
	  return   entityManager.createQuery("from Category Where name = :category  ",Category.class)
	    .setParameter("category", category).getResultList();
		
}
	
}
