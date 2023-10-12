package com.phmth.laptopshop.security.encrypt_decrypt.symmetric;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class AlgorithmSymmetric {
	static {
		Security.addProvider(new BouncyCastleProvider());
	}

	public AlgorithmSymmetric() {

	}

	// Key----------------------------------------------------------------------------------------------------------------------
	// tạo key
	public static SecretKey GenerateKey(int n, String algorithm) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
		keyGenerator.init(n);
		SecretKey key = keyGenerator.generateKey();
		return key;
	}

	// tạo file
	private static File CreateFile(File file) throws IOException {
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		return file;
	}

	// tạo file key
	public void KeyFile(String directory, int n, String algorithm) throws Exception {
		File file = CreateFile(new File(directory + "/key_" + algorithm + "_" + n + "_" + "bit" + ".txt"));
		if (file.isFile()) {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			SecretKey key = GenerateKey(n, algorithm);
			String k = ConvertSecretKeyToString(key);
			bos.write(k.getBytes());
			bos.close();
		}
	}

	// Chuyển đổi SecretKey thành String
	public static String ConvertSecretKeyToString(SecretKey secretKey) throws Exception {
		byte[] rawData = secretKey.getEncoded();
		String encodedKey = Base64.getEncoder().encodeToString(rawData);
		return encodedKey;
	}

	// Chuyển đổi String thành SecretKey
	public static SecretKey ConvertStringToSecretKey(String key, String algorithm) throws Exception {
		byte[] decodedKey = Base64.getDecoder().decode(key);
		SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
		return originalKey;
	}

	// đọc file key
	public static SecretKey readKey(String key, String algorithm) throws Exception {
		FileInputStream fisk = new FileInputStream(new File(key));
		BufferedReader br = new BufferedReader(new InputStreamReader(fisk));
		String line = br.readLine();
		SecretKey k = ConvertStringToSecretKey(line, algorithm);
		br.close();
		return k;
	}

	// Text--------------------------------------------------------------------------------------------------------------------------
	// mã hóa text
	public String Encrypt(String text, SecretKey key, String algorithm, String mode, String padding) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] byteEncrypted = cipher.doFinal(text.getBytes());
		return Base64.getEncoder().encodeToString(byteEncrypted);
	}

	public static String EncryptCBC(String text, SecretKey key, IvParameterSpec iv, String algorithm, String mode,
			String padding) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		byte[] byteEncrypted = cipher.doFinal(text.getBytes());
		return Base64.getEncoder().encodeToString(byteEncrypted);
	}

	// giải mã text
	public String Decrypt(String text, SecretKey key, String algorithm, String mode, String padding) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(text));
		return new String(byteDecrypted);
	}

	public static String DecryptCBC(String text, SecretKey key, IvParameterSpec iv, String algorithm, String mode,
			String padding) throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);
		byte[] byteDecrypted = cipher.doFinal(Base64.getDecoder().decode(text));
		return new String(byteDecrypted);
	}

	// File-----------------------------------------------------------------------------------------------------------------------------
	// mã hóa file
	public void EncryptFile(String fileInput, String fileKey, String algorithm, String mode, String padding)
			throws Exception {
		SecretKey key = readKey(fileKey, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.ENCRYPT_MODE, key);

		// đọc file input và lưu file output
		FileInputStream fis = new FileInputStream(new File(fileInput));
		BufferedInputStream bis = new BufferedInputStream(fis);
		String res = fileInput.replace(".", "_encrypt.");
		FileOutputStream fos = new FileOutputStream(new File(res));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] input = new byte[64];
		int readByte;
		while ((readByte = bis.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, readByte);
			if (output != null) {
				bos.write(output);
			}
		}

		byte[] output = cipher.doFinal();
		if (output != null) {
			bos.write(output);
		}

		// bos.flush();
		bos.close();
		bis.close();

	}

	public void EncryptFileCBC(String fileInput, String fileKey, IvParameterSpec iv, String algorithm, String mode,
			String padding) throws Exception {
		SecretKey key = readKey(fileKey, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.ENCRYPT_MODE, key, iv);

		// đọc file input và lưu file output
		File file = new File(fileInput);
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		String res = fileInput.replace(".", "_encrypt.");
		FileOutputStream fos = new FileOutputStream(new File(res));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] input = new byte[64];
		int readByte;
		while ((readByte = bis.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, readByte);
			if (output != null) {
				bos.write(output);
			}
		}
		byte[] output = cipher.doFinal();
		if (output != null) {
			bos.write(output);
		}
		// bos.flush();
		bos.close();
		bis.close();
	}

	// giải mã file
	public void DecryptFile(String fileInput, String fileKey, String algorithm, String mode, String padding)
			throws Exception {
		SecretKey key = readKey(fileKey, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.DECRYPT_MODE, key);

		// đọc file input và lưu file output
		File file = new File(fileInput);
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		String res = fileInput.replace(".", "_decrypt.");
		FileOutputStream fos = new FileOutputStream(new File(res));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] input = new byte[64];
		int readByte = 0;

		while ((readByte = bis.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, readByte);
			if (output != null) {
				bos.write(output);
			}
		}
		byte[] output = cipher.doFinal();
		if (output != null) {
			bos.write(output);
		}
		// bos.flush();
		bos.close();
		bis.close();
	}

	public void DecryptFileCBC(String fileInput, String fileKey, IvParameterSpec iv, String algorithm, String mode,
			String padding) throws Exception {
		SecretKey key = readKey(fileKey, algorithm);
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.DECRYPT_MODE, key, iv);

		// đọc file input và lưu file output
		File file = new File(fileInput);
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream bis = new BufferedInputStream(fis);
		String res = fileInput.replace(".", "_decrypt.");
		FileOutputStream fos = new FileOutputStream(new File(res));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] input = new byte[64];
		int readByte = 0;

		while ((readByte = bis.read(input)) != -1) {
			byte[] output = cipher.update(input, 0, readByte);
			if (output != null) {
				bos.write(output);
			}
		}
		byte[] output = cipher.doFinal();
		if (output != null) {
			bos.write(output);
		}
		// bos.flush();
		bos.close();
		bis.close();
	}
}
