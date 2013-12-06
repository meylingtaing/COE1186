/**
 * Controller for removing a track
 */
package ctc;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class RemoveTrackFormController extends FormController 
{
	@FXML ComboBox<String> trackListBox;
	
	/**
	 * Initializes form input with the tracks
	 */
	public void initialize()
	{
		if (ctcOffice != null)
		{
			// Get list of all tracks
			for (TrackLayout track : ctcOffice.tracks.values())
			{
				System.out.println("Adding track " + track);
				String trackName = track.toString();
				trackListBox.getItems().add(trackName);
			}
		}
	}

	@Override
	@FXML protected void submit() 
	{
		String selectedTrack = trackListBox.getValue();
		
		if (selectedTrack != null)
		{
			// Remove from CTC's database
			ctcOffice.tracks.remove(selectedTrack);
			
			// Remove from the transit system
			ctcOffice.transitSystem.ctcRemoveTrack(selectedTrack);
		}
		
		// Close window
		Stage currStage = (Stage) trackListBox.getScene().getWindow();
		currStage.close();
		
		// Update displays
		ctcOffice.ctcController.displayTrack();
		ctcOffice.ctcController.displayLegend();
	}
}
