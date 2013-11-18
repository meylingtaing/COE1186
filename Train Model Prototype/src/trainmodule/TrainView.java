package trainmodule;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TrainView extends Application
{	
	
	public static void main(String[] args)
	{	
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
            AnchorPane page = (AnchorPane) FXMLLoader.load(TrainModel.class.getResource("/trainmodule/TrainGUI.fxml"));
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

