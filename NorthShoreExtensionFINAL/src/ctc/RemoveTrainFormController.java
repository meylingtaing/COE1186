/**
 * Controller for removing a train
 */
package ctc;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class RemoveTrainFormController extends FormController 
{
	@FXML private ComboBox<String> trainListBox;
	
	/**
	 * Initializes form input with the tracks
	 */
	public void initialize()
	{
		if (ctcOffice != null)
		{
			// Get list of all trains
			for (String trainName : ctcOffice.trains.keySet())
			{
				String trainString = "#" + ctcOffice.trains.get(trainName) + " " + trainName;
				trainListBox.getItems().add(trainString);
			}
		}
	}

	/**
	 * Submits a train for removal
	 */
	@Override
	@FXML protected void submit() 
	{
		String selectedTrain = trainListBox.getValue();
		
		if (selectedTrain != null)
		{
			// de-manipulate string
			String[] stringParts = selectedTrain.split(" ");
			int trainId = Integer.parseInt(stringParts[0].substring(1));
			
			// Remove from transit system
			ctcOffice.transitSystem.ctcRemoveTrain(trainId);
			
			// Remove from CTC's train database
			ctcOffice.trains.remove(stringParts[1]);
		}
		
		// Close window
		Stage currStage = (Stage) trainListBox.getScene().getWindow();
		currStage.close();
		
		// Update displays
		ctcOffice.ctcController.displayTrack();
		ctcOffice.ctcController.displayLegend();
	}

}
