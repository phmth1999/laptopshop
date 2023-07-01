package com.phmth.laptopshop.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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

	private String name;

	private String description;

	private Integer price;
	
	private String thumbnail;

}
