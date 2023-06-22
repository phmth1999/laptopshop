package com.phmth.laptopshop.utils.hash.impl;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.phmth.laptopshop.utils.hash.IHashing;

@Component
@Qualifier("pbkdf2")
public class PBKDF2 implements IHashing {

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
			KeySpec spec = new PBEKeySpec(text.toCharArray(), salt, 65536, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

			byte[] byteData = factory.generateSecret(spec).getEncoded();

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
