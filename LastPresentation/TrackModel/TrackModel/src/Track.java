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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;


public class Track extends Application {
	protected static Stage trackStage;//holds main GUI
	protected static Hashtable<String, TrackObject> trackArray = new Hashtable<String, TrackObject>(); //holds track objects which contrain block DB's
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
