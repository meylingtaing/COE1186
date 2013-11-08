/**
 * Java FX skeleton code
 * This is used to start the application
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Model extends Application 
{
	public static void main(String[] args) 
	{
		Application.launch(Model.class, args);
	}

	public void start(Stage stage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));

		stage.setTitle("Welcome");
		stage.setScene(new Scene(root, 640, 480)); // Width and height are optional
		stage.show();
	}
}