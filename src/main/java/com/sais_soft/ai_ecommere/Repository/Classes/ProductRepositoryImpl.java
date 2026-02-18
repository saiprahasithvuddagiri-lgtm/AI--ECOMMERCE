package com.sais_soft.ai_ecommere.Repository.Classes;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sais_soft.ai_ecommere.Entity.Product;
import com.sais_soft.ai_ecommere.Repository.Exceptions.RepositoryException;
import com.sais_soft.ai_ecommere.Repository.RepsitoryInterfaces.ProductRepository;
import com.sais_soft.ai_ecommere.dto.ProductSearchDTO;
import com.sais_soft.ai_ecommere.dto.SortRequestDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;


@Repository
public class ProductRepositoryImpl implements ProductRepository{
	private static Logger logger=LoggerFactory.getLogger(ProductRepositoryImpl.class);
	
	
	@PersistenceContext
	private  EntityManager entityManager;

	@Override
	public Product save(Product product) {
		logger.info("Enetered into save Product Method");
		try {
			if(product.getPk_id() == null) {
				logger.info("the product is new product");
		       entityManager.persist(product);
		       
			}
			else {
				logger.info("The records of product updated ");
				entityManager.merge(product);
			}
			 logger.info("the product is saved");
			 logger.info("Exist from the save method ::{}",product.toString());
			
			return product;	
		}	
		catch(Exception e) {
			throw new  RepositoryException("Error in saving product", e);
		}
		
		
		
	}


	@Override
	public Optional<Product> findById(Long Id) {
		logger.info("Entered into findById method::{}",Id);
		try {
		TypedQuery<Product> query= entityManager.createQuery("From  Product p where p.pk_id = :id "
				+ "and p.isDeleted=false",Product.class);
		query.setParameter("id",Id);
		List<Product> result = query.getResultList();
		logger.info("result find by Id :{}",result.size());
		return result.stream().findFirst();
		}
		catch(Exception e) {
			throw new RepositoryException("Error fetching product with id: " + Id, e);
		}
		
	}


	@Override
	public List<Product> findAllActive() {
		logger.info("Entered into findAllActive Method");
		try {
		 TypedQuery<Product> query = entityManager.createQuery("From product p where isDeleted=false",
				 Product.class);
		logger.info(" the products list ::{}",query.getResultList()); 
		 return query.getResultList();
		}
		catch(Exception e) {
			throw new RepositoryException("Error to fetch products" ,e);
		}
		
		
	}


