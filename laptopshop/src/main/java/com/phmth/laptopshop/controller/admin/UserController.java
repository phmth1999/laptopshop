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

import com.phmth.laptopshop.dto.FormAddUser;
import com.phmth.laptopshop.dto.FormEditUser;
import com.phmth.laptopshop.dto.FormSearchUser;
import com.phmth.laptopshop.dto.ResponseMessage;
import com.phmth.laptopshop.entity.UserEntity;
import com.phmth.laptopshop.exception.BrandException;
import com.phmth.laptopshop.exception.UserException;
import com.phmth.laptopshop.service.IUserService;
import com.phmth.laptopshop.utils.UploadFileUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController("adminUser")
@RequestMapping("/admin/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	@Value("${upload.path.image.user}")
	private String pathUploadImageUser;
	
	@GetMapping
	public ModelAndView showUserPage(@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("/admin/user/index");
		
		int limit = 3;
		Page<UserEntity> listPageUser = userService.findAll(page, limit);
		List<UserEntity> listUser = listPageUser.getContent();
		
		mav.addObject("formSearchUser", new FormSearchUser());
		mav.addObject("formAddUser", new FormAddUser());
		mav.addObject("formEditUser", new FormEditUser());
		
		mav.addObject("user", listUser);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageUser.getTotalPages());
		
		return mav;
	}
	@PostMapping
	public ModelAndView processUserPage(
					@ModelAttribute("formSearchUser") FormSearchUser formSearchUser,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		
		ModelAndView mav = new ModelAndView("/admin/user/index");
		logger.error(formSearchUser.getRole()+": role");
		int limit = 3;
		Page<UserEntity> listPageUser = userService.findAll(page, limit, formSearchUser);
		List<UserEntity> listUser = listPageUser.getContent();
		
		mav.addObject("formSearchUser", formSearchUser);
		mav.addObject("formAddUser", new FormAddUser());
		mav.addObject("formEditUser", new FormEditUser());
		
		mav.addObject("user", listUser);
		mav.addObject("currentPage", page);
		mav.addObject("totalPage", listPageUser.getTotalPages());
		
		return mav;
	}
	
	@PostMapping("/add")
	public ModelAndView addUser(@ModelAttribute("formSignupAdmin") FormAddUser formAddUser) {
		
		ModelAndView mav = new ModelAndView("redirect:/admin/user");
		
		try {
			if(userService.insert(formAddUser) != null) {
				mav = new ModelAndView("redirect:/admin/user?add=success");
			}else {
				mav = new ModelAndView("redirect:/admin/user?add=fail");
			}
		} catch (BrandException e) {
			mav = new ModelAndView("redirect:/admin/user?add=exist");
			logger.error(e.getMessage());
		}
		
		return mav;
	}
	
	@GetMapping("/edit/{id}")
	public UserEntity editProduct1(@PathVariable("id") long id) {
		
		return userService.findById(id).get();
	}
	
	@PostMapping("/edit")
	public ResponseMessage editProduct(
					@RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
					@ModelAttribute("formEditUser") FormEditUser formEditUser,
					HttpServletRequest request) throws IOException {
		
		String nameImage = "";
		String message = "";
		UserEntity data = null;
		try {
			if(fileImage != null && !fileImage.isEmpty()) {
				nameImage = StringUtils.cleanPath(fileImage.getOriginalFilename());
				formEditUser.setImg(nameImage);
				String realPath = request.getServletContext().getRealPath(pathUploadImageUser);
				UploadFileUtil.saveFile(realPath, nameImage, fileImage);
			}
			boolean userUpdate = userService.update(formEditUser);

			if (userUpdate) {
				message = "Update user successfully!";
				data = userService.findById(formEditUser.getId()).get();
			}else {
				message = "Update user failed!";
			}
		} catch (UserException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}

		return new ResponseMessage(message, data);
	}
	
	@PostMapping("/clock")
	public ResponseMessage clockUser(@RequestParam("id") long id) {
		String message = "";
		UserEntity data = null;
		try {
			boolean clockUser = userService.clockUser(id);

			if (clockUser) {
				message = "Clock user successfully!";
				data = userService.findById(id).get();
			}else {
				message = "Clock user failed!";
			}
		} catch (UserException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}
		return new ResponseMessage(message, data);
	}
	
	@PostMapping("/unclock")
	public ResponseMessage unClockUser(@RequestParam("id") long id) {
		
		String message = "";
		UserEntity data = null;
		try {
			boolean unClockUser = userService.unClockUser(id);

			if (unClockUser) {
				message = "UnClock user successfully!";
				data = userService.findById(id).get();
			}else {
				message = "UnClock user failed!";
			}
		} catch (UserException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		}
		return new ResponseMessage(message, data);
	}
	
}
