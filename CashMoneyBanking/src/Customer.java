
public class Customer {

	private String name;
	private Account[] accounts;
	
	public Customer(String name, Account[] accounts)
	{
		this.name = name;
		this.accounts = accounts;
	}
	
	public String getName() {
		return name;
	}
	
	public Account[] getAccounts() {
		return accounts;
	}
}
