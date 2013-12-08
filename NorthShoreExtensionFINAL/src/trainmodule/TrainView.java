package trainmodule;

import java.util.Stack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TrainView extends Application
{	
	protected static ViewController viewController;
	
	public TrainView()
	{
		
	}
	
	public static void createGUI()
	{
		launch();
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			FXMLLoader fxml = new FXMLLoader();
			fxml.setLocation(getClass().getResource("TrainGUI.fxml"));
			Parent root = (Parent) fxml.load();
            
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Train Model");
            primaryStage.show();
        }
		catch (Exception ex) 
        {
            ex.printStackTrace();
        }
	}
}

