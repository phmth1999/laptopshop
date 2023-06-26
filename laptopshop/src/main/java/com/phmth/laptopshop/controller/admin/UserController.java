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

import com.phmth.laptopshop.dto.UserDto;
import com.phmth.laptopshop.dto.reponse.MessageResponse;
import com.phmth.laptopshop.dto.request.AddUserRequest;
import com.phmth.laptopshop.dto.request.EditUserRequest;
import com.phmth.laptopshop.dto.request.SearchUserRequest;
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
		
		int limit = 3;
		
		ModelAndView mav = new ModelAndView("/admin/user/index");
		mav.addObject("formSearchUser", new SearchUserRequest());
		mav.addObject("formAddUser", new AddUserRequest());
		mav.addObject("formEditUser", new EditUserRequest());
		
		try {
			Page<UserDto> listPageUser = userService.findAll(page, limit);
			if(listPageUser != null) {
				List<UserDto> listUser = listPageUser.getContent();
				mav.addObject("user", listUser);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageUser.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	@PostMapping
	public ModelAndView processUserPage(
					@ModelAttribute("formSearchUser") SearchUserRequest formSearchUser,
					@RequestParam(name = "page", defaultValue = "1") int page) {
		
		int limit = 3;
		
		ModelAndView mav = new ModelAndView("/admin/user/index");
		mav.addObject("formSearchUser", formSearchUser);
		mav.addObject("formAddUser", new AddUserRequest());
		mav.addObject("formEditUser", new EditUserRequest());
		
		try {
			Page<UserDto> listPageUser = userService.findAll(page, limit, formSearchUser);
			if(listPageUser != null) {
				List<UserDto> listUser = listPageUser.getContent();
				mav.addObject("user", listUser);
				mav.addObject("currentPage", page);
				mav.addObject("totalPage", listPageUser.getTotalPages());
			}
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	
	@PostMapping("/add")
	public ModelAndView handleCreate(@ModelAttribute("formSignupAdmin") AddUserRequest formAddUser) {
		
		ModelAndView mav = new ModelAndView("redirect:/admin/user");
		
		try {
			UserDto userReponse = userService.insert(formAddUser);
			
			if(userReponse != null) {
				mav = new ModelAndView("redirect:/admin/user?add=success");
			}else {
				mav = new ModelAndView("redirect:/admin/user?add=fail");
			}
		} catch (UserException e) {
			mav = new ModelAndView("redirect:/admin/user?add=exist");
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return mav;
	}
	
	@GetMapping("/edit/{id}")
	public UserDto userApi(@PathVariable("id") long id) {
		return userService.findById(id).get();
	}
	
	@PostMapping("/edit")
	public MessageResponse handleUpdate(
					@RequestParam(value = "fileImage", required = false) MultipartFile fileImage,
					@ModelAttribute("formEditUser") EditUserRequest formEditUser,
					HttpServletRequest request) throws IOException {
		
		String nameImage = "";
		String message = "";
		UserDto data = null;
		
		try {
			if(fileImage != null && !fileImage.isEmpty()) {
				nameImage = StringUtils.cleanPath(fileImage.getOriginalFilename());
				formEditUser.setImg(nameImage);
				String realPath = request.getServletContext().getRealPath(pathUploadImageUser);
				UploadFileUtil.saveFile(realPath, nameImage, fileImage);
			}
			
			boolean userReponse = userService.update(formEditUser);

			if (userReponse) {
				message = "Update user successfully!";
				data = userService.findById(formEditUser.getId()).get();
			}else {
				message = "Update user failed!";
			}
		} catch (UserException e) {
			message = e.getMessage();
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}

		return new MessageResponse(message, data);
	}
	
	@PostMapping("/clock")
	public MessageResponse handleClockUser(@RequestParam("id") long id) {
		String message = "";
		UserDto data = null;
		
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
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return new MessageResponse(message, data);
	}
	
	@PostMapping("/unclock")
	public MessageResponse handleUnClockUser(@RequestParam("id") long id) {
		
		String message = "";
		UserDto data = null;
		
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
		} catch (Exception e) {
			logger.error("Message error: {} --> ", e);
		}
		
		return new MessageResponse(message, data);
	}
	
}
