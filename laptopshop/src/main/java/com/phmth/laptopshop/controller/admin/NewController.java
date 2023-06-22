package com.phmth.laptopshop.controller.admin;

import java.util.Date;
import java.util.List;

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
import com.phmth.laptopshop.entity.NewEntity;
import com.phmth.laptopshop.service.ICategoryService;
import com.phmth.laptopshop.service.INewService;

@RestController("adminNew")
@RequestMapping("/admin/new")
public class NewController {

	@Autowired
	private INewService newService;
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping
	public ModelAndView showListNew(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("admin/new/index"); 
		int limit = 3;
		Page<NewEntity> listPageNew = newService.findAll(page, limit);
		List<NewEntity> listNew = listPageNew.getContent();
		
		mav.addObject("newDto", new NewDto());
		
		mav.addObject("news", listNew);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageNew.getTotalPages());
		
		return mav;
	}
	@PostMapping
	public ModelAndView processListNew(
					@ModelAttribute("newDto") NewDto newDto,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("admin/new/index"); 
		int limit = 3;
		Page<NewEntity> listPageNew = newService.findAll(page, limit);
		List<NewEntity> listNew = listPageNew.getContent();
		
		mav.addObject("newDto", newDto);
		
		mav.addObject("news", listNew);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageNew.getTotalPages());
		
		return mav;
	}
	@PostMapping("/add")
	public ModelAndView processAdd(@ModelAttribute("newDto") NewDto newDto) {
		
		NewEntity newEntity = new NewEntity();
		newEntity.setCategory(categoryService.findOne(newDto.getCategory()).get());
		newEntity.setTitle(newDto.getTitle());
		newEntity.setThumbnail(newDto.getThumbnail());
		newEntity.setShortDescription(newDto.getShortDescription());
		newEntity.setContent(newDto.getContent());
		newEntity.setCreated_at(new Date());
		newService.insert(newEntity);
		ModelAndView mav = new ModelAndView("redirect:/admin/new"); 
		return mav;
	}
	@GetMapping("/edit/{id}")
	public NewEntity showNew(@PathVariable("id") long id) {
		
		return newService.findOne(id).get();
	}
	@PostMapping("/edit")
	public ModelAndView processNew(@ModelAttribute("newDto") NewDto newDto) {
		NewEntity newEntity = newService.findOne(newDto.getId()).get();
		newEntity.setCategory(categoryService.findOne(newDto.getCategory()).get());
		newEntity.setTitle(newDto.getTitle());
		newEntity.setThumbnail(newDto.getThumbnail());
		newEntity.setShortDescription(newDto.getShortDescription());
		newEntity.setContent(newDto.getContent());
		newService.insert(newEntity);
		ModelAndView mav = new ModelAndView("redirect:/admin/new"); 
		return mav;
	}
}
