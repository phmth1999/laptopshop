package com.phmth.laptopshop.utils.hash;

import java.security.NoSuchAlgorithmException;

public interface IHashing {
	byte[] generatedSalt() throws NoSuchAlgorithmException;
	
	String hashText(String text, byte[] salt);
	
	boolean validateText(byte[] salt, String originalText, String hashText);

	
}
