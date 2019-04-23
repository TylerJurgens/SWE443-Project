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
	public static File filepath2 = new File("accounts.txt");
	private static String filepath3 = "helpertoggle.txt";
	
	@FXML
	private void signupClicked() {
		if(readUser(this.usernameSignupField.getText())) {
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
			saveUser(this.usernameSignupField.getText(), this.passwordSignupField.getText());
			this.authErrorSignupLabel.setVisible(false);
			this.authError2SignupLabel.setVisible(false);
			this.authSuccessSignupLabel.setVisible(true);
			
			// Should add username and password to database here
		}
	}
	
	private static void saveUser(String username, String password) {
		try {
			FileWriter fw = new FileWriter(filepath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter pw = new PrintWriter(bw);
			
			pw.println(username+","+password);
			pw.flush();
			pw.close();
			
			fw = new FileWriter(filepath2, true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			
			pw.println(username+","+"Checking"+","+"0");
			pw.println(username+","+"Savings"+","+"0");
			pw.flush();
			pw.close();
			
			fw = new FileWriter(filepath3, true);
			bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);
			
			pw.println(username+",1");
			pw.flush();
			pw.close();
			
			JOptionPane.showMessageDialog(null, "Record saved");
			
		}
		catch(Exception E) {
			System.out.println("saveuser()");
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
				if(username.equals(user)) {
					foundUser = true;
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
				//System.out.println("u: "+username);
				//System.out.println("p: "+password);
				if(username.equals(user)) {
					foundUser = true;
					if(password.substring(0,password.length()-1).equals(pass)) {
						correctPassword = true;
					}
				}
				if(foundUser && correctPassword) {
					ans = true;
				}
			}
		}
		catch(Exception e) {
			System.out.println("readuserandpass()");
			JOptionPane.showMessageDialog(null, "Read failed");
		}
		return ans;
		
	}
	
	@FXML
	private void loginClicked() {
		
		String username = this.usernameLoginField.getText();
		if(readUserAndPass(username, this.passwordLoginField.getText())) {
		// This if statement should check the username and password against the database
			Stage s = (Stage)(loginButton.getScene().getWindow());
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PrimaryScreen.fxml"));
			Scene scene = null;
			try {
				scene = new Scene(loader.load());
			} catch (IOException e) {
				e.printStackTrace();
			}
			s.setScene(scene);
			
			PrimaryController cont = loader.<PrimaryController>getController();
			cont.setUser(username);
			
		} else {
			this.authErrorLoginLabel.setVisible(true);
		}
	}
}
