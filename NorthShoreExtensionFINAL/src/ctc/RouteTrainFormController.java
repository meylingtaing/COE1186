/**
 * Controller for given a train a route
 */
package ctc;

import trackModel.Block;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class RouteTrainFormController extends FormController 
{
	@FXML private ComboBox<String> trackListBox, stationListBox, trainListBox;
	
	/**
	 * Fill dropdowns with information
	 */
	public void initialize()
	{
		if (ctcOffice != null)
		{
			// Add the list of trains
			for (String trainName : ctcOffice.trains.keySet())
			{
				String trainString = "#" + ctcOffice.trains.get(trainName) + " " + trainName;
				trainListBox.getItems().add(trainString);
			}
			
			// Add the list of tracks
			for (TrackLayout track : ctcOffice.tracks.values())
			{
				String trackName = track.toString();
				trackListBox.getItems().add(trackName);
			}
			
			// Populate list of stations from the selected track
			trackListBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> selected, String oldTrack, String newTrack) {
					
					// Clear the list
					stationListBox.getItems().clear();
					
					int numBlocks = ctcOffice.tracks.get(newTrack).getNumBlocks();
					for (int i = 0; i < numBlocks; i++ )
					{
						// Grab block from track controller
						Block block = ctcOffice.transitSystem.ctcGetBlock(newTrack, i);
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
	}
	
	@Override
	@FXML protected void submit() 
	{
		String selectedTrain = trainListBox.getValue();
			String[] stringParts = selectedTrain.split(" ");
			selectedTrain = stringParts[1];
		
		String selectedTrack = trackListBox.getValue();
		String selectedStation = stationListBox.getValue();
		Route route;
		
		// If route exists, just update it
		if (ctcOffice.routes.containsKey(selectedTrain))
			route = ctcOffice.routes.get(selectedTrain);
		
		// Route doesn't exist, make new one
		else
		{
			int trainId = ctcOffice.trains.get(selectedTrain);
			route = new Route(trainId, ctcOffice.transitSystem.ctcGetTrack(selectedTrack));
		}
		
		route.addStation(selectedStation);
		
		// Update route in CTC's route database
		ctcOffice.routes.put(selectedTrain, route);
		
		// Update train position
		int trainID = Integer.parseInt(stringParts[0].substring(1));
		ctcOffice.transitSystem.ctcSetInitialPosition(trainID, selectedTrack);
		
		// Send route to track controller
		ctcOffice.transitSystem.ctcSendRoute(trainID, route);
		
		// Figure out a way to display this
		System.out.println(route);

		// Close window
		Stage currStage = (Stage) trackListBox.getScene().getWindow();
		currStage.close();
	}
	
	/**
	 * Route train outside of CTC gui
	 * @param selectedTrain
	 * @param selectedTrack
	 * @param selectedStation
	 * @param ctc
	 * @return
	 */
	public static boolean routeTrain(String selectedTrain, String selectedTrack, String selectedStation, CTC ctc)
	{
		try
		{
			Route route;
			// Modifying name so it's easier to deal with
			selectedTrain = selectedTrain.trim();
			selectedTrain = selectedTrain.replace(" ", "");
			int trainId = ctc.trains.get(selectedTrain);
			
			// If route exists, just update it
			if (ctc.routes.containsKey(selectedTrain))
				route = ctc.routes.get(selectedTrain);
			// Route doesn't exist, make new one
			else
			{
				route = new Route(trainId, ctc.transitSystem.ctcGetTrack(selectedTrack));
			}
			
			route.addStation(selectedStation);
			
			// Update route in CTC's route database
			ctc.routes.put(selectedTrain, route);
			
			// Update train position
			ctc.transitSystem.ctcSetInitialPosition(trainId, selectedTrack);
			
			// Send route to track controller
			ctc.transitSystem.ctcSendRoute(trainId, route);
			
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Error in routing train");
			return false;
		}
	}

}
