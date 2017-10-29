import java.util.ArrayList;

public class Account 
{
	private String name;
	private String uuid;
	private User holder;
	private ArrayList<Transaction> transactions;
	
	public Account(String name, User holder, Bank theBank) 
	{
		this.name = name;
		this.holder = holder;
		
		// get next account uuid
		this.uuid = theBank.getNewAccountUUID();
		
		// init transactions
		this.transactions = new ArrayList<Transaction>();	
	}
	public String getUUID() 
	{
		return this.uuid;
	}
	public void addTransaction(double amount) 
	{
		Transaction newTrans = new Transaction(amount, this);
		this.transactions.add(newTrans);	
	}
	public void addTransaction(double amount, String memo) 
	{
		// create new transaction and add it to our list
		Transaction newTrans = new Transaction(amount, this);
		this.transactions.add(newTrans);
	}
	
	public double getBalance() 
	{
		double balance = 100000;
		for (Transaction t : this.transactions) 
		{
			balance += t.getAmount();
		}
		return balance;
		
	}
	
	public String getSummaryLine() 
	{
		
		double balance = this.getBalance();
		
		if (balance >= 0) 
		{
			return String.format("%s : Rs. %.02f %s", this.uuid, balance, this.name);
		} 
		else 
		{
			return String.format("%s : Rs. %.02f %s", this.uuid, balance, this.name);
		}
		
	}
	public void printTransHistory() 
	{	
		System.out.printf("\nTransaction history for account %s\n", this.uuid);
		for (int t = this.transactions.size()-1; t >= 0; t--) 
		{
			System.out.println(this.transactions.get(t).getSummaryLine());
		}
		System.out.println("");
		
	}

}