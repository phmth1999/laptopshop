//package com.phmth.laptopshop.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.phmth.laptopshop.entity.ProductElasticsearch;
//import com.phmth.laptopshop.service.impl.ProductElasticsearchService;
//
//@RestController
//@RequestMapping("/elasticsearch")
//public class ProductElasticsearchController {
//
//	@Autowired
//	private ProductElasticsearchService productElasticsearchService;
//	
//	@PostMapping("/saveProduct")
//	public List<ProductElasticsearch> createProductByList(@RequestBody List<ProductElasticsearch> listProduct){
//		return productElasticsearchService.createProductByList(listProduct);
//	}
//	
//	@GetMapping("/getProduct/{id}")
//	public Optional<ProductElasticsearch> getProductById(@PathVariable("id") long id) {
//		return productElasticsearchService.getProductById(id);
//	}
//	
//	@PutMapping("/updateProduct")
//	public ProductElasticsearch createProductByList(@RequestBody ProductElasticsearch product){
//		return productElasticsearchService.updateProduct(product);
//	}
//	
//	@DeleteMapping("/removeProduct/{id}")
//	public boolean removeProductById(@PathVariable("id") long id) {
//		return productElasticsearchService.removeProduct(id);
//	}
//	
//	@GetMapping("/search")
//	public List<ProductElasticsearch> search(@RequestParam("term") String term){
//		return productElasticsearchService.search(term);
//	}
//	@GetMapping("/searchAll")
//	public Iterable<ProductElasticsearch> searchAll(){
//		return productElasticsearchService.getAllProduct();
//	}
//	
//}
