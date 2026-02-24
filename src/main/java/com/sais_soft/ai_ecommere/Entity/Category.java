package com.sais_soft.ai_ecommere.Entity;



import java.time.LocalDate;

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
@Table(name="category")
@SQLDelete(sql="UPDATE category SET is_deleted =true where pk_id =?")
@SQLRestriction("is_deleted=false")
@Setter
@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
    @Column(name="pk_id")
	private Long iD;
	
	
	@Column(name="category_name",nullable=false,unique=true)
	private String name;
	
	@CreationTimestamp
	@Column(name ="create_date",updatable = false)
    private LocalDate createDate;
	
	
	@UpdateTimestamp
	@Column(name="update_date",updatable = true)
	private LocalDate updateDate;
	
	
	@Column(name="is_deleted",nullable = false)
	private Boolean isDeleted;
	
	
}



