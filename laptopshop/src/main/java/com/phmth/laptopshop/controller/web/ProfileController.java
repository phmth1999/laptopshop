package com.phmth.laptopshop.controller.web;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.phmth.laptopshop.dto.UserDto;
import com.phmth.laptopshop.dto.request.EditProfileRequest;
import com.phmth.laptopshop.service.IUserService;
import com.phmth.laptopshop.utils.IdLogged;
import com.phmth.laptopshop.utils.UploadFileUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/")
public class ProfileController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Value("${upload.path.image.user}")
	private String pathUploadImageUser;
	
	@Autowired
	private IUserService userService;
	
	@GetMapping("profile")
	public ModelAndView profilePage() {
		
		ModelAndView mav = new ModelAndView("/web/profile/index");
		
		try {
			/*
			 * 	Find the user who is logged in
			 * 	true --> convert entity to dto --> set data profile = user login
			 * */
			Optional<UserDto> userEntity = userService.findById(IdLogged.getId());
			if(userEntity != null) {
				EditProfileRequest formProfile = new EditProfileRequest();
				formProfile.setFullname(userEntity.get().getFullname());
				formProfile.setSex(userEntity.get().getSex());
				formProfile.setBirthday(userEntity.get().getBirthday());
				formProfile.setAddress(userEntity.get().getAddress());
				formProfile.setImg(userEntity.get().getImg());
				formProfile.setEmail(userEntity.get().getEmail());
				formProfile.setId(userEntity.get().getId());
				mav.addObject("profile", formProfile);
			}
		} catch (Exception e) {
			logger.error("Message: --> {} :", e);
		}
		
		return mav;
	}
	
	@PostMapping("profile")
	public ModelAndView editProfilePage(
						@RequestParam(value = "fileImage", required = false) MultipartFile fileImage, 
						@ModelAttribute("profile") @Valid EditProfileRequest formProfile,
						BindingResult result,
						HttpServletRequest request
						) throws IOException {
		String nameImage = "";
		try {
			if (result.hasErrors()) {
			    return new ModelAndView("/web/profile/index");
			  }
			/*	Check fileImage has data
			 * 	true --> reset data fields (thumbnail) = newly uploaded file (file name)
			 * */
			if(fileImage != null && !fileImage.isEmpty()) {
				nameImage = StringUtils.cleanPath(fileImage.getOriginalFilename());
				formProfile.setImg(nameImage);
				String realPath = request.getServletContext().getRealPath(pathUploadImageUser);
				UploadFileUtil.saveFile(realPath, nameImage, fileImage);
			}
			/*
			 * 	Find the user need to edit profile
			 * 	true --> convert dto to entity --> set value --> save data into database
			 * 	Check save data = true --> executed save file image
			 * */
			UserDto userNeedEdit = userService.findById(formProfile.getId()).get();
			if(userNeedEdit != null) {
				userService.update(formProfile);
			}
			
		} catch (Exception e) {
			logger.error("Message: --> {} :", e);
		}
		return new ModelAndView("redirect:/user/profile");
	}
}
