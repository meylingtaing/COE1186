/**
 * Controller for adding trains
 */
package ctc;

import TrainController.TrainController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTrainFormController extends FormController 
{
	// Keeps track of all of the trains
	private static int trainId = 1;
	
	private static final boolean DEBUG = false;
	
	// GUI stuff
	@FXML private TextField trainNameInput; 
	
	@Override
	@FXML protected void submit() 
	{
		String trainName = trainNameInput.getText();
		
		if (addTrain(trainName, ctcOffice))
		{
			Stage currStage = (Stage) trainNameInput.getScene().getWindow();
			currStage.close();
			ctcOffice.ctcController.displayTrack();
			ctcOffice.ctcController.displayLegend();
		}
		else
			errorMessage.setVisible(true);

	}
	
	/**
	 * Adds a train to the CTC's database and the transit system
	 * @param trainName
	 * @param ctc
	 * @return
	 */
	public static boolean addTrain(String trainName, CTC ctc)
	{
		// Modifying name so it's easier to deal with
		trainName = trainName.trim();
		trainName = trainName.replace(" ", "");
		
		try
		{
			TrainController newTrain = new TrainController(trainId);
			if (DEBUG)
			{
				System.out.println(newTrain.getModel().getTrainID());
			}
			
			// Add train to transit system
			ctc.transitSystem.ctcAddTrain(newTrain);
			
			// Add to CTC's train database
			ctc.trains.put(trainName, trainId);
			if (DEBUG)
				System.out.println("Added train: " + trainId);
			
			trainId++;
			return true;
		}
		catch (Exception e)
		{
			System.err.println("Can't add a train");
			return false;
		}
	}

}
