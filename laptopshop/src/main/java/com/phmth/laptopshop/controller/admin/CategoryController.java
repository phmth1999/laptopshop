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

import com.phmth.laptopshop.dto.CategoryDto;
import com.phmth.laptopshop.dto.reponse.MessageResponse;
import com.phmth.laptopshop.exception.CategoryException;
import com.phmth.laptopshop.service.ICategoryService;


@RestController("adminCategory")
@RequestMapping("/admin/category")
public class CategoryController {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping
	public ModelAndView showCategoryPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 3;
		
		ModelAndView mav = new ModelAndView("admin/category/index"); 
		
		try {
			Page<CategoryDto> listPageCategory = categoryService.findAll(page, limit);
			if(listPageCategory != null) {
				List<CategoryDto> listCategory = listPageCategory.getContent();
				mav.addObject("category", listCategory);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageCategory.getTotalPages());
			}

		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	@PostMapping
	public ModelAndView processCategoryPage(
					@RequestParam("name") String name,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 3;
		
		ModelAndView mav = new ModelAndView("admin/category/index"); 
		mav.addObject("name", name);
		
		try {
			Page<CategoryDto> listPageCategory = categoryService.findAll(page, limit, name);
			if(listPageCategory != null) {
				List<CategoryDto> listCategory = listPageCategory.getContent();
				mav.addObject("category", listCategory);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageCategory.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	
	@PostMapping("/add")
	public ModelAndView handleCreate(@RequestParam("name") String name) {
		
		ModelAndView mav = new ModelAndView("redirect:/admin/category"); 
		
		try {
			CategoryDto categoryDto = new CategoryDto();
			categoryDto.setName(name);
			
			CategoryDto categoryReponse = categoryService.insert(categoryDto);
			
			if(categoryReponse != null) {
				mav = new ModelAndView("redirect:/admin/category?add=success");
			}else {
				mav = new ModelAndView("redirect:/admin/category?add=fail");
			}
		} catch (CategoryException e) {
			mav = new ModelAndView("redirect:/admin/category?add=exist");
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	
	@PostMapping("/edit")
	public MessageResponse handleUpdate(
						@RequestParam("id") long id, 
						@RequestParam("name") String name) {
		
		String message = "";
		CategoryDto data = null;
		
		try {
			CategoryDto categoryDto = new CategoryDto();
			categoryDto.setId(id);
			categoryDto.setName(name);
			
			boolean categoryReponse = categoryService.update(categoryDto);

			if (categoryReponse) {
				message = "Update category successfully!";
				data = categoryService.findById(id).get();
			}else {
				message = "Update category failed!";
			}
		} catch (CategoryException e) {
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
			data = categoryService.remove(id);
			
			if(data) {
				message = "Remove category successfully!";
			}else {
				message = "Remove category failed!";
			}
		} catch (CategoryException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return new MessageResponse(message, data);
	}
}
