package nse;
import trainmodule.TrainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {
	public static TransitSystem transitSystem = new TransitSystem();
	public static boolean simulatedMain = false;
	protected Thread myThread = new Thread(transitSystem);
	//public static boolean threadSuspended = true;
	
	public void initialize()
	{
		myThread.start();
		transitSystem.simulated = false;
		simulatedMain = false;
	}
	
	@FXML private void navigateCTC() throws Exception 
	{
		Stage newStage = new Stage();
		transitSystem.ctc.start(newStage);
	}
	
	@FXML private void TrainGUI() throws Exception 
	{
		TrainView train = new TrainView();
		train.start(new Stage());
	}
	
	@FXML private void simulate(ActionEvent event)
	{
		Button buttonClicked = (Button) event.getSource();
		if (buttonClicked.getText().equals("Simulate"))
		{
			
			buttonClicked.setText("Stop Simulation");
			//buttonClicked.setDisable(true);
			transitSystem.simulated = true;
			simulatedMain = true;
			
		}
		else
		{
			//myThread.interrupt();
			buttonClicked.setText("Simulate");
			transitSystem.simulated = false;
			simulatedMain = false;
		}
		
		if (transitSystem.simulated)
		{
			synchronized(transitSystem) {
				transitSystem.notify();
			}
		}
	}
	
}
