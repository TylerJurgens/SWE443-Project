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
	
	private String currentAccount;
	
	public void initialize() {
		currentAccount = null;
		
	    accountSelection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	      @Override public void changed(ObservableValue<? extends String> selected, String oldAccount, String newAccount) {
	    	  currentAccount = newAccount;
	    	  System.out.println(currentAccount);
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
