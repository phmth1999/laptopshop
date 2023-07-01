package com.phmth.laptopshop.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Document(indexName = "employee")
public class Employee {

	@Id
	private String id;

	private String name;

	private double price;

}
