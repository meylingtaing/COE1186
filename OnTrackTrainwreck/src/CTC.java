/**
 *	Meyling Taing
 *	Some skeleton code for a JavaFX main application
 */

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CTC extends Application {
	
	public static void main(String[] args) throws IOException{
		Application.launch(CTC.class, args);
	}
	
	@Override
	public void start(Stage ctcStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("CTC.fxml"));
		
		ctcStage.setScene(new Scene(root));
		ctcStage.setTitle("Trains and stuff");
		ctcStage.show();
	}
}