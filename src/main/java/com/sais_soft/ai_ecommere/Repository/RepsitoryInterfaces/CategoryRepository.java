package com.sais_soft.ai_ecommere.Repository.RepsitoryInterfaces;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sais_soft.ai_ecommere.Entity.Category;


public interface CategoryRepository {
   List<Category> getCategoryByType(String category);
}
