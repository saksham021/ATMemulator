import java.security.MessageDigest;
import java.util.ArrayList;

public class User 
{
	private String fName;//First Name
	private String lName;//Last Name
	private byte pinHash[];
	private String uuid;
	private ArrayList<Account> accounts;

	public User(String fName, String lName, String pin, Bank theBank) 
	{
		this.fName = fName;
		this.lName = lName;
		
		//Store the pin's MD5 hash value, instead of the original value for security reasons
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(pin.getBytes());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Caught exception: "+e.getMessage());
			System.exit(1);
		}
		
		// get a new, universal unique ID for the user
		this.uuid=theBank.getNewUserUUID();
		
		// create empty list of accounts
		this.accounts=new ArrayList<Account>();
		
		// print log message
		System.out.printf("We have \"%s %s\" as current user with ID %s and pin is %s", fName, lName, this.uuid, pin);
	}
	public String getUUID() 
	{
		return this.uuid;
	}
	public void addAccount(Account anAcct) 
	{
		this.accounts.add(anAcct);
	}
	public int numAccounts() 
	{
		return this.accounts.size();
	}
	public double getAcctBalance(int acctIdx) 
	{
		return this.accounts.get(acctIdx).getBalance();
	}
	public String getAcctUUID(int acctIdx) 
	{
		return this.accounts.get(acctIdx).getUUID();
	}
	public void printAcctTransHistory(int acctIdx) 
	{
		this.accounts.get(acctIdx).printTransHistory();
	}
	public void addAcctTransaction(int acctIdx, double amount) 
	{
		this.accounts.get(acctIdx).addTransaction(amount);
	}
	public boolean validatePin(String aPin) 
	{
		
		try 
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash);
		} 
		catch (Exception e) 
		{
			System.err.println("Exception caught: "+e.getMessage());
			System.exit(1);
		}
		return false;
	}
	
	public void printAccountsSummary() 
	{	
		System.out.printf("\n%s's account summary\n",this.fName);
		for (int a = 0; a < this.accounts.size(); a++) 
		{
			System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
		}
		System.out.println();		
	}
}