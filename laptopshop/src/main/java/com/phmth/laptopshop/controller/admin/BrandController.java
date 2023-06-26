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

import com.phmth.laptopshop.dto.BrandDto;
import com.phmth.laptopshop.dto.reponse.MessageResponse;
import com.phmth.laptopshop.exception.BrandException;
import com.phmth.laptopshop.service.IBrandService;

@RestController("adminBrand")
@RequestMapping("/admin/brand")
public class BrandController {

	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

	@Autowired
	private IBrandService brandService;

	@GetMapping
	public ModelAndView showBrandPage(@RequestParam(name = "page", defaultValue = "1") int page) {

		int limit = 3;
		
		ModelAndView mav = new ModelAndView("admin/brand/index");
		
		try {
			Page<BrandDto> listPageBrand = brandService.findAll(page, limit);
			if(listPageBrand != null) {
				List<BrandDto> listBrand = listPageBrand.getContent();
				mav.addObject("brand", listBrand);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageBrand.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}

	@PostMapping
	public ModelAndView processBrand(
				@RequestParam("name") String name,
				@RequestParam(name = "page", defaultValue = "1") int page) {

		int limit = 3;
		
		ModelAndView mav = new ModelAndView("admin/brand/index");
		mav.addObject("name", name);
		
		try {
			Page<BrandDto> listPageBrand = brandService.findAll(page, limit, name);
			if(listPageBrand != null) {
				List<BrandDto> listBrand = listPageBrand.getContent();
				mav.addObject("brand", listBrand);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageBrand.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}

		return mav;
	}

	@PostMapping("/add")
	public ModelAndView handleCreate(@RequestParam("name") String name) {

		ModelAndView mav = new ModelAndView("redirect:/admin/brand");

		try {
			BrandDto brandDto = new BrandDto();
			brandDto.setName(name);
			
			BrandDto brandReponse = brandService.insert(brandDto);
			
			if(brandReponse != null) {
				mav = new ModelAndView("redirect:/admin/brand?add=success");
			}else {
				mav = new ModelAndView("redirect:/admin/brand?add=fail");
			}
		} catch (BrandException e) {
			mav = new ModelAndView("redirect:/admin/brand?add=exist");
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
		BrandDto data = null;
		
		try {
			BrandDto brandDto = new BrandDto();
			brandDto.setId(id);
			brandDto.setName(name);
			
			boolean brandReponse = brandService.update(brandDto);

			if (brandReponse) {
				message = "Update brand successfully!";
				data = brandService.findById(id).get();
			}else {
				message = "Update brand failed!";
			}
		} catch (BrandException e) {
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
			data = brandService.remove(id);
			if(data) {
				message = "Remove brand successfully!";
			}else {
				message = "Remove brand failed!";
			}
		} catch (BrandException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return new MessageResponse(message, data);
	}

}
