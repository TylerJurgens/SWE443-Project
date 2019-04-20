import javafx.scene.control.Label;

public class VirtHelpEventHandler {

	public void handleEvent(Label virtHelper, String compText) {
		
		// Virtual Helper Label is set to give advice about whichever component was clicked
		
		if(compText.equals("Accounts")) {
			virtHelper.setText("The Accounts tab displays all information about your accounts.");
		} else if(compText.equals("Settings")) {
			virtHelper.setText("In the settings tab, you can view and modify any of your personal information. You can also sign out of your account from here.");
		}
	}
}
