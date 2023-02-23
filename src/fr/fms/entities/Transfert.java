/**
 * @author El babili - 2022
 * 
 */

package fr.fms.entities;

import java.util.Date;

public class Transfert extends Transaction {

	public Transfert(long transactionId, Date transactionDate, double amount, long accountId) {
		super(transactionId, transactionDate, amount, accountId);		
	}

	@Override
	public String toString() {
		return "Versement : " + super.toString();
	}
	
}
