package com.phmth.laptopshop.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class ImageUploadController {
	
	@Value("${upload.path.image.product}")
	private String pathUploadImageProduct;
	
	@Value("${upload.path.image.user}")
	private String pathUploadImageUser;
	
	@Value("${upload.path.image.slide}")
	private String pathUploadImageSlide;
	
	@Value("${upload.path.image.banner}")
	private String pathUploadImageBanner;
	
	@GetMapping("/images/{type}/{photo}")
	public ResponseEntity<ByteArrayResource> getImage(
							@PathVariable("photo") String photo, 
							@PathVariable("type") String type, 
							HttpServletRequest request) {
		if (!photo.equals("") || photo != null) {
			try {
				String realPath = "";
				if(type.equals("product")) {
					realPath = request.getServletContext().getRealPath(pathUploadImageProduct);
				}else if(type.equals("user")) {
					realPath = request.getServletContext().getRealPath(pathUploadImageUser);
				}else if(type.equals("slide")) {
					realPath = request.getServletContext().getRealPath(pathUploadImageSlide);
				}else if(type.equals("banner")) {
					realPath = request.getServletContext().getRealPath(pathUploadImageBanner);
				}
				
				Path path = Paths.get(realPath, photo);
				byte[] buffer = Files.readAllBytes(path);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentLength(buffer.length)
						.contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);
			} catch (Exception e) {
			}
		}
		return ResponseEntity.badRequest().build();
	}

}
