package com.phmth.laptopshop.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Document(indexName = "products")
public class ProductElasticsearch {
	@Id
	private Long id;

	@Field
	private String name;

	@Field
	private String description;

	@Field
	private Integer price;
	
	@Field
	private String thumbnail;

}
