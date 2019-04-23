import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class PrimaryController {
	
	private VirtHelpEventHandler virtHelp;
	private boolean helpToggle;

	@FXML
	private Button signoutButton;
	@FXML
	private Label helperText;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab accountsTab;
	@FXML
	private Tab settingsTab;
	@FXML
	private ToggleButton virtHelpToggle;
	
	@FXML
	private ComboBox<String> accountSelection;
	@FXML
	private Label balanceLabel, balanceValue, transactionHistory;
	@FXML
	private ScrollPane transactionHistoryPane;
	@FXML
	private Button depositButton, withdrawButton;
	
	//Holds the ID of the current displayed account
	private int currentAccount;
	//Presenter class for MVP architecture
	private MVPresenter presenter;
	//Name of the currently logged in user
	private static String name;
	
	private int[] accountIDs;
	
	@FXML
	public void initialize() { //Automatically called upon being loaded
		this.virtHelp = new VirtHelpEventHandler();
		
		this.helpToggle = true; // should be retrieved from database
		this.virtHelpToggle.setSelected(!this.helpToggle);
		this.virtHelpToggle.setText((this.helpToggle) ?"On":"Off");
		this.virtHelp.handleEvent(this.helpToggle, this.helperText, this.virtHelpToggle.getText());
		
		this.currentAccount = -1;
		this.presenter = new MVPresenter();
		this.accountIDs = presenter.fetchAccounts(name);
		
		
		//Binds a listener to accountSelection, updating the value of currentAccount when changed
	    accountSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	    		@Override public void changed(ObservableValue<? extends String> selected, String oldAccount, String newAccount) {
	    			if(newAccount != null)
	    			{
	    				switch(newAccount)
	    				{
	    					case "Savings":
	    						currentAccount = accountIDs[0];
	    						break;
	    					case "Checkings":
	    						currentAccount = accountIDs[1];
	    						break;
	    				}
	    				balanceValue.setText("" + presenter.fetchBalance(currentAccount, name));
	    				transactionHistory.setText(presenter.fetchTransactionHistory(currentAccount, name));
	    			}
	    			
	    		}
	    });
	}
	
	public void setUser(String user) {
		this.name = user;
	}
	
	public static String getUser() {
		return name;
	}
	
	private static Scanner scanner;
	public static double readChecking() {
		String username = "";
		String accountType = "";
		double balance = 0;
		try {
			scanner = new Scanner(new File("accounts.txt"));
			scanner.useDelimiter("[,\n]");
				
			while(scanner.hasNext()) {
				username = scanner.next();
				accountType = scanner.next();
				balance = Double.valueOf(scanner.next());
				System.out.println(username);
				if(username.equals(name)) {
					System.out.println("User = true");
					if(accountType.equals("Checking")) {
						return balance;
					}
				}
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Read failed");
		}
		return balance;
		
	}
	
	public static double readSavings() {
		String username = "";
		String accountType = "";
		double balance = 0;
		try {
			scanner = new Scanner(new File("accounts.txt"));
			scanner.useDelimiter("[,\n]");
				
			while(scanner.hasNext()) {
				username = scanner.next();
				accountType = scanner.next();
				balance = Double.valueOf(scanner.next());
				System.out.println(username);
				if(username.equals(name)) {
					System.out.println("User = true");
					if(accountType.equals("Savings")) {
						return balance;
					}
				}
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Read failed");
		}
		return balance;
		
	}
	
	public static void writeChecking(double input) {
		String updateFile = "update.txt";
		File oldFile = new File(filepath);
		File newFile = new File(updateFile);
		String username = "";
		String accountType = "";
		String balance = "";
		double newBalance = 0;
		try {
			FileWriter fw = new FileWriter(updateFile, false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			scanner = new Scanner(new File(filepath));
			scanner.useDelimiter("[,\n]");
			
			while(scanner.hasNext()) {
				username = scanner.next();
				accountType = scanner.next();
				balance = scanner.next();
				newBalance = Double.valueOf(balance);
				System.out.println(username);
				if(username.equals(name)) {
					System.out.println("User = true");
					if(accountType.equals("Checking")) {
						balance = Double.toString(newBalance + input);
						if(newBalance + input < 0) {
							balance = "0";
						}
						pw.println(username + "," + accountType + "," + balance);
					}
					else {
						pw.println(username + "," + accountType + "," + balance);
					}
				}
				else {
					pw.println(username + "," + accountType + "," + balance);
				}
			}
			scanner.close();
			pw.flush();
			pw.close();
			
				fw = new FileWriter(LoginController.filepath2, false);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				
				scanner = new Scanner(new File(updateFile));
				scanner.useDelimiter("[,\n]");
				
				while(scanner.hasNext()) {
					username = scanner.next();
					accountType = scanner.next();
					balance = scanner.next();
					System.out.println(username);
					pw.println(username + "," + accountType + "," + balance);
					
				}
				scanner.close();
				pw.flush();
				pw.close();
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Read failed");
		}
		
	}
	static String filepath = "accounts.txt";
	public static void writeSavings(double input) {
		String updateFile = "update.txt";
		File oldFile = new File(filepath);
		File newFile = new File(updateFile);
		String username = "";
		String accountType = "";
		String balance = "";
		double newBalance = 0;
		try {
			FileWriter fw = new FileWriter(updateFile, false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			scanner = new Scanner(new File(filepath));
			scanner.useDelimiter("[,\n]");
			
			while(scanner.hasNext()) {
				username = scanner.next();
				accountType = scanner.next();
				balance = scanner.next();
				newBalance = Double.valueOf(balance);
				System.out.println(username);
				if(username.equals(name)) {
					System.out.println("User = true");
					if(accountType.equals("Savings")) {
						balance = Double.toString(newBalance + input);
						if(newBalance + input < 0) {
							balance = "0";
						}
						pw.println(username + "," + accountType + "," + balance);
					}
					else {
						pw.println(username + "," + accountType + "," + balance);
					}
				}
				else {
					pw.println(username + "," + accountType + "," + balance);
				}
			}
			scanner.close();
			pw.flush();
			pw.close();
			
				fw = new FileWriter(LoginController.filepath2, false);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				
				scanner = new Scanner(new File(updateFile));
				scanner.useDelimiter("[,\n]");
				
				while(scanner.hasNext()) {
					username = scanner.next();
					accountType = scanner.next();
					balance = scanner.next();
					System.out.println(username);
					pw.println(username + "," + accountType + "," + balance);
					
				}
				scanner.close();
				pw.flush();
				pw.close();
			
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Read failed");
		}
	}
	
	@FXML
	private void tabChanged() {
		// Tab pane gets initialized before virtual helper pane, so first call of this would throw null pointer
		if(this.helperText==null) return;
		
		for(Tab tab : this.tabPane.getTabs()) {
			if(tab.isSelected()) {
				virtHelp.handleEvent(this.helpToggle, this.helperText, tab.getText());
				return;
			}
		}
	}
	
	@FXML
	private void signoutClicked() {
		Stage s = (Stage)(signoutButton.getScene().getWindow());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
		
		Scene scene = null;
		try {
			scene = new Scene(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.setScene(scene);
	}
	
	@FXML
	private void depositOnClick() {
		balanceValue.setText(""+presenter.deposit(currentAccount, name, 20));
		if(currentAccount == 2) {
			writeChecking(20);
		}
		if(currentAccount == 1) {
			writeSavings(20);
		}
		transactionHistory.setText(presenter.fetchTransactionHistory(currentAccount, name));
	}
	
	@FXML
	private void withdrawOnClick() {
		balanceValue.setText(""+presenter.withdraw(currentAccount, name, 20));
		if(currentAccount == 2) {
			writeChecking(-20);
		}
		if(currentAccount == 1) {
			writeSavings(-20);
		}
		transactionHistory.setText(presenter.fetchTransactionHistory(currentAccount, name));
	}
	
	@FXML
	private void virtHelpToggled() { // this should update the user's choice in the database
		this.helpToggle = !this.virtHelpToggle.isSelected();
		
		this.virtHelpToggle.setText((this.helpToggle) ?"On":"Off");
		
		this.virtHelp.handleEvent(this.helpToggle, this.helperText, this.virtHelpToggle.getText());
	}
}
