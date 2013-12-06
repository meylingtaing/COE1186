/**
 * So as far as CTC goes, it's a model that has stuff
 */
package ctc;

import java.util.Hashtable;

import nse.TransitSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CTC extends Application 
{
	// In the OBJECT of the CTC, what it has, not GUI related
	protected TransitSystem transitSystem;
	protected User currUser;
	protected Hashtable<String, TrackLayout> tracks;
	protected Hashtable<String, Integer> trains;
	protected Hashtable<String, Route> routes;
	
	// GUI related fields
	protected Stage ctcStage;
	private String loginGui = "loginForm.fxml";
	private String ctcGui = "CTCView.fxml";
	
	// Controller -- TODO: change this back to protected
	public CTCController ctcController;
	
	/**
	 * Initializes everything
	 * 
	 * @param transitSystem
	 */
	public CTC(TransitSystem transitSystem)
	{
		this.transitSystem = transitSystem;
		currUser = null;
		ctcController = null;
		
		tracks = new Hashtable<String, TrackLayout>();
		trains = new Hashtable<String, Integer>();
		routes = new Hashtable<String, Route>();
	}

	/**
	 * Login popup form shows up when CTC is started
	 */
	@Override
	public void start(Stage loginStage) throws Exception 
	{
		// Set up the login controller
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(loginGui));
		Parent root = (Parent) fxmlLoader.load();
		((LoginController)fxmlLoader.getController()).setContext(this);
		
		// Displaying the login
		loginStage.setScene(new Scene(root));
		loginStage.setTitle("CTC Login");
		loginStage.show();
	}
	
	/**
	 * Load the main ctc office stage
	 * 
	 * @param mainStage
	 * @throws Exception
	 */
	public void startMain() throws Exception
	{
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource(ctcGui));
		Parent root = (Parent) fxmlLoader.load();
		((CTCController)fxmlLoader.getController()).setContext(this);
		ctcStage = new Stage();
		ctcStage.setScene(new Scene(root));
		ctcStage.setTitle("North Shore Extension");
		ctcStage.show();
	}

}
