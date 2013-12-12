package ctc;

import java.util.LinkedList;

import trackModel.Block;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ScheduleTrainFormController extends FormController 
{
	@FXML private ComboBox<String> stationListBox, trainListBox;
	@FXML private TextField dwellTimeInput;
	
	public void initialize()
	{
		if (ctcOffice != null)
		{
			// Add list of trains that have a route
			for (String train : ctcOffice.routes.keySet())
			{
				String trainString = "#" + ctcOffice.trains.get(train) + " " + train;
				trainListBox.getItems().add(trainString);
			}
			
			// Add list of stations associated with route
			trainListBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> selected, String oldTrain, String newTrain) {
					
					String[] stringParts = newTrain.split(" ");
					newTrain = stringParts[1];
					
					Route route = ctcOffice.routes.get(newTrain);
					
					// Clear the list
					stationListBox.getItems().clear();
					
					LinkedList<Block> blocks = route.getBlockList();
					for (Block block : blocks)
					{
						if (block.isStation())
						{
							String stationName = block.getStationName();
							if (!stationListBox.getItems().contains(stationName))
								stationListBox.getItems().add(block.getBlockId() + " " + stationName);
						}
					}
				}
			});
		}
	}
	
	@Override
	@FXML protected void submit() 
	{
		
		// Make sure user inputted
		//if (!dwellTime.equals("") && stationListBox.getValue() != null)
		try
		{
			// Get dwell time
			String dwellTimeString = dwellTimeInput.getText();
			double dwellTime = Double.parseDouble(dwellTimeString);
			
			// Get block ID
			String[] stringParts = stationListBox.getValue().split(" ");
			int blockId = Integer.parseInt(stringParts[0]);

			// Get train id
			stringParts = trainListBox.getValue().split(" ");
			int trainId = Integer.parseInt(stringParts[0].substring(1));
			
			//ctcOffice.transitSystem.ctcSetDwellTime(trainId, blockId, dwellTime);
			setDwellTime(trainId, blockId, dwellTime, ctcOffice);
			
			// Close window
			Stage currStage = (Stage) trainListBox.getScene().getWindow();
			currStage.close();
			
		}
		catch (Exception e)
		{
			errorMessage.setText("Error in scheduling train");
		}
	}

	/**
	 * Sets the dwell time for a station in a route
	 * Static method used for adding it outside of form
	 * @param blockId
	 * @param dwellTimeMins
	 */
	static public void setDwellTime(int trainId, int blockId, double dwellTimeMins, CTC ctc)
	{
		ctc.transitSystem.ctcSetDwellTime(trainId, blockId, dwellTimeMins);
	}

}
