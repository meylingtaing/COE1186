/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

import java.util.Stack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class creates the GUI
 */
public class TrainView extends Application implements Runnable
{	
	protected static ViewController viewController;		//This holds the view controller
	
	/**
	 * This is the GUI constructor
	 */
	public TrainView()
	{
		
	}
	
	/**
	 * This method launches the GUI
	 */
	public void createGUI()
	{
		launch();
	}
	
	/**
	 * This method initializes the GUI and starts it
	 */
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

	@Override
	public void run() {
		launch();
	}
}

