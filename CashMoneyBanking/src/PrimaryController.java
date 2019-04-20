import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class PrimaryController {
	
	private VirtHelpEventHandler virtHelp = new VirtHelpEventHandler();

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
	private void tabChanged() {
		// Tab pane gets initialized before virtual helper pane, so first call of this would throw null pointer
		if(this.helperText==null) return;
		
		for(Tab tab : this.tabPane.getTabs()) {
			if(tab.isSelected()) {
				virtHelp.handleEvent(this.helperText, tab.getText());
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
}
