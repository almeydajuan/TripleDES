package ar.com.fusap.tripledes;

import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Main {

	public static void main(String[] args) {
		try {
			SecretKey key1 = generateDESkey();
			String data = "Confidential data";
			Cipher encryptCipher1 = generateEncriptCipher(key1);
			Cipher decryptCipher1 = generateDecriptCipher(key1, encryptCipher1);

			byte[] encryptedData = encriptData(data, encryptCipher1);
			decryptData(encryptedData, decryptCipher1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Cipher generateDecriptCipher(SecretKey key1, Cipher encryptCipher) throws Exception {
		Cipher decryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		byte iv[] = encryptCipher.getIV();
		IvParameterSpec dps = new IvParameterSpec(iv);
		decryptCipher.init(Cipher.DECRYPT_MODE, key1, dps);
		return decryptCipher;
	}

	private static Cipher generateEncriptCipher(SecretKey key1) throws Exception {
		Cipher encryptCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key1);
		return encryptCipher;
	}

	public static SecretKey generateDESkey() {
		KeyGenerator keyGen = null;
		try {
			keyGen = KeyGenerator.getInstance("DESede");
		} catch (NoSuchAlgorithmException ex) {
		}
		keyGen.init(112); // key length 112 for two keys, 168 for three keys
		SecretKey secretKey = keyGen.generateKey();
		return secretKey;
	}

	private static byte[] encriptData(String data, Cipher encryptCipher) throws Exception {
		System.out.println("Data Before Encryption :" + data);
		byte[] dataToEncrypt = data.getBytes();
		byte[] encryptedData = encryptCipher.doFinal(dataToEncrypt);
		System.out.println("Encryted Data: " + encryptedData);

		return encryptedData;
	}

	private static void decryptData(byte[] data, Cipher decryptCipher) throws Exception {
		byte[] textDecrypted = decryptCipher.doFinal(data);
		System.out.println("Decryted Data: " + new String(textDecrypted));
	}
}
