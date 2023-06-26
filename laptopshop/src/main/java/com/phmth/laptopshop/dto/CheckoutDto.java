package com.phmth.laptopshop.dto;

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDto {
	
	private Long id;
	private Long order;
	private Long user;
	private Integer amount;
	private String bankCode;
	private String bankTranNo;
	private String cardType;
	private String orderInfo;
	private String payDate;
	private String responseCode;
	private String tmnCode;
	private String transactionNo;
	private String transactionStatus;
	private String txnRef;
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
