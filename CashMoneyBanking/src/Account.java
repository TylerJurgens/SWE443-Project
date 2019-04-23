import java.time.LocalDateTime;

public class Account {

	private double balance;
	private int ID;
	private String transactionHistory;
	
	public Account(int ID, double amount)
	{
		transactionHistory = "[" + LocalDateTime.now() + "] Account " + ID + " opened with initial deposit of $" + amount; 
		balance = amount;
		this.ID = ID; 
	}
	
	public double getBalance() {
		return balance;
	}
	
	public int getID() {
		return ID;
	}
	
	public String getTransactionHistory() {
		return transactionHistory;
	}
	
	public double addMoney(double amount)
	{
		balance += amount;
		transactionHistory = "[" + LocalDateTime.now() + "] Deposit of $" + amount + " - new balance is $" + balance + "\r\n" + transactionHistory;
		return balance;
	}

	public double removeMoney(double amount)
	{
		balance -= amount;
		transactionHistory = "[" + LocalDateTime.now() + "] Withdrawal of $" + amount + " - new balance is $" + balance + "\r\n" + transactionHistory;
		return balance;
	}
}
