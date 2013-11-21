/**
 * The main CTC class
 * This also loads the gui
 * 
 * @author meyling
 */
package ctc;

import java.util.ArrayList;

import nse.TransitSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CTC extends Application 
{
	protected static Stage ctcStage;
	public static CTCController ctcController;
	
	protected static TransitSystem transitSystem;
	protected static User currUser;
	protected static ArrayList<TrackLayout> tracks;
	protected static ArrayList<String> trains;
	
	private String loginGui = "loginForm.fxml";
	
	/**
	 * Initializes everything for CTC
	 * 
	 * @param transitSystem
	 */
	public CTC(TransitSystem transitSystem)
	{
		CTC.transitSystem = transitSystem;
		tracks = new ArrayList<TrackLayout>();
		trains = new ArrayList<String>();
		ctcStage = new Stage();
		currUser = null;
	}

	@Override
	public void start(Stage loginStage) throws Exception 
	{
		// Set up and show log in
		Parent root = FXMLLoader.load(getClass().getResource(loginGui));
		loginStage.setScene(new Scene(root));
		loginStage.setTitle("CTC Login");
		loginStage.show();

	}

}
