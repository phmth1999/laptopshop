package com.phmth.laptopshop.entity;

import java.lang.reflect.Field;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "brands")
public class BrandEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", length = 100, unique = true)
	private String name;

	@OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<ProductEntity> products;
	
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
