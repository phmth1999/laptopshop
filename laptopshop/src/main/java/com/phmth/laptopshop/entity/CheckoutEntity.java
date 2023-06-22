package com.phmth.laptopshop.entity;

import java.lang.reflect.Field;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "checkouts")
public class CheckoutEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderEntity order;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(name = "amount")
	private Integer amount;

	@Column(name = "bank_code")
	private String bankCode;

	@Column(name = "bank_tran_no")
	private String bankTranNo;
	
	@Column(name = "card_type")
	private String cardType;
	
	@Column(name = "order_info")
	private String orderInfo;
	
	@Column(name = "pay_date")
	private String payDate;
	
	@Column(name = "response_code")
	private String responseCode;
	
	@Column(name = "tmn_code")
	private String tmnCode;
	
	@Column(name = "transaction_no")
	private String transactionNo;
	
	@Column(name = "transaction_status")
	private String transactionStatus;
	
	@Column(name = "txn_ref")
	private String txnRef;
	
	@Column(name = "secure_hash")
	private String secureHash;
	
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
