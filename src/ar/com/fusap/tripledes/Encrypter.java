package ar.com.fusap.tripledes;

import javax.crypto.SecretKey;

/**
 * Created by jualmeyda on 11/11/15.
 */
public class Encrypter {

	public static void main(String[] args) throws Exception {
		// va a venir por parametro
		String sourceFile = "/tmp/source.jpg";

		PropertiesConfiguration properties = new PropertiesConfiguration();
		SecretKey secretKey = TripleDesService.generateDESkey(properties.getDESKey());

		byte[] fileBytes = FileSystemService.readFile(sourceFile);
		byte[] encryptedBytes = TripleDesService.desEncryption(fileBytes, secretKey);
		FileSystemService.writeEncryptedFile(sourceFile, properties, encryptedBytes);
	}
}
