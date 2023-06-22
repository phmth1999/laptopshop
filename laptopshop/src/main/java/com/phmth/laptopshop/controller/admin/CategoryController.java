package com.phmth.laptopshop.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.FormAddCategory;
import com.phmth.laptopshop.dto.FormEditCategory;
import com.phmth.laptopshop.dto.ResponseMessage;
import com.phmth.laptopshop.entity.CategoryEntity;
import com.phmth.laptopshop.exception.BrandException;
import com.phmth.laptopshop.exception.CategoryException;
import com.phmth.laptopshop.service.ICategoryService;


@RestController("adminCategory")
@RequestMapping("/admin/category")
public class CategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping
	public ModelAndView categoryPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("admin/category/index"); 
		int limit = 3;
		Page<CategoryEntity> listPageCategory = categoryService.findAll(page, limit);
		List<CategoryEntity> listCategory = listPageCategory.getContent();
		
		mav.addObject("category", listCategory);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageCategory.getTotalPages());
		
		return mav;
	}
	@PostMapping
	public ModelAndView category(
					@RequestParam("name") String name,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("admin/category/index"); 
		int limit = 3;
		Page<CategoryEntity> listPageCategory = categoryService.findAll(page, limit, name);
		List<CategoryEntity> listCategory = listPageCategory.getContent();
		logger.error(name);
		mav.addObject("name", name);
		
		mav.addObject("category", listCategory);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageCategory.getTotalPages());
		
		return mav;
	}
	
	@PostMapping("/add")
	public ModelAndView addCategory(@RequestParam("name") String name) {
		
		ModelAndView mav = new ModelAndView("redirect:/admin/category"); 
		
		try {
			FormAddCategory formAddCategory = new FormAddCategory(name);
			if(categoryService.insert(formAddCategory) != null) {
				mav = new ModelAndView("redirect:/admin/category?add=success");
			}else {
				mav = new ModelAndView("redirect:/admin/category?add=fail");
			}
		} catch (CategoryException e) {
			mav = new ModelAndView("redirect:/admin/category?add=exist");
			logger.error(e.getMessage());
		}
		
		return mav;
	}
	
	@PostMapping("/edit")
	public ResponseMessage editCategory(
						@RequestParam("id") long id, 
						@RequestParam("name") String name) {
		
		String message = "";
		CategoryEntity data = null;
		
		try {
			FormEditCategory formEditCategory = new FormEditCategory(id, name);
			boolean categoryUpdate = categoryService.update(formEditCategory);

			if (categoryUpdate) {
				message = "Update category successfully!";
				data = categoryService.findOne(id).get();
			}else {
				message = "Update category failed!";
			}
		} catch (Exception e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}
		
		return new ResponseMessage(message, data);
	}
	
	@PostMapping("/delete")
	public ResponseMessage deleteCategory(@RequestParam("id") long id) {
		String message = "";
		boolean data = false;
		try {
			data = categoryService.remove(id);
			if(data) {
				message = "Remove category successfully!";
			}else {
				message = "Remove category failed!";
			}
		} catch (BrandException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}
		return new ResponseMessage(message, data);
	}
}
