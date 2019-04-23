import java.io.IOException;

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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
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
	private Label balanceLabel, balanceValue, transactionHistory, errorLabel;
	@FXML
	private ScrollPane transactionHistoryPane;
	@FXML
	private Button depositButton, withdrawButton;
	@FXML
	private TextField depositField;
	
	@FXML
	private Label settings_nameLabel;
	@FXML
	private TextField settings_nameField;
	@FXML
	private Label settings_passwordLabel;
	@FXML
	private TextField settings_passwordField;
	@FXML
	private Button settings_update;
	
	//Holds the ID of the current displayed account
	private int currentAccount;
	//Presenter class for MVP architecture
	private MVPresenter presenter;
	//Name of the currently logged in user
	private String name;
	
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
	    				balanceValue.setText("" + presenter.fetchBalance(currentAccount, "bob"));
	    				transactionHistory.setText(presenter.fetchTransactionHistory(currentAccount, "bob"));
	    			}
	    			
	    		}
	    });
	}
	
	public void setUser(String user) {
		this.name = user;
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
		if(this.currentAccount != -1)
		{
		double amount = 0;
		boolean valid = true;
		try {
			amount = Double.parseDouble(depositField.getText());
		} catch (Exception e){
			//errorLabel.setTextFill(Color.RED);
			//errorLabel.setText("Invalid value given for deposit: Not a number");
			JOptionPane.showMessageDialog(null, "Invalid value given for deposit: Not a number");
			valid = false;
		}
		if(valid && amount <= 0)
		{
			//errorLabel.setTextFill(Color.RED);
			//errorLabel.setText("Invalid value given for deposit: Please enter a number greater than 0");
			JOptionPane.showMessageDialog(null, "Invalid value given for deposit: Please enter a number greater than 0");
		}
		else if(valid) {
			balanceValue.setText(""+presenter.deposit(currentAccount, name, amount));
			transactionHistory.setText(presenter.fetchTransactionHistory(currentAccount, name));

			JOptionPane.showMessageDialog(null, "Deposit successfully completed");
		}
		}else
			JOptionPane.showMessageDialog(null, "Please select an account first");
	}
	
	@FXML
	private void withdrawOnClick() {
		if(this.currentAccount != -1) {
		double amount = 0;
		boolean valid = true;
		try {
			amount = Double.parseDouble(depositField.getText());
		} catch (Exception e){
			//errorLabel.setTextFill(Color.RED);
			//errorLabel.setText("Invalid value given for withdrawal: Not a number");
			JOptionPane.showMessageDialog(null, "Invalid value given for withdrawal: Please enter a number greater than 0");
			valid = false;
		}
		if(valid && amount <= 0)
		{
			//errorLabel.setTextFill(Color.RED);
			//errorLabel.setText("Invalid value given for withdrawal: Please enter a number greater than 0");
			JOptionPane.showMessageDialog(null, "Invalid value given for withdrawal: Please enter a number greater than 0");
		}
		else if(valid) {
			amount = presenter.withdraw(currentAccount, name, amount);
			if(amount >= 0)
			{
				balanceValue.setText(""+amount);
				transactionHistory.setText(presenter.fetchTransactionHistory(currentAccount, name));
				//errorLabel.setTextFill(Color.GREEN);
				//errorLabel.setText("Withdrawal successfully completed");
				JOptionPane.showMessageDialog(null, "Withdrawal successfully completed");
			}else if(amount == -2)
			{
				//errorLabel.setTextFill(Color.RED);
				//errorLabel.setText("Invalid value given for withdrawal: Not enough funds to withdraw");
				JOptionPane.showMessageDialog(null, "Withdrawal failed: Not enough funds to withdraw");
			}
		}
		}else
			JOptionPane.showMessageDialog(null, "Please select an account first");
	}
	
	@FXML
	private void virtHelpToggled() { // this should update the user's choice in the database
		this.helpToggle = !this.virtHelpToggle.isSelected();
		
		this.virtHelpToggle.setText((this.helpToggle) ?"On":"Off");
		
		this.virtHelp.handleEvent(this.helpToggle, this.helperText, this.virtHelpToggle.getText());
	}
}
