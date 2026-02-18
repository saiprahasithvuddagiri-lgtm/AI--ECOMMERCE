package com.sais_soft.ai_ecommere.Repository.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.sais_soft.ai_ecommere.Entity.Product;
import com.sais_soft.ai_ecommere.Repository.Exceptions.RepositoryException;
import com.sais_soft.ai_ecommere.Repository.RepsitoryInterfaces.ProductRepository;

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
	public List<Product> searchByName(String keyword) {
		// TODO Auto-generated method stub
		return null;
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
