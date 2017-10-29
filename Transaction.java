import java.util.Date;

public class Transaction 
{
	private double amount;
	private Date timestamp;
	private Account inAccount;

	public Transaction(double amount, Account inAccount) 
	{	
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
	}

	public double getAmount() 
	{
		return this.amount;
	}
	public String getSummaryLine() 
	{		
		if (this.amount >= 0) 
		{
			return String.format("%s, Rs. %.02f : ", this.timestamp.toString(), this.amount);
		} 
		else 
		{
			return String.format("%s, Rs. %.02f : ", this.timestamp.toString(), this.amount);
		}
	}

}