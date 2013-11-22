package trainmodule;

import java.util.Stack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
            AnchorPane page = (AnchorPane) fxml.load(TrainModel.class.getResource("/trainmodule/TrainGUI.fxml"));
            Scene scene = new Scene(page);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Train Model");
            primaryStage.show();
        }
		catch (Exception ex) 
        {
            ex.printStackTrace();
        }
	}
}

