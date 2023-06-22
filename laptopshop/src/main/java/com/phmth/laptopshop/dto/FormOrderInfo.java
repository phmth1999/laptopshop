package com.phmth.laptopshop.dto;

import java.lang.reflect.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FormOrderInfo {

	private Long userId;
	private String name;
	private String email;
	
	private String phone;
	private String address_delivery;
	
	private Integer num;
	private Integer totalMoney;
	private String payment;
	
	private String codeOrder;
	
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
