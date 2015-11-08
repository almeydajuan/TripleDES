package ar.com.fusap.tripledes;

import java.io.File;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * Created by jualmeyda on 11/7/15.
 */
public class Main2 {

	public static void main(String[] args) {
		PropertiesConfiguration properties = new PropertiesConfiguration();

		String filename = "/tmp/jaja.txt";

		SecretKey k1 = generateDESkey(properties);
		String firstEncryption = desEncryption("plaintext", k1);
		File processingFolder = new File(properties.getProcessingPath());
		processingFolder.mkdir();

		System.out.println(firstEncryption);
		String decryption = desDecryption(firstEncryption, k1);
		System.out.println(decryption);
	}

	public static SecretKey generateDESkey(PropertiesConfiguration properties) {
		try {
			String desKey = properties.getDESKey();
			byte[] keyBytes = DatatypeConverter.parseHexBinary(desKey);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = null;
			key = factory.generateSecret(new DESedeKeySpec(keyBytes));
			return key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String desEncryption(String strToEncrypt, SecretKey desKey) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			String encryptedString = Base64.encode(cipher.doFinal(strToEncrypt.getBytes()));
			return encryptedString;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String desDecryption(String strToDecrypt, SecretKey desKey) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			String decryptedString = new String(cipher.doFinal(Base64.decode(strToDecrypt)));
			return decryptedString;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
