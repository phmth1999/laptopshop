//package com.phmth.laptopshop.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.phmth.laptopshop.dto.ProductDto;
//import com.phmth.laptopshop.entity.ProductElasticsearch;
//import com.phmth.laptopshop.repository.IProductElasticsearchRepository;
//
//@Service
//public class ProductElasticsearchService {
//
//	@Autowired
//	private IProductElasticsearchRepository productElasticsearch;
//
//	public List<ProductElasticsearch> createProductByList(List<ProductElasticsearch> listProduct) {
//		return (List<ProductElasticsearch>) productElasticsearch.saveAll(listProduct);
//	}
//
//	public void dataSynchronization(ProductDto productDto) {
//		ProductElasticsearch productElastic = new ProductElasticsearch();
//
//		productElastic.setId(productDto.getId());
//		productElastic.setName(productDto.getName());
//		productElastic.setDescription(productDto.getDescription());
//		productElastic.setPrice(productDto.getPrice());
//		productElastic.setThumbnail(productDto.getThumbnail());
//
//		productElasticsearch.save(productElastic);
//	}
//
//	public void dataSynchronization(List<ProductDto> listProductDto) {
//
//		List<ProductElasticsearch> listProductElasticsearch = new ArrayList<>();
//
//		for (ProductDto productEntity : listProductDto) {
//			ProductElasticsearch productElasticsearch = new ProductElasticsearch();
//			productElasticsearch.setId(productEntity.getId());
//			productElasticsearch.setName(productEntity.getName());
//			productElasticsearch.setDescription(productEntity.getDescription());
//			productElasticsearch.setPrice(productEntity.getPrice());
//			productElasticsearch.setThumbnail(productEntity.getThumbnail());
//
//			listProductElasticsearch.add(productElasticsearch);
//		}
//
//		productElasticsearch.saveAll(listProductElasticsearch);
//	}
//
//	public Optional<ProductElasticsearch> getProductById(long id) {
//		return productElasticsearch.findById(id);
//	}
//
//	public ProductElasticsearch updateProduct(ProductElasticsearch product) {
//		return productElasticsearch.save(product);
//	}
//
//	public boolean removeProduct(long id) {
//		productElasticsearch.deleteById(id);
//		if (productElasticsearch.existsById(id)) {
//			return false;
//		}
//		return true;
//	}
//
//	public Iterable<ProductElasticsearch> getAllProduct() {
//		return productElasticsearch.findAll();
//	}
//
//	public List<ProductDto> search(String term) {
//		List<ProductElasticsearch> productElastic = productElasticsearch.findByNameStartsWith(term);
//		List<ProductDto> listProductDto = new ArrayList<>();
//		for (ProductElasticsearch productElasticsearch : productElastic) {
//			ProductDto productDto = new ProductDto();
//			productDto.setId(productElasticsearch.getId());
//			productDto.setName(productElasticsearch.getName());
//			productDto.setDescription(productElasticsearch.getDescription());
//			productDto.setPrice(productElasticsearch.getPrice());
//			productDto.setThumbnail(productElasticsearch.getThumbnail());
//			
//			listProductDto.add(productDto);
//		}
//
//		
//		return listProductDto;
//	}
//	
//	/*Match Query: Truy vấn tìm kiếm dựa trên sự khớp chính xác hoặc gần giống của các từ khóa văn bản.*/
////	public List<MyDocument> searchByContent(String searchTerm) {
////        MatchQueryBuilder query = QueryBuilders.matchQuery("content", searchTerm);
////        Iterable<MyDocument> result = documentRepository.search(query);
////        return StreamSupport.stream(result.spliterator(), false)
////                            .collect(Collectors.toList());
////    }
//	
//	/*Term Query: Truy vấn tìm kiếm dựa trên khớp chính xác của một từ khóa cụ thể. 
//	 * Nó thường được sử dụng cho các trường không phải là văn bản, chẳng hạn như trường keyword.*/
////	public List<MyDocument> searchByCategory(String category) {
////        TermQueryBuilder query = QueryBuilders.termQuery("category", category);
////        Iterable<MyDocument> result = documentRepository.search(query);
////        return StreamSupport.stream(result.spliterator(), false)
////                            .collect(Collectors.toList());
////    }
//	
//	/*Bool Query: Truy vấn logic AND, OR và NOT để kết hợp nhiều điều kiện tìm kiếm. 
//	 * Bạn có thể sử dụng must, should, và must_not để xác định các điều kiện.*/
////	public List<MyDocument> searchWithBoolQuery(String searchTerm1, String searchTerm2) {
////        BoolQueryBuilder query = QueryBuilders.boolQuery()
////            .should(QueryBuilders.matchQuery("content", searchTerm1))
////            .should(QueryBuilders.matchQuery("title", searchTerm2));
////
////        Iterable<MyDocument> result = documentRepository.search(query);
////        return StreamSupport.stream(result.spliterator(), false)
////                            .collect(Collectors.toList());
////    }
//	
//	/*Range Query: Truy vấn để tìm kiếm các giá trị trong khoảng cụ thể của một trường. 
//	 * Ví dụ, bạn có thể tìm kiếm các tài liệu có trường "age" nằm trong khoảng từ 18 đến 30.*/
////	public List<MyDocument> searchByAgeRange(int minAge, int maxAge) {
////        RangeQueryBuilder query = QueryBuilders.rangeQuery("age")
////            .gte(minAge) // Greater than or equal to minAge
////            .lte(maxAge); // Less than or equal to maxAge
////
////        Iterable<MyDocument> result = documentRepository.search(query);
////        return StreamSupport.stream(result.spliterator(), false)
////                            .collect(Collectors.toList());
////    }
//	
//	/*Wildcard Query: Truy vấn tìm kiếm sử dụng ký tự đại diện để tìm kiếm các từ hoặc cụm từ khớp với một mẫu cụ thể. 
//	 * Ví dụ, bạn có thể sử dụng * để tìm kiếm các từ bắt đầu bằng "elast".*/
////	public List<MyDocument> searchByWildcard(String wildcardPattern) {
////        WildcardQueryBuilder query = QueryBuilders.wildcardQuery("content", wildcardPattern);
////
////        Iterable<MyDocument> result = documentRepository.search(query);
////        return StreamSupport.stream(result.spliterator(), false)
////                            .collect(Collectors.toList());
////    }
//	
//	/*Prefix Query: Tìm kiếm các từ hoặc cụm từ bắt đầu bằng một tiền tố cụ thể. 
//	 * Ví dụ, prefix query có thể được sử dụng để tìm kiếm các từ bắt đầu bằng "elast".*/
////	public List<MyDocument> searchByPrefix(String prefix) {
////        PrefixQueryBuilder query = QueryBuilders.prefixQuery("title", prefix);
////
////        Iterable<MyDocument> result = documentRepository.search(query);
////        return StreamSupport.stream(result.spliterator(), false)
////                            .collect(Collectors.toList());
////    }
//	
//	/*Fuzzy Query: Truy vấn tìm kiếm với khả năng diễn giải, 
//	 * cho phép tìm kiếm các từ hoặc cụm từ có mức độ tương tự cao với từ khóa được cung cấp.*/
////	public List<MyDocument> searchByFuzzy(String searchTerm) {
////        FuzzyQueryBuilder query = QueryBuilders.fuzzyQuery("content", searchTerm)
////            .fuzziness(Fuzziness.AUTO) // You can adjust fuzziness level here
////
////        Iterable<MyDocument> result = documentRepository.search(query);
////        return StreamSupport.stream(result.spliterator(), false)
////                            .collect(Collectors.toList());
////    }
//	
//	/*Match Phrase Query: Tìm kiếm chính xác một cụm từ cố định. 
//	 * Nó tìm kiếm các tài liệu chứa cụm từ cụ thể mà thứ tự từng từ trong cụm từ cũng cần phải trùng khớp.*/
////	public List<MyDocument> searchByPhrase(String phrase) {
////        MatchPhraseQueryBuilder query = QueryBuilders.matchPhraseQuery("content", phrase);
////
////        Iterable<MyDocument> result = documentRepository.search(query);
////        return StreamSupport.stream(result.spliterator(), false)
////                            .collect(Collectors.toList());
////    }
//	
//	/*Nested Query: Được sử dụng khi bạn có trường dạng nested trong tài liệu và muốn tìm kiếm trong các trường con của trường nested đó.
//	More Like This Query: Truy vấn tìm kiếm các tài liệu tương tự với một tài liệu đã biết.*/
//}