	@Override
	public Page<Product> findAllActive(Pageable pageable) {
		logger.info("Entered into findAllActive method");
		try {
			 StringBuilder jpql =new StringBuilder("From Product p where isDeleted =false");
			if(pageable.getSort().isSorted()) {
				jpql.append("Order by");
				List<String> sortOrders =new ArrayList<>();
				pageable.getSort().forEach(order ->{
					String property =order.getProperty();
					String direction =order.getDirection().name();
					sortOrders.add("p."+property+" "+direction);
				});
				jpql.append(String.join(", ",sortOrders));
			}
			logger.info("Query for fetching results ::{} ",jpql);
			TypedQuery<Product> query=entityManager.createQuery(jpql.toString(),Product.class);
			query.setFirstResult((int)pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
			
			List<Product> resultList =query.getResultList();
			 Long total = entityManager.createQuery(
		                "SELECT COUNT(p) FROM Product p WHERE p.isDeleted = false",
		                Long.class
		        ).getSingleResult();
			 logger.info("The Result List Size ::{}",total);
			 return new PageImpl<>(resultList,pageable,total);
		}
		catch(Exception e) {
			throw new RepositoryException("Error fetching paginated products", e);
		}
		
	}


	@Override
	public Page<Product> getProducts(ProductSearchDTO searchDto) {
		logger.info("Entered into getproducts method::{}",searchDto);
		try {
		StringBuilder jpql =new StringBuilder("select p from Product p where isDeleted =false");
		Map<String,Object> parameters =new HashMap<String, Object>();
		// get all entity fields from product table 
		 Set<String> validFields = Arrays.stream(Product.class.getDeclaredFields())
	                .map(Field::getName)
	                .collect(Collectors.toSet());
	     logger.info("get string type dervived fields from product entity ::{}",validFields);
	     if(Objects.nonNull(searchDto.getKeyword()) && !searchDto.getKeyword().isBlank()) {
	    	 List<String> stringFields = Arrays.stream(Product.class.getDeclaredFields())
	                    .filter(f -> f.getType().equals(String.class))
	                    .map(Field::getName)
	                    .toList();
	    	 if(!stringFields.isEmpty()) {
	    		 jpql.append("And (");
	    		 List<String> Conditions =new ArrayList<>();
	    		 for(String field :stringFields) {
	    			 Conditions.add("LOWER(p."+field+") LIKE (:keyword)");
	    		 }
	    		 jpql.append(String.join("OR",Conditions));
	    		 jpql.append(")");
	    		 parameters.put("keyword","%"+searchDto.getKeyword().trim()+"%");
	    		 
	    	 }
	    	 
	     }
	     logger.info("Query for fetching with keyword ::{}",jpql);
	     logger.info("parameters ::{}",parameters);
	     if(Objects.nonNull(searchDto.getFilters())){
	    	 logger.info("get filters::{}",searchDto.getFilters());
	    	 for(Map.Entry<String, Object> entry : searchDto.getFilters().entrySet()) {
	    		  String fieldvalue =entry.getKey(); 
	    		  Object value =entry.getValue();
	    		  if(!validFields.contains(fieldvalue)) {
	    			  throw new IllegalArgumentException("Invalid field "+fieldvalue);
	    		  }
	    		  if(value instanceof String) {
	    			  jpql.append("And LOWER(p.")
	    			   .append(fieldvalue)
	    			   .append(") LIKE LOWER(:")
	    			   .append(fieldvalue)
	    			   .append(")");
	    			  parameters.put(fieldvalue,"%"+value.toString().trim()+"%");
	    			  
	    		  }
	    		  else {
	    			  jpql.append("And p.")
	    			  .append(fieldvalue)
	    			  .append("= :")
	    			  .append(fieldvalue);
	    			  
	    			  parameters.put(fieldvalue, value);
	    		  }
	    	 }
	     }
	     logger.info("Query after fetching filters::{}",jpql);
	     if(Objects.nonNull(searchDto.getSort()) && !searchDto.getSort().isEmpty()) {
	    	 jpql.append(" ORDER BY ");
	    	 List<String> sortOrders = new ArrayList<String>();
	    	 for(SortRequestDTO sort : searchDto.getSort()) {
	    		 if(!validFields.contains(sort.getField())) {
	    			  throw new IllegalArgumentException("Invalid field "+sort.getField());
	    		  }
	    		 
	    		 String direction = sort.getDirection().equalsIgnoreCase("ASC")?"ASC":"DESC";
	    		sortOrders.add("p."+sort.getField()+" "+direction);
	    		
	    	 }
	    	 jpql.append(String.join(",",sortOrders));
	     } 
	     logger.info("query after with sorts::{}",jpql);
	     TypedQuery<Product> query = entityManager.createQuery(jpql.toString(),Product.class);
	     parameters.forEach(query::setParameter);
	     int page =searchDto.getPage();
	     int size = searchDto.getSize();
	     query.setFirstResult(page*size);
	     query.setMaxResults(size);
	     List<Product> productsList = query.getResultList();  
	     logger.info(" the result list ::{}",productsList.size());
	     String countQueryStr = jpql.toString()
	                .replaceFirst("SELECT p FROM", "SELECT COUNT(p) FROM")
	                .replaceFirst("ORDER BY.*", "");

	        TypedQuery<Long> countQuery =
	                entityManager.createQuery(countQueryStr, Long.class);

	        parameters.forEach(countQuery::setParameter);

	        Long total = countQuery.getSingleResult();
	    		                  
	        Pageable pageable = PageRequest.of(page, size);

	        return new PageImpl<>(productsList, pageable, total);	 
	     
		}
		catch(Exception e) {
			 throw new RepositoryException("Error executing dynamic search", e);
		}
	}


	@Override
	public List<Product> findByPriceRange(double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void softDelete(Long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public long countActiveProducts() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	


}
