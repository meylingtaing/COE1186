package trainmodule;

import java.util.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TrainView extends Application
{	
	public static void main(String[] args)
	{
		ArrayList<TrainModel> list = new ArrayList<TrainModel>();
		Stack<String> temp = new Stack<String>();
		temp.push("Hazelwood");
		temp.push("Oakland");
		temp.push("Downtown");
		
		list.add(new TrainModel(1, new Route(temp), 70.2, "SUPERUSER"));
		list.add(new TrainModel(1, new Route(temp), 69.2, "ENGINEER"));
		list.add(new TrainModel(1, new Route(temp), 67.4, "?????????"));
		
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

