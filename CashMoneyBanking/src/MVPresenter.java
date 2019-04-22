
public class MVPresenter {
	
	private Model model;
	
	public MVPresenter()
	{
		model = new Model();
	}
	
	public double fetchBalance(int accountNumber, String userName)
	{	
		if(!model.userHasAccess(userName, accountNumber))
			return -1;
		return model.getBalance(accountNumber);
	}
	
	public int[] fetchAccounts(String userName)
	{
		return model.getAccounts(userName);
	}
	
	public double deposit(int accountNumber, String userName, int amount)
	{
		return model.depositFunds(accountNumber, amount);
	}
	
	public double withdraw(int accountNumber, String userName, int amount)
	{
		return model.withdrawFunds(accountNumber, amount);
	}
	
	public String fetchTransactionHistory(int accountNumber, String userName)
	{
		return model.getHistory(accountNumber);
	}
}
