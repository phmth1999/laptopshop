package com.phmth.laptopshop.entity;

import java.lang.reflect.Field;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "advertisements")
public class AdvertisementEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "thumbnail")
	private String thumbnail;

	@Column(name = "link")
	private String link;

	@Column(name = "created_at")
	private Date created_at;
	
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
