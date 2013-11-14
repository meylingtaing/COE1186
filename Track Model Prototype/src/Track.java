

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
	protected static Stage trackStage;
	protected static Hashtable<String, TrackObject> trackArray = new Hashtable<String, TrackObject>();;
	protected static ObservableList<String> trackListData;

	public static void main(String[] args){
		trackListData = FXCollections.observableArrayList();
		Application.launch(Track.class, args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {


			// This line to resolve keys against Bundle_fr_FR.properties
			//        ResourceBundle i18nBundle = ResourceBundle.getBundle("helloi18n.Bundle", new Locale("fr", "FR"));
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
