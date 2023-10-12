package com.phmth.laptopshop.security.encrypt_decrypt.asymmetric;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
public class RSA {
	public RSA() {

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

	// Key-------------------------------------------------------------------------------------------------------------------
	// tạo khóa
	public void GenerateKey(String fileOutput, int n) throws Exception {
		KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(n);

		KeyPair kp = kpg.genKeyPair();
		PublicKey publicKey = kp.getPublic();
		PrivateKey privateKey = kp.getPrivate();

		File publicKeyFile = CreateKeyFile(new File(fileOutput + "/publicKey_" + n + "bit" + ".txt"));
		File privateKeyFile = CreateKeyFile(new File(fileOutput + "/privateKey_" + n + "bit" + ".txt"));

		FileOutputStream fosPublic = new FileOutputStream(publicKeyFile);
		BufferedOutputStream bosPublic = new BufferedOutputStream(fosPublic);
		byte[] rawDataPublicKey = publicKey.getEncoded();
		String encodedPublicKey = Base64.getEncoder().encodeToString(rawDataPublicKey);
		bosPublic.write(encodedPublicKey.getBytes());
		bosPublic.close();
		fosPublic.close();

		FileOutputStream fosPrivate = new FileOutputStream(privateKeyFile);
		BufferedOutputStream bosPrivate = new BufferedOutputStream(fosPrivate);
		byte[] rawDataPrivateKey = privateKey.getEncoded();
		String encodedPrivateKey = Base64.getEncoder().encodeToString(rawDataPrivateKey);
		bosPrivate.write(encodedPrivateKey.getBytes());
		bosPrivate.close();
		fosPrivate.close();
	}

	// tạo file
	private static File CreateKeyFile(File file) throws Exception {
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		return file;
	}

	// Text--------------------------------------------------------------------------------------------------------------------------
	// mã hóa text
	public String Encrypt(String text, String PublicKey, String algorithm, String mode, String padding)
			throws Exception {
		PublicKey pubKey = readPublicKey(PublicKey);
		Cipher c = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		c.init(Cipher.ENCRYPT_MODE, pubKey);
		byte encryptOut[] = c.doFinal(text.getBytes());
		return Base64.getEncoder().encodeToString(encryptOut);
	}

	// giải mã text
	public String Decrypt(String text, String PrivateKey, String algorithm, String mode, String padding)
			throws Exception {
		PrivateKey priKey = readPrivateKey(PrivateKey);
		Cipher c = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		c.init(Cipher.DECRYPT_MODE, priKey);
		byte[] byteDecrypted = c.doFinal(Base64.getDecoder().decode(text));
		return new String(byteDecrypted);
	}

	// File-------------------------------------------------------------------------------------------------------------------------
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

	// mã hóa file
	public void EncryptFile(String Input, String PublicKey, String algorithm, String mode, String padding)
			throws Exception {
		PublicKey pubKey = readPublicKey(PublicKey);
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		// đọc file input và lưu file output
		FileInputStream fis = new FileInputStream(new File(Input));
		BufferedInputStream bis = new BufferedInputStream(fis);
		String res = Input.replace(".", "_encrypt.");
		FileOutputStream fos = new FileOutputStream(new File(res));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		processFile(cipher, bis, bos);
		// bos.flush();
		bos.close();
		bis.close();
	}

	// giải mã file
	public void DecryptFile(String fileInput, String PrivateKey, String algorithm, String mode, String padding)
			throws Exception {
		PrivateKey priKey = readPrivateKey(PrivateKey);
		Cipher cipher = Cipher.getInstance(algorithm + "/" + mode + "/" + padding);
		cipher.init(Cipher.DECRYPT_MODE, priKey);

		// đọc file input và lưu file output
		FileInputStream fis = new FileInputStream(new File(fileInput));
		BufferedInputStream bis = new BufferedInputStream(fis);
		String res = fileInput.replace(".", "_decrypt.");
		FileOutputStream fos = new FileOutputStream(new File(res));
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		processFile(cipher, bis, bos);
		// bos.flush();
		bos.close();
		bis.close();
	}
}
