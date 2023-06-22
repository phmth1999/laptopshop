package com.phmth.laptopshop.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class UploadFileUtil {
	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);

	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path path = Paths.get(uploadDir);
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = path.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			logger.error("Count not save image file" + fileName);
			throw new IOException("Count not save image file: " + fileName);
		}
	}
}
