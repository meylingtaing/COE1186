package ctc;

import trainmodule.TrainModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddTrainFormController extends FormController {

	@FXML private TextField trainNameInput;
	
	private boolean addTrain(String trainName)
	{
		int id = CTC.transitSystem.trains.size();
		trainName = id + " " + trainName;
		TrainModel newTrain = new TrainModel(CTC.transitSystem.trains.size());
		CTC.transitSystem.trains.put(id, newTrain);
		
		System.out.println("All of the trains:");
		for (TrainModel train : CTC.transitSystem.trains.values())
		{
			System.out.println("Train " + train.getTrainID());
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
