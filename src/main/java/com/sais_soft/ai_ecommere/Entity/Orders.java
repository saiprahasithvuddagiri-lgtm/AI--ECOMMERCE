package com.sais_soft.ai_ecommere.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="orders")
@SQLDelete(sql="UPDATE orders SET is_deleted =true where pk_id =?")
@SQLRestriction("is_deleted = false")
@Getter
@Setter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="pk_id")
	private Long pk_id;
	
	@Column(name="order_id" ,nullable = false,unique= true )
    private UUID orderID;
	
	@ManyToOne
    @JoinColumn(name= "fk_product_id")
	private Product product;
	
	@Column(name="quantity_ordered")
    private int quantityOrdered; 	
	
	@Column(name = "balance_quantity_stock")
	private int balanceQuantityStock;
	
	@Column(name ="status")
	private  String status;
	
	@Column(name ="is_deleted")
	private boolean isDeleted;
	 
	
	@CreationTimestamp
	@Column(name ="created_date",updatable = false)
	private LocalDateTime   createDate;
	
	
	@UpdateTimestamp
	@Column(name ="updated_date")
	private LocalDateTime updateDate;
	
	
	@Column(name ="created_user")
	private String createdUser;
	
	
	@Column(name ="updated_user")
    private String updatedUser;
	
	
	 @PrePersist
	    public void prePersist() {
	        this.orderID = UUID.randomUUID();
	        this.createDate = LocalDateTime.now();
	        this.updateDate = LocalDateTime.now();
	        this.createdUser = "SYSTEM";
	        this.updatedUser = "SYSTEM";
	    }
	 
	 
	 @PreUpdate
	    public void preUpdate() {
	        this.updateDate = LocalDateTime.now();

	    }
	
	

}
