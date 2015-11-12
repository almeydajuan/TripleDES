package ar.com.fusap.tripledes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.DatatypeConverter;

import ar.com.fusap.tripledes.exception.TripleDesException;

/**
 * Created by jualmeyda on 11/11/15.
 */
public class TripleDesService {

	public static final String suffix = ".pgp";

	public static SecretKey generateDESkey(String desKey) {
		try {
			byte[] keyBytes = DatatypeConverter.parseHexBinary(desKey);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
			return factory.generateSecret(new DESedeKeySpec(keyBytes));
		} catch (Exception e) {
			throw new TripleDesException(e);
		}
	}

	public static byte[] desEncryption(byte[] fileBytes, SecretKey desKey) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			return cipher.doFinal(fileBytes);
		} catch (Exception e) {
			throw new TripleDesException(e);
		}
	}

	public static byte[] desDecryption(byte[] fileBytes, SecretKey secretKey) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(fileBytes);
		} catch (Exception e) {
			throw new TripleDesException(e);
		}
	}
}
