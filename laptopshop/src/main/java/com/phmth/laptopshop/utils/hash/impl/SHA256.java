package com.phmth.laptopshop.utils.hash.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.phmth.laptopshop.utils.hash.IHashing;

@Component
@Qualifier("sha256")
/*
 * Stronger than SHA-1 – 256 bits Hash
 **/
public class SHA256 implements IHashing {

	@Override
	public byte[] generatedSalt() throws NoSuchAlgorithmException {
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}


	@Override
	public String hashText(String text, byte[] salt) {
		String generatedtext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes());
			md.update(salt);

			byte[] byteData = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedtext = sb.toString();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return generatedtext;
	}

	@Override
	public boolean validateText(byte[] salt, String originalText, String hashText) {
		String hashOriginalText = hashText(originalText, salt);
		return hashOriginalText.equals(hashText);
	}


}
