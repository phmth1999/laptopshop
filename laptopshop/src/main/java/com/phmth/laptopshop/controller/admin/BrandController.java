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

import com.phmth.laptopshop.dto.FormAddBrand;
import com.phmth.laptopshop.dto.FormEditBrand;
import com.phmth.laptopshop.dto.ResponseMessage;
import com.phmth.laptopshop.entity.BrandEntity;
import com.phmth.laptopshop.exception.BrandException;
import com.phmth.laptopshop.service.IBrandService;

@RestController("adminBrand")
@RequestMapping("/admin/brand")
public class BrandController {

	private static final Logger logger = LoggerFactory.getLogger(BrandController.class);

	@Autowired
	private IBrandService brandService;

	@GetMapping
	public ModelAndView brandPage(@RequestParam(name = "page", defaultValue = "1") int page) {

		ModelAndView mav = new ModelAndView("admin/brand/index");

		int limit = 3;
		Page<BrandEntity> listPageBrand = brandService.findAll(page, limit);
		List<BrandEntity> listBrand = listPageBrand.getContent();

		mav.addObject("brand", listBrand);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageBrand.getTotalPages());

		return mav;
	}

	@PostMapping
	public ModelAndView brand(@RequestParam("name") String name,
			@RequestParam(name = "page", defaultValue = "1") int page) {

		ModelAndView mav = new ModelAndView("admin/brand/index");
		int limit = 3;
		Page<BrandEntity> listPageBrand = brandService.findAll(page, limit, name);
		List<BrandEntity> listBrand = listPageBrand.getContent();

		mav.addObject("name", name);

		mav.addObject("brand", listBrand);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageBrand.getTotalPages());

		return mav;
	}

	@PostMapping("/add")
	public ModelAndView addBrand(@RequestParam("name") String name) {

		ModelAndView mav = new ModelAndView("redirect:/admin/brand");

		try {
			FormAddBrand formAddBrand = new FormAddBrand(name);
			if(brandService.insert(formAddBrand) != null) {
				mav = new ModelAndView("redirect:/admin/brand?add=success");
			}else {
				mav = new ModelAndView("redirect:/admin/brand?add=fail");
			}
		} catch (BrandException e) {
			mav = new ModelAndView("redirect:/admin/brand?add=exist");
			logger.error(e.getMessage());
		}

		return mav;
	}

	@PostMapping("/edit")
	public ResponseMessage handleUpdate(@RequestParam("id") long id, @RequestParam("name") String name) {
		String message = "";
		BrandEntity data = null;
		try {
			FormEditBrand formEditBrand = new FormEditBrand(id, name);
			boolean brandUpdate = brandService.update(formEditBrand);

			if (brandUpdate) {
				message = "Update brand successfully!";
				data = brandService.findOne(id).get();
			}else {
				message = "Update brand failed!";
			}
		} catch (BrandException e) {
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
			data = brandService.remove(id);
			if(data) {
				message = "Remove brand successfully!";
			}else {
				message = "Remove brand failed!";
			}
		} catch (BrandException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}
		return new ResponseMessage(message, data);
	}

}
