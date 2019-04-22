import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
	
	private static String filepath = "users.txt";
	
	@FXML
	private void signupClicked() {
		if(readUser(this.usernameSignupField.getText())) {
			this.authErrorSignupLabel.setVisible(true);
		}
		else {
			saveUser(this.usernameSignupField.getText(), this.passwordSignupField.getText());
		}
	}
	
	private static void saveUser(String username, String password) {
		try {
			FileWriter fw = new FileWriter(filepath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(username+", "+password);
			pw.flush();
			pw.close();
			
			JOptionPane.showMessageDialog(null, "Record saved");
			
		}
		catch(Exception E) {
			JOptionPane.showMessageDialog(null, "Record not saved");
		}
	}
	private static Scanner scanner;
	private static boolean readUser(String user) {
		boolean foundUser = false;
		boolean ans = false;
		String username = "";
		try {
			scanner = new Scanner(new File(filepath));
			scanner.useDelimiter("[,\n]");
				
			while(scanner.hasNext() && !foundUser) {
				username = scanner.next();
				scanner.next();
				System.out.println(username);
				if(username.equals(user)) {
					foundUser = true;
					System.out.println("User = true");
				}
				if(foundUser) {
					ans = true;
				}
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Read failed");
		}
		return ans;
		
	}
	private static boolean readUserAndPass(String user, String pass) {
		boolean foundUser = false;
		boolean correctPassword = false;
		boolean ans = false;
		String username = "_";
		String password = "_";
		try {
			scanner = new Scanner(new File(filepath));
			scanner.useDelimiter("[,\n]");
				
			while(scanner.hasNext() && !foundUser && !correctPassword) {
				username = scanner.next();
				password = scanner.next();
				System.out.println(username);
				System.out.println(password);
				if(username.equals(user)) {
					foundUser = true;
					System.out.println("User = true");
					if(password.equals(pass)) {
						correctPassword = true;
						System.out.println("Pass = true");
					}
				}
				if(foundUser) {
					ans = true;
				}
			}
		}
		catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Read failed");
		}
		return ans;
		
	}
	
	@FXML
	private void loginClicked() {
		System.out.println("Username: " + this.usernameLoginField.getText());
		System.out.println("Password: " + this.passwordLoginField.getText());
		
		if(readUserAndPass(this.usernameLoginField.getText(), this.passwordLoginField.getText())) {
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
