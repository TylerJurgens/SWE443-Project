import java.io.IOException;



import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class PrimaryController {

	@FXML
	private Button signoutButton;
	@FXML
	private Label helperText;
	@FXML
	private ComboBox<String> accountSelection;
	
	//Holds the ID of the current displayed account
	private int currentAccount;
	//View class for MVP architecture
	private MVPView view;
	
	private int[] accountIDs;
	
	
	public void initialize() {
		currentAccount = -1;
		view = new MVPView();
		accountIDs = view.fetchAccounts("bob");
		
		
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
	    				System.out.println(view.fetchBalance(currentAccount, "bob"));
	    			}
	    			
	    		}
	    });
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
}
