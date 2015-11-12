package ar.com.fusap.tripledes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import ar.com.fusap.tripledes.exception.FileSystemException;

/**
 * Created by jualmeyda on 11/11/15.
 */
public class FileSystemService {

	private static String generateProcessedFileName(String processedFolderPath, String fileName) {
		return String.format("%s/%s%s", processedFolderPath, fileName, TripleDesService.suffix);
	}

	private static String generateDecryptedFileName(String nextFolderPath, String encryptedFile) {
		return String.format("%s/%s", nextFolderPath, encryptedFile.replace(TripleDesService.suffix, ""));
	}

	private static String getFileName(String sourceFile) {
		String[] split = sourceFile.split("/");
		return split[split.length - 1];
	}

	public static void writeEncryptedFile(String sourceFilePath, PropertiesConfiguration propertiesConfiguration, byte[] encryptedBytes) {
		try {
			String processedFolderPath = generateFolder(propertiesConfiguration.getProcessedFolderPath());
			String processedFile = generateProcessedFileName(processedFolderPath, getFileName(sourceFilePath));

			FileOutputStream fileWriter = new FileOutputStream(processedFile);
			fileWriter.write(encryptedBytes);
			fileWriter.close();
		} catch (IOException e) {
			throw new FileSystemException(e);
		}
	}

	public static void writeDecryptedFile(String encriptedFilePath, PropertiesConfiguration properties, byte[] decryptedBytes) {
		try {
			String nextFolderPath = generateFolder(properties.getNextFolderPath());
			String decryptedFile = generateDecryptedFileName(nextFolderPath, getFileName(encriptedFilePath));

			FileOutputStream fileWriter = new FileOutputStream(decryptedFile);
			fileWriter.write(decryptedBytes);
			fileWriter.close();
		} catch (IOException e) {
			throw new FileSystemException(e);
		}
	}

	private static String generateFolder(String folderPath) {
		new File(folderPath).mkdir();
		return folderPath;
	}

	public static byte[] readFile(String filePath) {
		try {
			File file = new File(filePath);
			long length = file.length();
			InputStream is = new FileInputStream(file);

			// You cannot create an array using a long type.
			// It needs to be an int type.
			// Before converting to an int type, check
			// to ensure that file is not larger than Integer.MAX_VALUE.
			if (length > Integer.MAX_VALUE) {
				throw new FileSystemException(new RuntimeException("file is too large, lenght: " + length));
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
				throw new FileSystemException(new RuntimeException("Could not completely read file " + file.getName()));
			}
			return bytes;
		} catch (Exception e) {
			throw new FileSystemException(e);
		}
	}

	public static void copyFileToErrorFolder(String filePath) {
		try {
			String errorFolderPath = new PropertiesConfiguration().getErrorFolderPath();
			Files.copy(new File(filePath).toPath(), new File(String.format("%s/%s", errorFolderPath, getFileName(filePath))).toPath());
		} catch (IOException e) {
			throw new FileSystemException(e);
		}
	}
}
