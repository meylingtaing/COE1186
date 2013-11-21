package nse;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TransitSystemGui extends Application 
{
	
	public static void main(String[] args) throws IOException
	{
		Application.launch(TransitSystemGui.class, args);
	}

	@Override
	public void start(Stage stage) throws Exception 
	{
		// Display the main GUI that links to all of the individual module interfaces
		Parent root = FXMLLoader.load(getClass().getResource("MainGui.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("North Shore Extension");
		stage.show();
	}

}