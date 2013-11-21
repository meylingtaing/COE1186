package nse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	public static TransitSystem transitSystem = new TransitSystem();
	protected Thread myThread = new Thread(transitSystem);
	
	public void initialize()
	{

	}
	
	@FXML private void navigateCTC() throws Exception 
	{
		Stage newStage = new Stage();
		transitSystem.ctc.start(newStage);
	}
	
	@FXML private void simulate(ActionEvent event)
	{
		Button buttonClicked = (Button) event.getSource();
		if (buttonClicked.getText().equals("Simulate"))
		{
			myThread.start();
			buttonClicked.setText("Stop Simulation");
			buttonClicked.setDisable(true);
		}
		else
		{
			myThread.interrupt();
			buttonClicked.setText("Simulate");
		}
	}
}
