import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
		primaryStage.setTitle("Ca$h Money Banking");
		primaryStage.setScene(new Scene(root, 600, 400));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
