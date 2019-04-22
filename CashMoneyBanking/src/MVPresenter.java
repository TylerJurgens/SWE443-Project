
public class MVPresenter {
	
	private Model model;
	
	public MVPresenter()
	{
		model = new Model();
	}
	
	public double fetchBalance(int accountNumber, String userName)
	{
		return model.getBalance(accountNumber, userName);
	}
	
	public int[] fetchAccounts(String userName)
	{
		return model.getAccounts(userName);
	}
}
