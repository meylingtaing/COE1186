/**
 *	Meyling Taing
 *	Some skeleton code for a JavaFX main application
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CTC extends Application {
	protected static Track[] tracks;
	protected static int numTracks = 0;
	protected static ArrayList<Train> trains;
	protected static int numTrains = 0;
	protected static Stage ctcStage;
	protected static User currUser;
	
	private static String title = "North Shore Extension";
	private String loginFile = "loginForm.fxml";
	
	public static void main(String[] args) throws IOException{
		Application.launch(CTC.class, args);
	}
	
	@Override
	public void start(Stage stage) throws IOException {
		 
		// Initialize data
		tracks = new Track[4];
		currUser = null;
		trains = new ArrayList<Train>();
		
		// Set up the main stage
		ctcStage = new Stage();
		refreshStage();
		
		// Set up login
		Parent loginRoot = FXMLLoader.load(new File(loginFile).toURI().toURL());
		stage.setTitle("Log in");
		stage.setScene(new Scene(loginRoot));
		stage.show();
	}
	
	protected static void refreshStage() {
		Parent mainController = new CTCMainController();
		ctcStage.setScene(new Scene(mainController));
		ctcStage.setTitle(title);
		ctcStage.show();
	}
}