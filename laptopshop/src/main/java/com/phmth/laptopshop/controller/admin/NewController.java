package com.phmth.laptopshop.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.NewDto;
import com.phmth.laptopshop.service.ICategoryService;
import com.phmth.laptopshop.service.INewService;

@RestController("adminNew")
@RequestMapping("/admin/new")
public class NewController {
	
	private static final Logger logger = LoggerFactory.getLogger(NewController.class);

	@Autowired
	private INewService newService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping
	public ModelAndView showNewPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 3;
		
		ModelAndView mav = new ModelAndView("admin/new/index"); 
		mav.addObject("newDto", new NewDto());
		try {
			mav.addObject("category", categoryService.findAll());
			
			Page<NewDto> listPageNew = newService.findAll(page, limit);
			if(listPageNew != null) {
				List<NewDto> listNew = listPageNew.getContent();
				mav.addObject("news", listNew);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageNew.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	@PostMapping
	public ModelAndView processNewPage(
					@ModelAttribute("newDto") NewDto newDto,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 3;
		
		ModelAndView mav = new ModelAndView("admin/new/index"); 
		mav.addObject("newDto", new NewDto());
		
		try {
			mav.addObject("category", categoryService.findAll());
			
			Page<NewDto> listPageNew = newService.findAll(page, limit);
			if(listPageNew != null) {
				List<NewDto> listNew = listPageNew.getContent();
				mav.addObject("news", listNew);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageNew.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	@PostMapping("/add")
	public ModelAndView handleCreate(@ModelAttribute("newDto") NewDto newDto) {
		
		ModelAndView mav = new ModelAndView("redirect:/admin/new"); 
		
		try {
			NewDto newReponse = newService.insert(newDto);
			
			if(newReponse == null) {
				mav = new ModelAndView("redirect:/admin/new?add=fail");
			}else {
				mav = new ModelAndView("redirect:/admin/new?add=success");
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
	
		return mav;
	}
	
	@GetMapping("/edit/{id}")
	public NewDto newApi(@PathVariable("id") long id) {
		return newService.findById(id).get();
	}
	
	@PostMapping("/edit")
	public ModelAndView handleUpdate(@ModelAttribute("newDto") NewDto newDto) {
		
		ModelAndView mav = new ModelAndView("redirect:/admin/new"); 
		
		try {
			boolean newReponse = newService.update(newDto);
			
			if(!newReponse) {
				mav = new ModelAndView("redirect:/admin/new?add=fail");
			}else {
				mav = new ModelAndView("redirect:/admin/new?add=success");
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
}
