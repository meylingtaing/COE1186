/**
 * Controller class for the CTC Track Manager
 * 
 * @author meyling
 */
package ctc;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CTCTrackController extends CTCController 
{
	@FXML private Button removeTrackButton;
	
	private Label selectedTrackLegend;
	
	public void initialize() 
	{
		CTC.ctcController = this;
		
		if (!CTC.currUser.isTrackCreator())
		{
			addTrackButton.setDisable(true);
			removeTrackButton.setDisable(true);
		}
		
		displayTrack();
		
		// Make the event handler for selecting a legend
		selectLegendHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event)
			{
				Node trackLegend = (Node) event.getSource();
				trackLegend.setStyle("-fx-border-width: 1px; -fx-border-color: #FFFFFF;");
				
				if (CTC.currUser.isTrackCreator())
					removeTrackButton.setDisable(false);
				
				if (selectedTrackLegend == trackLegend)
				{
					selectedTrackLegend.setStyle("-fx-border-width: 0px;");
					selectedTrackLegend = null;
					removeTrackButton.setDisable(true);
				}
				
				else 
				{
					if (selectedTrackLegend != null)
						selectedTrackLegend.setStyle("-fx-border-width: 0px;");
					
					selectedTrackLegend = (Label) trackLegend;
				}
			}
		};
		// END selectLegendHandler ********************************************
	}
	
	/**
	 * Displays the track layout and makes track items selectable
	 */
	protected void displayTrack()
	{
		super.displayTrack();
		
		ObservableList<Node> trackLegends = trackLegendBox.getChildren();
		for (final Node trackLegend : trackLegends)
		{
			// Make the legend click-able
			trackLegend.setOnMouseClicked(selectLegendHandler);
		}
	}
	
	/**
	 * Removes a track
	 */
	@FXML private void removeTrack()
	{
		if (selectedTrackLegend == null)
			return;
		
		// Remove from tracklayout
		for (TrackLayout track : CTC.tracks)
		{
			if (track.name.equals(selectedTrackLegend.getText()))
			{
				CTC.tracks.remove(track);
				break;
			}
		}
		
		// Remove from TrackModel
		CTC.transitSystem.track.trackArray.remove(selectedTrackLegend.getText());
		
		selectedTrackLegend = null;
		removeTrackButton.setDisable(true);
		displayTrack();
	}
}
