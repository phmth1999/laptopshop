package com.phmth.laptopshop.controller.admin;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.dto.reponse.MessageResponse;
import com.phmth.laptopshop.dto.request.SearchProductRequest;
import com.phmth.laptopshop.exception.ProductException;
import com.phmth.laptopshop.service.IBrandService;
import com.phmth.laptopshop.service.ICategoryService;
import com.phmth.laptopshop.service.IProductService;
import com.phmth.laptopshop.service.impl.ProductElasticsearchService;
import com.phmth.laptopshop.utils.UploadFileUtil;

import jakarta.servlet.http.HttpServletRequest;


@RestController("adminProduct")
@RequestMapping("/admin/product")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@Autowired
	private IBrandService brandService;
	
	@Value("${upload.path.image.product}")
	private String pathUploadImageProduct;
	
	@Autowired
	private ProductElasticsearchService productElasticsearchService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping
	public ModelAndView showProductPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 5;
		
		ModelAndView mav = new ModelAndView("admin/product/index"); 
		mav.addObject("formSearchProduct", new SearchProductRequest());
		mav.addObject("productDto", new ProductDto());
		
		try {
			mav.addObject("category", categoryService.findAll());
			mav.addObject("brand", brandService.findAll());
			
			Page<ProductDto> listPageProduct = productService.findAll(page, limit);
			if(listPageProduct != null) {
				List<ProductDto> listProduct = listPageProduct.getContent();
				mav.addObject("product", listProduct);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageProduct.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	@PostMapping
	public ModelAndView processProductPage(
					@ModelAttribute("formSearchProduct") SearchProductRequest formSearchProduct,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 5;
		
		ModelAndView mav = new ModelAndView("admin/product/index"); 
		mav.addObject("formSearchProduct", formSearchProduct);
		mav.addObject("productDto", new ProductDto());
		
		try {
			mav.addObject("category", categoryService.findAll());
			mav.addObject("brand", brandService.findAll());
			
			Page<ProductDto> listPageProduct = productService.findAll(page, limit, formSearchProduct);
			if(listPageProduct != null) {
				List<ProductDto> listProduct = listPageProduct.getContent();
				mav.addObject("product", listProduct);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageProduct.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	
	@PostMapping("/add")
	public ModelAndView handleCreate(
					@RequestParam(value = "fileImage") MultipartFile fileImage,
					@ModelAttribute("product") ProductDto productDto,
					HttpServletRequest request) throws IOException {
		
		ModelAndView mav = new ModelAndView("redirect:/admin/product"); 
		String nameImage = "";
		
		try {
			if(fileImage != null && !fileImage.isEmpty()) {
				nameImage = StringUtils.cleanPath(fileImage.getOriginalFilename());
				productDto.setThumbnail(nameImage);
				String realPath = request.getServletContext().getRealPath(pathUploadImageProduct);
				UploadFileUtil.saveFile(realPath, nameImage, fileImage);
			}
			
			ProductDto productReponse = productService.insert(productDto);
			
			if(productReponse != null) {
				productElasticsearchService.dataSynchronization(productReponse);
				mav = new ModelAndView("redirect:/admin/product?add=success");
			}else {
				mav = new ModelAndView("redirect:/admin/product?add=fail");
			}
		} catch (ProductException e) {
			mav = new ModelAndView("redirect:/admin/product?add=exist");
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	
	@GetMapping("/edit/{id}")
	public ProductDto productApi(@PathVariable("id") long id) {
		return productService.findById(id).get();
	}
	
	@PostMapping("/edit")
	public MessageResponse handleUpdate(
					@RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
					@ModelAttribute("product") ProductDto productDto,
					HttpServletRequest request) throws IOException {
		
		String nameImage = "";
		String message = "";
		ProductDto data = null;
		
		try {
			if(fileImage != null && !fileImage.isEmpty()) {
				nameImage = StringUtils.cleanPath(fileImage.getOriginalFilename());
				productDto.setThumbnail(nameImage);
				String realPath = request.getServletContext().getRealPath(pathUploadImageProduct);
				UploadFileUtil.saveFile(realPath, nameImage, fileImage);
			}
			
			boolean productReponse = productService.update(productDto);

			if (productReponse) {
				productElasticsearchService.dataSynchronization(productService.findById(productDto.getId()).get());
				message = "Update product successfully!";
				data = productService.findById(productDto.getId()).get();
			}else {
				message = "Update product failed!";
			}
		} catch (ProductException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return new MessageResponse(message, data);
	}
	
	@PostMapping("/delete")
	public MessageResponse handleRemove(@RequestParam("id") long id) {
		
		String message = "";
		boolean data = false;
		
		try {
			data = productService.remove(id);
			
			if(data) {
				message = "Remove product successfully!";
			}else {
				message = "Remove product failed!";
			}
		} catch (ProductException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return new MessageResponse(message, data);
	}
}
