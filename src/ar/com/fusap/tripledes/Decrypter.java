package ar.com.fusap.tripledes;

import javax.crypto.SecretKey;

/**
 * Created by jualmeyda on 11/11/15.
 */
public class Decrypter {

	public static void main(String[] args) throws Exception {
		// va a venir por parametro
		String encriptedFile = "/tmp/processed/source.jpg.pgp";
		try {
			PropertiesConfiguration properties = new PropertiesConfiguration();
			SecretKey secretKey = TripleDesService.generateDESkey(properties.getDESKey());
			byte[] fileBytes = FileSystemService.readFile(encriptedFile);
			byte[] decryptedBytes = TripleDesService.desDecryption(fileBytes, secretKey);
			FileSystemService.writeDecryptedFile(encriptedFile, properties, decryptedBytes);
		} catch (Exception e) {
			FileSystemService.copyFileToErrorFolder(encriptedFile);
			throw e;
		}
	}
}
