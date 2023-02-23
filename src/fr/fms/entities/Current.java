/**
 * @author El babili - 2022
 * 
 */

package fr.fms.entities;

import java.util.Date;

public class Current extends Account {
	private int overdraft;

	public Current(long accountId, Date creationDate, double balance, int overdraft, Customer customer) {
		super(accountId, creationDate, balance, customer);
		this.overdraft = overdraft;
	}

	public int getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(int overdraft) {
		this.overdraft = overdraft;
	}

	@Override
	public String toString() {
		return "Current Account " + super.toString() +  ", overdraft=" + overdraft + "]";
	}
}
