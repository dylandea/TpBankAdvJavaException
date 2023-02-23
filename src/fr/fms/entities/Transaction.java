/**
 * @author El babili - 2022
 * 
 */

package fr.fms.entities;

import java.util.Date;

public abstract class Transaction {
	private long transactionId;
	private Date transactionDate;
	private double amount;
	
	private long accountId;

	public Transaction(long transactionId, Date transactionDate, double amount, long accountId) {
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.accountId = accountId;
	}
	
	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", transactionDate=" + Account.SIMPLE_DATE_FORMAT.format(transactionDate) + ", amount="
				+ amount + ", accountId=" + accountId + "]";
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	
}
