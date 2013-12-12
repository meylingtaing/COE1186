package nse;
import trainmodule.TrainView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController {
	public static TransitSystem transitSystem = new TransitSystem();
	public static boolean simulatedMain = false;
	protected Thread myThread = new Thread(transitSystem);
	//public static boolean threadSuspended = true;
	
	@FXML private TextField speedInput;
	
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
		// Change speed
		try 
		{
			Double tickRate = Double.parseDouble(speedInput.getText());
			transitSystem.tickRate = tickRate;
		}
		catch (Exception e) 
		{
			// Default is to run at wall clock speed
			transitSystem.tickRate = 1.0;
		}
		
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
			synchronized(transitSystem) 
			{
				transitSystem.notify();
			}
		}
	}
	
}
