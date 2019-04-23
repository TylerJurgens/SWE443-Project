
public class Model {
	
	private Customer customer;
	
	public Model()
	{
		Account [] accounts = {new Account(1,20), new Account(2,100)};
		this.customer = new Customer("bob",accounts);
	}
	
	public int[] getAccounts(String customer)
	{
		Account[] accounts = this.customer.getAccounts();
		int[] ret = new int[accounts.length];
		for(int i = 0 ; i < accounts.length; i ++)
		{
			ret[i] = accounts[i].getID();
		}
		return ret;
	}
	
	public double getBalance(int account)
	{
		Account[] accounts = this.customer.getAccounts();
		for(int i = 0 ; i < accounts.length; i ++)
		{
			if(accounts[i].getID() == account)
			{
				return accounts[i].getBalance();
			}
		}
		return -1;
	}
	
	public boolean userHasAccess(String customer, int accountNumber)
	{
		return true;
	}
	
	public double depositFunds(int account, double amount)
	{
		if(amount < 0)
			return -1;
		Account[] accounts = this.customer.getAccounts();
		for(int i = 0 ; i < accounts.length; i ++)
		{
			if(accounts[i].getID() == account)
			{
				return accounts[i].addMoney(amount);
			}
		}
		return 0.0;
	}
	
	public double withdrawFunds(int account, double amount)
	{
		if(amount < 0)
			return -1;
		Account[] accounts = this.customer.getAccounts();
		for(int i = 0 ; i < accounts.length; i ++)
		{
			if(accounts[i].getID() == account)
			{
				if(accounts[i].getBalance() < amount)
					return -2;
				return accounts[i].removeMoney(amount);
			}
		}
		return 0.0;
	}
	
	public String getHistory(int account)
	{
		Account[] accounts = this.customer.getAccounts();
		for(int i = 0; i < accounts.length; i++)
		{
			if(accounts[i].getID() == account)
			{
				return accounts[i].getTransactionHistory();
			}
		}
		return null;
	}
}
