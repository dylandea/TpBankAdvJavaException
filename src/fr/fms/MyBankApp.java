
package fr.fms;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import fr.fms.business.IBankImpl;
import fr.fms.entities.Account;
import fr.fms.entities.Current;
import fr.fms.entities.Customer;
import fr.fms.entities.Saving;
import fr.fms.exceptions.*;

public class MyBankApp {	

	private static Scanner scan = new Scanner(System.in);
	private static IBankImpl bankJob = new IBankImpl();

	public static void main(String[] args) throws Exception {
		initSomeAccounts();
		bankApp();
		scan.close();
	}

	private static void bankApp() throws Exception {
		while(true) {
			try {
				Account account = selectAccount();
				selectOperation(account);			

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private static Account selectAccount() throws Exception {	
		System.out.println("Saisissez un numéro de compte bancaire valide:");
		long accountId = checkAccountRegex(scan.nextLine()); 
		return bankJob.consultAccount(accountId);
	}

	private static void selectOperation(Account account) throws Exception {
		System.out.printf("Bienvenue %s, que souhaitez vous faire ?\n", account.getCustomer().getFirstName());
		int choice = 0;
		while(choice != 6) {
			try {
				System.out.println("---------Saisir le numéro correspondant:----------");
				System.out.print("1:Versement - ");
				System.out.print("2:Retrait - ");
				System.out.print("3:Virement - ");
				System.out.print("4:Informations sur ce compte - ");
				System.out.print("5:Liste des opérations - ");
				System.out.println("6:Sortir");

				choice = checkRangeInput(1, 6);

				switch (choice) {
				case 1:
					deposit(account);
					break;
				case 2:
					withdraw(account);
					break;
				case 3:
					transfer(account);
					break;
				case 4:
					System.out.println(account);
					break;
				case 5:
					if (account.getListTransactions().size() < 1) {
						System.out.println("Aucune transaction à afficher!");
					} else {
						account.getListTransactions().stream().forEachOrdered(System.out::println);
					}
					break;
				case 6:
					System.out.println("Vous sortez du compte " + account.getAccountId());
					break;
				default:
					break;
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void deposit(Account account) throws Exception {	
		System.out.println("Saisissez le montant à déposer sur ce compte :");
		double amount = checkAmountRegex(scan.nextLine().replace(',','.'));
		if (bankJob.pay(account.getAccountId(), amount))
			System.out.println("Versement effectué avec succès.");
	}

	private static void withdraw(Account account) throws Exception {
		System.out.println("Saisissez le montant à retirer sur ce compte :");
		double amount = checkAmountRegex(scan.nextLine().replace(',','.'));
		if (bankJob.withdraw(account.getAccountId(), amount))
			System.out.println("Retrait effectué avec succès.");
	}

	private static void transfer(Account account) throws Exception {
		System.out.println("Vers quel compte souhaitez-vous virer :");
		Account accountTo = selectAccount();
		if (account.getAccountId() == accountTo.getAccountId()) 
			throw new TransferException("Erreur:Vous ne pouvez retirer et verser sur le même compte !");
		System.out.println("Saisissez le montant à virer :");
		double amount = checkAmountRegex(scan.nextLine().replace(',','.'));
		if (bankJob.transfert(account.getAccountId(), accountTo.getAccountId(), amount))
			System.out.println("Transfert effectué avec succès.");
	}

	private static int checkRangeInput(int rangeMin, int rangeMax) throws OutOfRangeException {
		//Gestion saisies inattendues quand on attend un chiffre entier dans une plage précise:	
		String input = scan.nextLine();
		if (!input.matches("\\d")) 
			throw new OutOfRangeException("Erreur:La valeur saisie doit obligatoirement être un chiffre entier.");
		else if (Integer.parseInt(input) < rangeMin || Integer.parseInt(input) > rangeMax) 
			throw new OutOfRangeException("Erreur:La valeur saisie ne correspond à aucun des choix disponibles."); 
		else 
			return Integer.parseInt(input);
	}

	private static long checkAccountRegex(String accountId) throws AccountRegexException {
		if (!accountId.matches("\\d{9,9}")) 
			throw new InputMismatchException("Erreur:un numéro de compte est toujours constitué de 9 chiffres sans espaces ni caractères spéciaux.");
		else return Long.parseLong(accountId);
	}
	
	private static double checkAmountRegex(String amount) throws AmountRegexException {
		if (!amount.matches("\\d+([\\.\\,]\\d{1,2})?")) 
			throw new AmountRegexException("Erreur:Vous devez saisir un montant sous forme de nombres, avec au maximum deux chiffres après la virgule.");
		 else return Double.parseDouble(amount);
	}

	private static void initSomeAccounts() {
		Customer robert = new Customer(1, "Dupont", "Robert", "robert.dupont@gmail.com");
		Customer julie = new Customer(2, "Jolie", "Julie", "julie.jolie@gmail.com");	

		Current firstAccount = new Current(100200300, new Date(), 1500, 200 , robert);
		Saving secondAccount = new Saving(200300400, new Date(), 2000, 5.5, julie);	

		bankJob.addAccount(firstAccount);
		bankJob.addAccount(secondAccount);
	}

}
