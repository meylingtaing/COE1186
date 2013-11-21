package ctc;

import trainModel.TrainModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTrainFormController extends FormController {

	@FXML private TextField trainNameInput;
	
	private boolean addTrain(String trainName)
	{
		
		TrainModel newTrain = new TrainModel(trainName, CTC.transitSystem.trains.size());
		CTC.transitSystem.trains.put(trainName, newTrain);
		CTC.trains.add(trainName);
		return true;
	}
	
	@Override
	@FXML protected void submit() 
	{
		String trainName = trainNameInput.getText();
		if (addTrain(trainName)) 
		{
			Stage currStage = (Stage) trainNameInput.getScene().getWindow();
			currStage.close();
			CTC.ctcController.displayTrack();
			CTC.ctcController.displayLegend();
		}
		else
			errorMessage.setVisible(true);
	}

}
