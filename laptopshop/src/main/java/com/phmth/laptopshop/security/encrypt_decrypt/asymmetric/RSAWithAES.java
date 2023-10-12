package com.phmth.laptopshop.security.encrypt_decrypt.asymmetric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class RSAWithAES {
	private static void processFile(Cipher ci, InputStream in, OutputStream out) throws Exception {
		byte[] input = new byte[1024];
		int readByte;
		while ((readByte = in.read(input)) != -1) {
			byte[] output = ci.update(input, 0, readByte);
			if (output != null) {
				out.write(output);
			}
		}
		byte[] output = ci.doFinal();
		if (output != null) {
			out.write(output);
			out.close();
		}
	}

	private static PublicKey readPublicKey(String key) throws Exception {
		FileInputStream fis = new FileInputStream(new File(key));
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = br.readLine();
		byte[] b = Base64.getDecoder().decode(line);
		br.close();
		fis.close();

		X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PublicKey pubKey = factory.generatePublic(spec);
		return pubKey;
	}

	private static PrivateKey readPrivateKey(String key) throws Exception {
		FileInputStream fis = new FileInputStream(new File(key));
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String line = br.readLine();
		byte[] b = Base64.getDecoder().decode(line);
		br.close();
		fis.close();

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		PrivateKey priKey = factory.generatePrivate(spec);
		return priKey;
	}

	public void EncryptFileRSAWithAES(String fileInput, String PrivateKey, String algorithm, String mode,
			String padding) throws Exception {

		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey skey = keyGenerator.generateKey();

		byte[] iv = new byte[128 / 8];
		SecureRandom sr = new SecureRandom();
		sr.nextBytes(iv);
		IvParameterSpec ivspec = new IvParameterSpec(iv);

		String res = fileInput.replace(".", "_encrypt.");
		try (FileOutputStream out = new FileOutputStream(new File(res))) {
			{
				PrivateKey priKey = readPrivateKey(PrivateKey);
				Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
				cipher.init(Cipher.ENCRYPT_MODE, priKey);
				byte[] b = cipher.doFinal(skey.getEncoded());
				out.write(b);
			}
			out.write(iv);

			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
			try (FileInputStream in = new FileInputStream(new File(fileInput))) {
				processFile(ci, in, out);
			}
		}
	}

	public void DecryptFileRSAWithAES(String fileInput, String PublicKey, String algorithm, String mode, String padding)
			throws Exception {
		try (FileInputStream in = new FileInputStream(new File(fileInput))) {
			SecretKeySpec skey = null;
			{
				PublicKey pubKey = readPublicKey(PublicKey);
				Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
				cipher.init(Cipher.DECRYPT_MODE, pubKey);
				byte[] b = new byte[128];
				in.read(b);
				byte[] keyb = cipher.doFinal(b);
				skey = new SecretKeySpec(keyb, "AES");
			}

			byte[] iv = new byte[128 / 8];
			in.read(iv);
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			Cipher ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ci.init(Cipher.DECRYPT_MODE, skey, ivspec);
			String res = fileInput.replace(".", "_decrypt.");
			try (FileOutputStream out = new FileOutputStream(new File(res))) {
				processFile(ci, in, out);
			}
		}
	}
}
