/**
 * Controller for picking a track for a train route
 * 
 * @author meyling
 */

package ctc;

import trackModel.Block;
import trackModel.TrackObject;
import trainModel.TrainModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class RouteTrainFormController extends FormController 
{
	@FXML private ComboBox<String> trackListBox, stationListBox;
	
	public void initialize()
	{
		System.out.println("Updating the route train form");
		for (TrackObject track : CTC.transitSystem.trackArray.values())
		{
			String trackName = track.getLine();
			trackListBox.getItems().add(trackName);
		}
		
		trackListBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> selected, String oldTrack, String newTrack) {
				stationListBox.getItems().clear();
				for (Block block : CTC.transitSystem.trackArray.get(newTrack))
				{
					if (block.isStation())
					{
						String stationName = block.getStationName();
						if (!stationListBox.getItems().contains(stationName))
							stationListBox.getItems().add(stationName);
					}
				}
			}
		});
	}
	
	@Override
	@FXML protected void submit() 
	{
		//*
		TrackObject selectedTrack = CTC.transitSystem.trackArray.get(trackListBox.getValue());
		TrainModel selectedTrain = CTC.ctcController.getSelectedTrain();
		Route newRoute;
		
		if (CTC.transitSystem.routeList.contains(selectedTrain.getName()) && 
				CTC.transitSystem.routeList.get(selectedTrain.getName()).getTrack().getLine().equals(selectedTrack.getLine()))
		{
			System.out.println("Existing route found");
			newRoute = CTC.transitSystem.routeList.get(selectedTrain);
		}
		else	
			newRoute = new Route(selectedTrain, selectedTrack);
		
		newRoute.addStation(stationListBox.getValue());
		
		CTC.transitSystem.routeList.put(selectedTrain.getName(), newRoute);
		
		Stage currStage = (Stage) trackListBox.getScene().getWindow();
		currStage.close();
		CTC.ctcController.displayRouteInfo(newRoute);
		//*/
	}

}