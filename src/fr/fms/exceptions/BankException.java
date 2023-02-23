package fr.fms.exceptions;

public class BankException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public BankException(String message) {
		super(message);
	}
	
	public static void main(String[] args) {
		try {
			police();
		} catch (BankException e) {
			e.printStackTrace();
		}		
	}
	
	public static void police() throws BankException {
		try {
			banquier();
		} catch (BankException e) {			
			e.printStackTrace();	
			System.out.println("la situation nous dépasse !");
			throw new BankException("appeler le Raid !");
		}
	}
	
	public static void banquier() throws BankException {
		vigil();	
	}
	
	public static void vigil() throws BankException {
		throw new BankException("nous vous informons qu'un braquage est en cours");
	}
}