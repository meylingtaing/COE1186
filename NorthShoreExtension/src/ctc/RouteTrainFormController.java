/**
 * Controller for picking a track for a train route
 * 
 * @author meyling
 */

package ctc;

import trackModel.TrackObject;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class RouteTrainFormController extends FormController 
{

	@FXML private ComboBox<String> trackListBox;
	
	public void initialize()
	{
		System.out.println("Updating the route train form");
		for (TrackObject track : CTC.transitSystem.track.trackArray.values())
		{
			String trackName = track.getLine();
			trackListBox.getItems().add(trackName);
		}
	}
	
	@Override
	@FXML protected void submit() 
	{
		String selectedTrack = trackListBox.getValue();
		// Get train...
	}

}
