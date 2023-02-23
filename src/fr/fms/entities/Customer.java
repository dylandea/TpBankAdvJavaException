/**
 * @author El babili - 2022
 * 
 */

package fr.fms.entities;

import java.util.ArrayList;

public class Customer {
	private long customerId;
	private String name;
	private String firstName;
	private String email;
	
	private ArrayList<Account> listAccounts;

	public Customer(long customerId, String name, String firstName, String email) {
		this.customerId = customerId;
		this.name = name;
		this.firstName = firstName;
		this.email = email;
		this.listAccounts = new ArrayList<Account>();
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", name=" + name + ", firstName=" + firstName + ", email=" + email
				+ "]";
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<Account> getListAccounts() {
		return listAccounts;
	}
}
