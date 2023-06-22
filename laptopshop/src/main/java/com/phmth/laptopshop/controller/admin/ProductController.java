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

import com.phmth.laptopshop.dto.FormSearchProduct;
import com.phmth.laptopshop.dto.ProductDto;
import com.phmth.laptopshop.dto.ResponseMessage;
import com.phmth.laptopshop.entity.ProductEntity;
import com.phmth.laptopshop.exception.ProductException;
import com.phmth.laptopshop.service.IBrandService;
import com.phmth.laptopshop.service.ICategoryService;
import com.phmth.laptopshop.service.IProductService;
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
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@GetMapping
	public ModelAndView productPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("admin/product/index"); 
		
		int limit = 5;
		Page<ProductEntity> listPageProduct = productService.findAll(page, limit);
		List<ProductEntity> listProduct = listPageProduct.getContent();
		
		mav.addObject("formSearchProduct", new FormSearchProduct());
		mav.addObject("productDto", new ProductDto());
		
		mav.addObject("category", categoryService.findAll());
		mav.addObject("brand", brandService.findAll());
		
		mav.addObject("product", listProduct);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageProduct.getTotalPages());
		
		return mav;
	}
	@PostMapping
	public ModelAndView product(
					@ModelAttribute("formSearchProduct") FormSearchProduct formSearchProduct,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("admin/product/index"); 
		
		int limit = 5;
		Page<ProductEntity> listPageProduct = productService.findAll(page, limit, formSearchProduct);
		List<ProductEntity> listProduct = listPageProduct.getContent();
		
		mav.addObject("formSearchProduct", formSearchProduct);
		mav.addObject("productDto", new ProductDto());
		
		mav.addObject("category", categoryService.findAll());
		mav.addObject("brand", brandService.findAll());
		
		mav.addObject("product", listProduct);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageProduct.getTotalPages());
		
		return mav;
	}
	
	@PostMapping("/add")
	public ModelAndView addProduct(
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
			
			if(productService.insert(productDto) != null) {
				mav = new ModelAndView("redirect:/admin/product?add=success");
			}else {
				mav = new ModelAndView("redirect:/admin/product?add=fail");
			}
		} catch (ProductException e) {
			mav = new ModelAndView("redirect:/admin/product?add=exist");
			logger.error(e.getMessage());
		}
		
		return mav;
	}
	
	@GetMapping("/edit/{id}")
	public ProductEntity editProduct1(@PathVariable("id") long id) {
		
		return productService.findOne(id).get();
	}
	
	@PostMapping("/edit")
	public ResponseMessage editProduct(
					@RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
					@ModelAttribute("product") ProductDto productDto,
					HttpServletRequest request) throws IOException {
		
		String nameImage = "";
		String message = "";
		ProductEntity data = null;
		try {
			if(fileImage != null && !fileImage.isEmpty()) {
				nameImage = StringUtils.cleanPath(fileImage.getOriginalFilename());
				productDto.setThumbnail(nameImage);
				String realPath = request.getServletContext().getRealPath(pathUploadImageProduct);
				UploadFileUtil.saveFile(realPath, nameImage, fileImage);
			}
			boolean productUpdate = productService.update(productDto);

			if (productUpdate) {
				message = "Update product successfully!";
				data = productService.findOne(productDto.getId()).get();
			}else {
				message = "Update product failed!";
			}
		} catch (ProductException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}
		return new ResponseMessage(message, data);
	}
	
	@PostMapping("/delete")
	public ResponseMessage deleteProduct(@RequestParam("id") long id) {
		
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
		}
		return new ResponseMessage(message, data);
	}
}
