
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

	public static void main(String[] args) throws InputMismatchException, NullPointerException, AccountRegexException, NullAccountException, AmountRegexException, SameAccountTransferException, TransferException {
		initSomeAccounts();
		bankApp();
		scan.close();
	}

	private static void bankApp() throws InputMismatchException, NullPointerException, AccountRegexException, NullAccountException, AmountRegexException, SameAccountTransferException, TransferException {
		while(true) {
			try {
				Account account = selectAccount();

				System.out.printf("Bienvenue %s, que souhaitez vous faire ?\n", account.getCustomer().getFirstName());
				selectOperation(account);			

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static Account selectAccount() throws AccountRegexException, InputMismatchException, NullPointerException, NullAccountException{	
		System.out.println("Saisissez un numéro de compte bancaire valide:");
		long accountId = checkAccountRegex(scan.nextLine()); 
		return bankJob.consultAccount(accountId);
	}

	private static void selectOperation(Account account) throws NullAccountException, AccountRegexException, AmountRegexException, InputMismatchException, NullPointerException, SameAccountTransferException, TransferException {
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
					if (deposit(account))
						System.out.println("Versement effectué avec succès.");
					break;
				case 2:
					if (withdraw(account))
						System.out.println("Retrait effectué avec succès.");
					break;
				case 3:
					if (transfer(account))
						System.out.println("Transfert effectué avec succès.");
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

	private static boolean deposit(Account account) throws NullAccountException, AmountRegexException {	
		System.out.println("Saisissez le montant à déposer sur ce compte :");
		double amount = checkAmountRegex(scan.nextLine().replace(',','.'));
		return bankJob.pay(account.getAccountId(), amount);
	}

	private static boolean withdraw(Account account) throws NullAccountException, AmountRegexException {
		System.out.println("Saisissez le montant à retirer sur ce compte :");
		double amount = checkAmountRegex(scan.nextLine().replace(',','.'));
		return bankJob.withdraw(account.getAccountId(), amount);
	}

	private static boolean transfer(Account account) throws InputMismatchException, NullPointerException, AccountRegexException, NullAccountException, AmountRegexException, SameAccountTransferException, TransferException {
		System.out.println("Vers quel compte souhaitez-vous virer :");
		Account accountTo = selectAccount();
		if (account.getAccountId() == accountTo.getAccountId()) {
			throw new RuntimeException("Vous ne pouvez retirer et verser sur le même compte !");
		}
		double amount = checkAmountRegex(scan.nextLine().replace(',','.'));
		return bankJob.transfert(account.getAccountId(), accountTo.getAccountId(), amount);
	}

	private static double checkAmountRegex(String amount) throws AmountRegexException {
		if (!amount.matches("\\d+([\\.\\,]\\d{1,2})?")) {
			throw new AmountRegexException("Saisie invalide: Vous devez saisir un montant sous forme de nombres, avec au maximum deux chiffres après la virgule.");
		} else {
			return Double.parseDouble(amount);
		}
	}

	public static int checkRangeInput(int rangeMin, int rangeMax) throws OutOfRangeException {
		//Gestion saisies inattendues quand on attend un CHIFFRE ENTIER dans une plage précise:	
		String input = scan.nextLine();
		if (!input.matches("\\d")) 
			throw new OutOfRangeException("La valeur saisie doit obligatoirement être un chiffre entier.");
		else if (Integer.parseInt(input) < rangeMin || Integer.parseInt(input) > rangeMax) 
			throw new OutOfRangeException("La valeur saisie ne correspond à aucun des choix disponibles."); 
		else 
			return Integer.parseInt(input);
	}

	public static long checkAccountRegex(String accountId) throws AccountRegexException {
		if (!accountId.matches("\\d{9,9}")) {
			throw new InputMismatchException("Saisie invalide: un numéro de compte est toujours constitué de 9 chiffres sans espaces ni caractères spéciaux.");
		} else {
			return Long.parseLong(accountId);
		}

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
