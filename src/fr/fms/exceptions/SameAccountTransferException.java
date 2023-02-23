
package fr.fms.exceptions;

public class SameAccountTransferException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public SameAccountTransferException(String message) {
		super(message);
	}
		
}
