
public class MVPView {
	
	public MVPView()
	{
		
	}
	
	public double fetchBalance(int accountNumber, String userName)
	{
		if(accountNumber == 1)
		{
			return 0.10;
		}
		else
			return 20.00;
	}
	
	public int[] fetchAccounts(String userName)
	{
		int[] accounts = {1,2};
		return accounts;
	}
}
