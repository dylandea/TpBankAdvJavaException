/**
 * @author El babili - 2022
 * 
 */

package fr.fms.entities;

import java.util.Date;

public class Saving extends Account {
	private double rate;

	public Saving(long accountId, Date creationDate, double balance, double rate, Customer customer) {
		super(accountId, creationDate, balance, customer);
		this.rate = rate;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Saving Account " + super.toString() +  ", rate=" + rate + "]";
	}
}
