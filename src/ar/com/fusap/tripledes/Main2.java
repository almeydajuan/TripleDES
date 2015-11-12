package ar.com.fusap.tripledes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Created by jualmeyda on 11/7/15.
 */
public class Main2 {

	public static void main(String[] args) throws Exception {
		PropertiesConfiguration properties = new PropertiesConfiguration();

		SecretKey k1 = example(properties);

		String file1 = "/tmp/image.jpg";
		String file2 = "/tmp/image2.jpg";
		String file3 = "/tmp/image3.jpg";

		// FileReader reads text files in the default encoding.
		byte[] buffer = new byte[1000];

		FileOutputStream fileWriter = new FileOutputStream(file2);

		fileWriter.write(desEncryption(readFile(file1), k1));

		fileWriter.close();

		FileOutputStream fileWriter2 = new FileOutputStream(file3);

		fileWriter2.write(desDecryption(readFile(file2), k1));

		fileWriter2.close();
	}

	private static SecretKey example(PropertiesConfiguration properties) {
		SecretKey k1 = generateDESkey(properties);
		byte[] firstEncryption = desEncryption("plaintext".getBytes(), k1);
		String decryption = new String(desDecryption(firstEncryption, k1));
		System.out.println(decryption);

		File processingFolder = new File(properties.getProcessingPath());
		processingFolder.mkdir();

		return k1;
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

	public static byte[] desEncryption(byte[] bytesToEncipt, SecretKey desKey) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			return cipher.doFinal(bytesToEncipt);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static byte[] desDecryption(byte[] bytesToDecript, SecretKey desKey) {
		try {
			Cipher cipher = Cipher.getInstance("DESede/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			return cipher.doFinal(bytesToDecript);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private static byte[] readFile(String filePath) throws IOException {
		File file = new File(filePath);
		long length = file.length();
		InputStream is = new FileInputStream(file);

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			throw new RuntimeException("file is too large, lenght: " + length);
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Close the input stream and return bytes
		is.close();

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new RuntimeException("Could not completely read file " + file.getName());
		}
		return bytes;
	}
}
