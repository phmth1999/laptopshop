package com.phmth.laptopshop.security.hash;

import java.security.NoSuchAlgorithmException;

public interface IHashing {
	byte[] generatedSalt() throws NoSuchAlgorithmException;
	
	String hashText(String text, byte[] salt);
	
	boolean validateText(byte[] salt, String originalText, String hashText);

	
}
