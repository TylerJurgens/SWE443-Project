import java.io.IOException;



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
		this.accountIDs = presenter.fetchAccounts("bob");
		
		
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
		balanceValue.setText(""+presenter.deposit(currentAccount, "bob", 20));
		transactionHistory.setText(presenter.fetchTransactionHistory(currentAccount, "bob"));
	}
	
	@FXML
	private void withdrawOnClick() {
		balanceValue.setText(""+presenter.withdraw(currentAccount, "bob", 20));
		transactionHistory.setText(presenter.fetchTransactionHistory(currentAccount, "bob"));
	}
	
	@FXML
	private void virtHelpToggled() { // this should update the user's choice in the database
		this.helpToggle = !this.virtHelpToggle.isSelected();
		
		this.virtHelpToggle.setText((this.helpToggle) ?"On":"Off");
		
		this.virtHelp.handleEvent(this.helpToggle, this.helperText, this.virtHelpToggle.getText());
	}
}
