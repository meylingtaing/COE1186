/**
 * On Track Trainwreck
 * Track.java
 * Purpose: holds main program to start application for Track Model GUI
 * hold static variables that are accessed throughout project
 * 
 * @author Sarah Bunke
 * @version 2.0 12/12/13
 * 
 */
package trackModel;

import java.util.Hashtable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Track extends Application {
	//static variables used throughout track model
	protected static Stage trackStage; //holds main GUI
	public static Hashtable<String, TrackObject> trackArray = new Hashtable<String, TrackObject>(); //holds track objects which contrain block DB's
	protected static ObservableList<String> trackListData = FXCollections.observableArrayList(); //contains list of current tracks

	/**
	 * Main program to start application
	 * @param args
	 */
	public static void main(String[] args){
		
		Application.launch(Track.class, args);
	}
	
	/**
	 * override method that is called to open GUI
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

			AnchorPane page = (AnchorPane) FXMLLoader.load(Track.class.getResource("GUI.fxml"));
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Track Model");
			primaryStage.show();


		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
