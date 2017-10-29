import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bank 
{
	private String name;
	private ArrayList<User> users;
	private ArrayList<Account> accounts;
	public Bank(String name) 
	{	
		this.name = name;
		users = new ArrayList<User>();
		accounts = new ArrayList<Account>();
	}
	public String getNewUserUUID() 
	{
		String uuid;
		Random rng = new Random();
		int len = 6;
		boolean nonUnique;
		
		// continue looping until we get a unique ID
		do {
			
			// generate the number
			uuid = "";
			for (int c = 0; c < len; c++) 
			{
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			// check to make sure it's unique
			nonUnique = false;
			for (User u : this.users) 
			{
				if (uuid.compareTo(u.getUUID()) == 0) 
				{
					nonUnique = true;
					break;
				}
			}
		} while (nonUnique);
		return uuid;
	}
	
	public String getNewAccountUUID() 
	{
		String uuid;
		Random rng = new Random();
		int len = 10;
		boolean nonUnique = false;
		
		// continue looping until we get a unique ID
		do {
			
			// generate the number
			uuid = "";
			for (int c = 0; c < len; c++) 
			{
				uuid += ((Integer)rng.nextInt(10)).toString();
			}
			
			// check to make sure it's unique
			for (Account a : this.accounts) 
			{
				if (uuid.compareTo(a.getUUID()) == 0) 
				{
					nonUnique = true;
					break;
				}
			}		
		} while (nonUnique);
		return uuid;			
	}
	public User addUser(String fName, String lName, String pin) 
	{
		User newUser = new User(fName, lName, pin, this);
		this.users.add(newUser);
		
		// create a savings account for the user and add it to our list
		Account newAccount = new Account("in Savings account", newUser, this);
		newUser.addAccount(newAccount);
		this.accounts.add(newAccount);
		
		return newUser;	
	}
	public void addAccount(Account newAccount) 
	{
		this.accounts.add(newAccount);
	}
	public User userLogin(String userID, String pin) 
	{
		// search through list of users
		for (User u : this.users) 
		{
			// if we find the user, and the pin is correct, return User object
			if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) 
			{
				return u;
			}
		}
		// if we haven't found the user or have an incorrect pin, return null
		return null;		
	}
	public String getName() 
	{
		return this.name;
	}

}