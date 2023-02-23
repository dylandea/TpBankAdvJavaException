package fr.fms.business;

import java.util.ArrayList;
import fr.fms.entities.Account;
import fr.fms.entities.Transaction;
import fr.fms.exceptions.NullAccountException;

/**
 * @author El babili - 2022
 * @since 1.0
 * Interface qui représente la couche métier de l'application bancaire, reprenant fonctionnalités du doc de spécifications fonctionnelles 
 */

public interface IBank {
	public void addAccount(Account account);							//ajoute un compte associé à un client à notre banque
	public Account consultAccount(long accountId) throws NullAccountException;						//renvoi le compte correspondant à l'id 
	public boolean pay(long accountId, double amount) throws NullAccountException;						//faire un versement sur un compte 
	public boolean withdraw(long accountId, double amount) throws NullAccountException;				//faire un retrait sur un compte
	public boolean transfert(long accIdSrc, long accIdDest, double amount) throws NullAccountException, Exception;	//faire un virement d'un compte source vers destination
	public ArrayList<Transaction> listTransactions(long accountId) throws NullAccountException; 	//renvoi la liste des opérations sur un compte donné
}
