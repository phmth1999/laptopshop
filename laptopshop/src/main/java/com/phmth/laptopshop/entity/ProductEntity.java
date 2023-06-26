package com.phmth.laptopshop.entity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name = "products")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private CategoryEntity category;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private BrandEntity brand;

	@Column(name = "name", length = 350, unique = true)
	private String name;

	@Column(name = "price")
	private Integer price;

	@Column(name = "discount")
	private Integer discount;
	
	@Column(name = "quantity_in_stock")
	private Integer quantity_in_stock;

	@Column(name = "thumbnail", length = 500)
	private String thumbnail;

	@Column(name = "description")
	@Lob
	private String description;

	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_at")
	private Date update_at;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<GalleryEntity> galleries;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CommentEntity> comments;

	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<OrderDetailEntity> order_details;
	
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
