package com.phmth.laptopshop.dto;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.phmth.laptopshop.enums.StateCheckout;
import com.phmth.laptopshop.enums.StateOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
	
	private Long id;
	private Long user;
	private String codeOrder;
	private String name;
	private String email;
	private String phone;
	private String address_delivery;
	private Date created_at;
	private Integer num;
	private Integer total_money;
	private String payment;
	private StateCheckout stateCheckout;
	private StateOrder stateOrder;
	private List<OrderDetailDto> orderdetail;
	
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
