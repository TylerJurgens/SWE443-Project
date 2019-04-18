import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private Button loginButton;
	@FXML
	private TextField usernameField;
	@FXML
	private TextField passwordField;
	@FXML
	private AnchorPane ap;
	
	@FXML
	private void LoginClicked() {
		System.out.println("Username: " + this.usernameField.getText());
		System.out.println("Password: " + this.passwordField.getText());
		
		Stage s = (Stage)(loginButton.getScene().getWindow());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PrimaryScreen.fxml"));
		
		Scene scene = null;
		try {
			scene = new Scene(loader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		s.setScene(scene);
	}
}
