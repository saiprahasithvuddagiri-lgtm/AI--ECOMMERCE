package com.sais_soft.ai_ecommere.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="products")
@SQLDelete(sql="UPDATE products SET is_deleted =true where pk_id =?")
@SQLRestriction( "is_deleted = false")
@Setter
@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="pk_id")
  private Long pk_id;
  
  
 @Column(name="product_id",nullable=false,unique=true)	 
  private String productId;
  
  @Column(name ="product_name")
  private String productName;
  
  @Column(name="SKU_Code")
  private String internalSecretCode;
  
  @Column(name="product_description")
  private String description;
  
  @Column(name="product_brand")
  private String brand;
  
  @Column(name ="price")
  private double price;
  
  
  @Column(name="product_stock")
  private int stock;
  
  @CreationTimestamp
  @Column(name="create_date",updatable=false)
  private LocalDateTime createDate;
  
  @UpdateTimestamp
  @Column(name="update_date")
  private LocalDateTime updateDate ;
  
  @Column(name ="is_deleted",nullable=false)
  private boolean isDeleted;
  
}
