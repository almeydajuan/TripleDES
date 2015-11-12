package ar.com.fusap.tripledes;

import javax.crypto.SecretKey;

/**
 * Created by jualmeyda on 11/11/15.
 */
public class Encrypter {

	public static void main(String[] args) {
		// va a venir por parametro
		String sourceFilePath = "/tmp/source.jpg";
		try {
			PropertiesConfiguration properties = new PropertiesConfiguration();
			SecretKey secretKey = TripleDesService.generateDESkey(properties.getDESKey());
			byte[] fileBytes = FileSystemService.readFile(sourceFilePath);
			byte[] encryptedBytes = TripleDesService.desEncryption(fileBytes, secretKey);
			FileSystemService.writeEncryptedFile(sourceFilePath, properties, encryptedBytes);
		} catch (Exception e) {
			FileSystemService.copyFileToErrorFolder(sourceFilePath);
			throw e;
		}
	}
}
