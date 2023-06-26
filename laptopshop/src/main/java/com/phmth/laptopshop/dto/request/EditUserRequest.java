package com.phmth.laptopshop.dto.request;

import java.lang.reflect.Field;

import com.phmth.laptopshop.enums.AuthenticationType;
import com.phmth.laptopshop.enums.StateUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequest {

	private Long id;
	
	private String fullname;
	
	private String sex;
	
	private String birthday;
	
	private String address;
	
	private StateUser stateUser;
	
	private AuthenticationType authType;
	
	private Long role;
	
	private String img;
	
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
