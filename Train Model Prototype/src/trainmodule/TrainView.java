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
	
	/*public static void main(String[] args)
	{	
		launch(args);
	}*/
	
	public TrainView()
	{
		//launch();
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
            //viewController =  (ViewController) fxml.getController();           
            Scene scene = new Scene(page);
            //viewController.addTrain(new TrainModel(new Route(new Stack<String>()), 70.2, "SUPERUSER"));
            
            //TrainModel.setViewController(viewController);
            //System.out.println("initialized vc");
            primaryStage.setScene(scene);
            primaryStage.setTitle("Train Model");
            primaryStage.show();
            //TrainModel.refreshGUI();
        }
		catch (Exception ex) 
        {
            ex.printStackTrace();
        }
	}
}

