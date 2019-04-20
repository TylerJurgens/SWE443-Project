import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private Button loginButton;
	@FXML
	private TextField usernameLoginField;
	@FXML
	private TextField passwordLoginField;
	@FXML
	private Label authErrorLoginLabel;
	@FXML
	private Button signupButton;
	@FXML
	private TextField usernameSignupField;
	@FXML
	private TextField passwordSignupField;
	@FXML
	private Label authErrorSignupLabel;
	@FXML
	private Label authError2SignupLabel;
	@FXML
	private Label authSuccessSignupLabel;
	
	
	@FXML
	private void signupClicked() {
		if(this.usernameSignupField.getText().equalsIgnoreCase("bob")) {
			// Username already in use
			this.authSuccessSignupLabel.setVisible(false);
			this.authError2SignupLabel.setVisible(false);
			this.authErrorSignupLabel.setVisible(true);
		} else if(this.usernameSignupField.getText().length()==0 || this.passwordSignupField.getText().length()==0){
			// Empty username or password field
			this.authErrorSignupLabel.setVisible(false);
			this.authSuccessSignupLabel.setVisible(false);
			this.authError2SignupLabel.setVisible(true);
		} else {
			// Successful signup
			this.authErrorSignupLabel.setVisible(false);
			this.authError2SignupLabel.setVisible(false);
			this.authSuccessSignupLabel.setVisible(true);
			
			// Should add username and password to database here
		}
	}
	
	@FXML
	private void loginClicked() {
		System.out.println("Username: " + this.usernameLoginField.getText());
		System.out.println("Password: " + this.passwordLoginField.getText());
		
		// This if statement should check the username and password against the database
		if(this.usernameLoginField.getText().equalsIgnoreCase("bob") && this.passwordLoginField.getText().equals("pw")) {
			Stage s = (Stage)(loginButton.getScene().getWindow());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PrimaryScreen.fxml"));
			
			Scene scene = null;
			try {
				scene = new Scene(loader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			s.setScene(scene);
		} else {
			this.authErrorLoginLabel.setVisible(true);
		}
	}
}
