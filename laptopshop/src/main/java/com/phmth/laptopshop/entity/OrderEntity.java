package com.phmth.laptopshop.entity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phmth.laptopshop.enums.StateCheckout;
import com.phmth.laptopshop.enums.StateOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;
	
	@Column(name = "code_order")
	private String codeOrder;
	
	
	
	
	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "phone")
	private String phone;

	@Column(name = "address_delivery")
	private String address_delivery;
	
	@Column(name = "created_at")
	private Date created_at;
	
	
	
	@Column(name = "num")
	private Integer num;

	@Column(name = "total_money")
	private Integer total_money;
	
	@Column(name = "payment")
	private String payment;
	
	
	
	@Column(name = "state_checkout")
	private StateCheckout stateCheckout;
	
	@Column(name = "state_order")
	private StateOrder stateOrder;
	
	
	

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<OrderDetailEntity> orderdetail;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<CheckoutEntity> checkouts;
	
	public boolean isEmpty()  {

	    for (Field field : this.getClass().getDeclaredFields()) {
	        try {
	            field.setAccessible(true);
	            if (field.get(this)!=null) {
	                return false;
	            }
	        } catch (Exception e) {
	          System.out.println("Exception occured in processing");
	        }
	    }
	    return true;
	}
}
