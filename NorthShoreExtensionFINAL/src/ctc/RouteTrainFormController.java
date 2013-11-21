/**
 * Controller for picking a track for a train route
 * 
 * @author meyling
 */

package ctc;

import nse.TrainPosition;
import trackModel.Block;
import trackModel.Track;
import trackModel.TrackObject;
import trainmodule.TrainModel;
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
				System.out.println("In track list box: " + Track.trackArray.get(newTrack));
				for (Block block : Track.trackArray.get(newTrack))
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
		
		System.out.println("Selected train: " + selectedTrain);
		if (CTC.transitSystem.routeList.containsKey(selectedTrain.getTrainID()) && 
				CTC.transitSystem.routeList.get(selectedTrain.getTrainID()).getTrack().getLine().equals(selectedTrack.getLine()))
		{
			newRoute = CTC.transitSystem.routeList.get(selectedTrain.getTrainID());
		}
		else
		{
			newRoute = new Route(selectedTrain, selectedTrack);
			TrainPosition position = new TrainPosition(selectedTrack);
			CTC.transitSystem.trainPositions.put(selectedTrain.getTrainID(), position);
		}
		
		newRoute.addStation(stationListBox.getValue());
		
		CTC.transitSystem.routeList.put(selectedTrain.getTrainID(), newRoute);
		CTC.transitSystem.trainPositions.get(selectedTrain.getTrainID()).setRoute(newRoute);
		
		Stage currStage = (Stage) trackListBox.getScene().getWindow();
		currStage.close();
		CTC.ctcController.displayRouteInfo(newRoute);
		//*/
	}

}