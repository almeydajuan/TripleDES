package ar.com.fusap.tripledes.exception;

/**
 * Created by jualmeyda on 11/12/15.
 */
public class FileSystemException extends RuntimeException {

	public FileSystemException(Exception e) {
		super(e);
	}
}
