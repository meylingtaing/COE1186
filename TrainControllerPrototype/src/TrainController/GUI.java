package TrainController;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUI extends Application  {
	
	GUIController guicontroller = null;
	public TrainController TC;
	Thread CThread = null;
	
	public static void main(String[] args) {
		GUI gui = new GUI();
		launch(args);
	}
	
	public GUI() {
		TC = new TrainController();
		CThread = new Thread(TC);
		CThread.start();
	}

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Pane myPane = (Pane) fxmlLoader.load(getClass().getResource("TrainControllerUI.fxml").openStream());
		guicontroller = (GUIController) fxmlLoader.getController();
		guicontroller.parentGUI = this;
		guicontroller.TC = this.TC;
		TC.guic = guicontroller;
		Scene myScene = new Scene(myPane);
		stage.setScene(myScene);
		stage.setTitle("Train Controller Prototype");
		stage.show();
	}
	
}
