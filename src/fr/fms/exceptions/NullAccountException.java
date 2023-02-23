package fr.fms.exceptions;

public class NullAccountException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public NullAccountException(String message) {
		super(message);
	}
		
}