package com.phmth.laptopshop.dto.request;

import java.lang.reflect.Field;

import com.phmth.laptopshop.enums.StateOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchOrderRequest {

	private String nameOrder;
	private String phone;
	private String address;
	private String totalMoney;
	private String orderDate;
	private String payment;
	private StateOrder status;
	
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
