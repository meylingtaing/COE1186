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
	protected static Stage trackStage;
	public static Hashtable<String, TrackObject> trackArray = new Hashtable<String, TrackObject>();
	protected static ObservableList<String> trackListData;

	//main program
	public static void main(String[] args){
		trackListData = FXCollections.observableArrayList();
		Application.launch(Track.class, args);
	}
	
	//Start function to open main GUI
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
