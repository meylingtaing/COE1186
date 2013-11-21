package ctc;

import TrainController.TrainController;
import trainmodule.TrainModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTrainFormController extends FormController {

	@FXML private TextField trainNameInput;
	
	private boolean addTrain(String trainName)
	{
		
		
		TrainController newTrain = new TrainController();
		int id = newTrain.model.getTrainID();
		System.out.println("Train ID is: " + id);
		trainName = id + " " + trainName;
		CTC.transitSystem.trains.put(id, newTrain);
		
		System.out.println("All of the trains:");
		for (TrainController train : CTC.transitSystem.trains.values())
		{
			System.out.println("Train " + train.model.getTrainID());
		}
		
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
