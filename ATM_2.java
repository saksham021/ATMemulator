import java.util.Scanner;
import java.io.*;
import java.util.Date;

public class ATM_2 
{
	public static void main(String[] args) 
	{
		Scanner sc = new Scanner(System.in);
		System.out.println("\n\t\t\t###################################");
		System.out.println("\t\t\t#   WELCOME TO THE BANK OF JAVA   #");
		System.out.println("\t\t\t###################################\n");
		Bank theBank = new Bank("Bank of JAVA");
		
		// Add a user, which also creates a Savings account
		User aUser=theBank.addUser("Saksham", "Arora", "1234");
		
		// Add a Current account for user
		Account newAccount = new Account("in Current account", aUser, theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		
		while (true) 
		{
			
			// stay in login screen until successful login
			curUser = ATM_2.mainMenuPrompt(theBank, sc);
			
			// stay in main menu until user quits by pressing CTRL+C
			ATM_2.printUserMenu(curUser, sc);			
		}

	}
	
	public static User mainMenuPrompt(Bank theBank, Scanner sc) 
	{
		String userID;
		String pin;
		User authUser;
		
		do {		
			System.out.print("\n\nEnter your ID: ");
			userID = sc.nextLine();
			System.out.print("Enter your pin: ");
			char[] p = System.console().readPassword(); 
			pin = String.valueOf(p);
			
			// try to get user object corresponding to ID and pin combo
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) 
			{
				System.out.println("Incorrect user ID or Pin");
			}
			
		} while(authUser == null);
		try
		{
			File f = new File("logs.txt");
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			Date d = new Date();
			bw.write("\n"+"User Id: "+userID+" / "+"Pin: "+pin+"\n"+d);
		
			bw.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return authUser;
		
	}
	
	public static void printUserMenu(User theUser, Scanner sc) 
	{
		int choice;
		do {	
			System.out.println("\n1)Create a new account");		
			System.out.println("2)Show transaction history");
			System.out.println("3)Withdraw money");
			System.out.println("4)Deposit money");
			System.out.println("5)Transfer money");
			System.out.println("6)Log in time");
			System.out.println("7)Show Account Summary");
			System.out.println("8)Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();	
		} while (choice < 1 || choice > 8);
		
		switch (choice) 
		{
		case 1: 		
			System.out.println("\n...Bank of JAVA welcomes you to its family...\n");
			System.out.println("Please enter your last name: ");
			String lname=sc.nextLine();
			sc.nextLine();
			System.out.print("");
			System.out.println("Please enter your first name: ");
			String fname=sc.nextLine();
			System.out.println("Please enter a pin: ");
			String pin=sc.nextLine();	

			Bank theBank = new Bank("Bank of JAVA");
			// Add a user, which also creates a Savings account
			User aUser=theBank.addUser(fname, lname, pin);
		
			// add a checking account for our user
			Account newAccount = new Account("in Current account", aUser, theBank);
			aUser.addAccount(newAccount);
			theBank.addAccount(newAccount);
			User curUser;
		
			while (true) 
			{
			
				// stay in login prompt until successful login
				curUser = ATM_2.mainMenuPrompt(theBank, sc);
				
				// stay in main menu until user quits by pressing CTRL+C
				ATM_2.printUserMenu(curUser, sc);			
			}
			
		case 2:	ATM_2.showTransHistory(theUser, sc);break;
		case 3:	ATM_2.withdrawFunds(theUser, sc);break;
		case 4:	ATM_2.depositFunds(theUser, sc);break;
		case 5:	ATM_2.transferFunds(theUser, sc);break;
		case 6: try
			{
				File f = new File("logs.txt");
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);
				
				String line = null;
				while((line=br.readLine())!=null)	
				{
					System.out.println(line);
				}
			
				br.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			break;
		case 7:	theUser.printAccountsSummary();	break;
		case 8:	System.exit(0);
		default:System.out.println("!!!...Invalid...!!!");
		}
		
		// redisplay this menu unless the user wants to quit
		if (choice != 8) 
		{
			ATM_2.printUserMenu(theUser, sc);
		}
		
	}
	
	public static void transferFunds(User theUser, Scanner sc) 
	{	
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		// get account to transfer from
		do {
			System.out.printf("Enter the number (1-%d) of the account to "+"transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account");
			}
		   } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		// get account to transfer to
		do {
			System.out.printf("Enter the number (1-%d) of the account to "+"transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account");
			}
		   } while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		// get amount to transfer
		do {
			System.out.printf("Enter the amount to transfer: Rs.(max Rs. %.02f) ",acctBal);
			amount = sc.nextDouble();
			if (amount < 0) 
			{
				System.out.println("Amount must be greater than zero.");
			} 
			else if (amount > acctBal) 
			{
				System.out.printf("Amount must not be greater than balance "+"of Rs. .02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		// finally, do the transfer 
		theUser.addAcctTransaction(fromAcct, -1*amount);
		theUser.addAcctTransaction(toAcct, amount);
		
	}

	public static void withdrawFunds(User theUser, Scanner sc) 
	{	
		int fromAcct;
		double amount;
		double acctBal;
		
		// get account to withdraw from
		do {
			System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		acctBal = theUser.getAcctBalance(fromAcct);
		
		// get amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw: Rs.(max Rs. %.02f)",acctBal);
			amount = sc.nextDouble();
			if (amount < 0) 
			{
				System.out.println("Amount must be greater than zero.");
			} 
			else if (amount > acctBal) 
			{
				System.out.printf("Amount must not be greater than balance "+"of Rs. %.02f.\n", acctBal);
			}
		   } while (amount < 0 || amount > acctBal);
		
		sc.nextLine();

		/*System.out.print("Enter a memo: ");
		memo = sc.nextLine();*/
		
		// do the withdrwal
		theUser.addAcctTransaction(fromAcct, -1*amount);
	}

	public static void depositFunds(User theUser, Scanner sc) 
	{		
		int toAcct;
		double amount;
		//String memo;
		
		// get account to withdraw from
		do {
			System.out.printf("Enter the number (1-%d) of the account to "+"deposit to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		// get amount to transfer
		do {
			System.out.printf("Enter the amount to deposit Rs. ");
			amount = sc.nextDouble();
			if (amount < 0) 
			{
				System.out.println("Amount must be greater than zero.");
			} 
		} while (amount < 0);

		sc.nextLine();
		
		//System.out.print("Enter a memo: ");
		//memo = sc.nextLine();
		
		// do the deposit
		theUser.addAcctTransaction(toAcct, amount);	
	}
	
	public static void showTransHistory(User theUser, Scanner sc) 
	{
		int theAcct;
		
		// get account whose transactions to print
		do {
			System.out.printf("Enter the number (1-%d) of the account whose transactions you want to see: ", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) 
			{
				System.out.println("Invalid account");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());
		
		// print the transaction history
		theUser.printAcctTransHistory(theAcct);		
	}
}