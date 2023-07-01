package com.phmth.laptopshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.entity.ProductElasticsearch;
import com.phmth.laptopshop.repository.IProductElasticsearchRepository;

@Service
public class ProductElasticsearchService {

	@Autowired
	private IProductElasticsearchRepository productElasticsearch;

	public List<ProductElasticsearch> createProductByList(List<ProductElasticsearch> listProduct) {
		return (List<ProductElasticsearch>) productElasticsearch.saveAll(listProduct);
	}

	public void dataSynchronization(ProductDto productDto) {
		ProductElasticsearch productElastic = new ProductElasticsearch();

		productElastic.setId(productDto.getId());
		productElastic.setName(productDto.getName());
		productElastic.setDescription(productDto.getDescription());
		productElastic.setPrice(productDto.getPrice());
		productElastic.setThumbnail(productDto.getThumbnail());

		productElasticsearch.save(productElastic);
	}

	public void dataSynchronization(List<ProductDto> listProductDto) {

		List<ProductElasticsearch> listProductElasticsearch = new ArrayList<>();

		for (ProductDto productEntity : listProductDto) {
			ProductElasticsearch productElasticsearch = new ProductElasticsearch();
			productElasticsearch.setId(productEntity.getId());
			productElasticsearch.setName(productEntity.getName());
			productElasticsearch.setDescription(productEntity.getDescription());
			productElasticsearch.setPrice(productEntity.getPrice());
			productElasticsearch.setThumbnail(productEntity.getThumbnail());

			listProductElasticsearch.add(productElasticsearch);
		}

		productElasticsearch.saveAll(listProductElasticsearch);
	}

	public Optional<ProductElasticsearch> getProductById(long id) {
		return productElasticsearch.findById(id);
	}

	public ProductElasticsearch updateProduct(ProductElasticsearch product) {
		return productElasticsearch.save(product);
	}

	public boolean removeProduct(long id) {
		productElasticsearch.deleteById(id);
		if (productElasticsearch.existsById(id)) {
			return false;
		}
		return true;
	}

	public Iterable<ProductElasticsearch> getAllProduct() {
		return productElasticsearch.findAll();
	}

	public List<ProductElasticsearch> search(String term) {
		return productElasticsearch.findByNameStartsWith(term);
	}

}
