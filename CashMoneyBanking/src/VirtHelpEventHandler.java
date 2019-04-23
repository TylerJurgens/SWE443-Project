import javafx.scene.control.Label;

public class VirtHelpEventHandler {

	public void handleEvent(boolean helperOn, Label virtHelper, String compText) {
		
		// Virtual Helper Label is set to give advice about whichever component was clicked
		if(!helperOn) {
			virtHelper.setText("The helper has been turned off.");
		} else if(compText.equals("On")) {
			virtHelper.setText("Useful Information about clicked features of our banking application will appear here (if turned on).");
		} else if(compText.equals("Accounts")) {
			virtHelper.setText("The Accounts tab displays all information about your accounts.");
		} else if(compText.equals("Settings")) {
			virtHelper.setText("In the settings tab, you can view and modify any of your personal information. You can also sign out of your account from here.");
		} else if(compText.equals("Choose your Account")) {
			virtHelper.setText("This drop down menu allows you to choose which of your bank accounts you are currently interacting with.");
		} else if(compText.equals("Balance:")) {
			virtHelper.setText("In this box you can specify the amount you with to deposit or withdraw.");
		} else if(compText.equals("Make Deposit")) {
			virtHelper.setText("Clicking this button deposits the specified amount into the current account.");
		} else if(compText.equals("Make Withdrawal")) {
			virtHelper.setText("Clicking this button withdraws the specified amount from the current account.");
		} else if(compText.equals("Transaction History")) {
			virtHelper.setText("This pane lists the transaction history for your current session.");
		} else if(compText.equals("Password")) {
			virtHelper.setText("This box allows you to enter a new password.");
		} else if(compText.equals("Update Password")) {
			virtHelper.setText("Clicking this button will update your password to whatever is in the above box.");
		} else if(compText.equals("Sign Out")) {
			virtHelper.setText("Clicking this button will sign you out of your account.");
		} else {
			virtHelper.setText("Unhandled Situation");
		}
	}
}
