/**
 * Controller for opening and closing tracks for maintenance
 */
package ctc;

import trackModel.Block;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class TrackMaintenanceFormController extends FormController 
{
	@FXML private ComboBox<String> trackListBox;
	@FXML private ComboBox<Integer> blockListBox;
	@FXML private Button maintenanceSubmitButton;
	
	/**
	 * Initializes maintenance form with tracks and blocks
	 */
	public void initialize()
	{
		if (ctcOffice != null) 
		{
			// Get list of tracks
			for (TrackLayout track : ctcOffice.tracks.values())
			{
				String trackName = track.toString();
				trackListBox.getItems().add(trackName);
			}
			
			// Update list of blocks when track is selected
			trackListBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> selected, String oldTrack, String newTrack) {
					blockListBox.getItems().clear();
					int numBlocks = ctcOffice.tracks.get(newTrack).getNumBlocks();
					for (int i = 0; i < numBlocks; i++)
						blockListBox.getItems().add(i);
				}
			});
			
			// Update submit button when block is selected
			blockListBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
				public void changed(ObservableValue<? extends Integer> selected, Integer oldBlock, Integer newBlock) {
					// Check if block is currently closed for maintenance
					Block block = ctcOffice.transitSystem.ctcGetBlock(trackListBox.getValue(), newBlock);
					
					// Block is currently closed -- so allow user to open
					if (block.isClosed())
					{
						maintenanceSubmitButton.setText("Open block");
						maintenanceSubmitButton.setDisable(false);
					}
					else
					{
						maintenanceSubmitButton.setText("Close block");
						maintenanceSubmitButton.setDisable(false);
					}
				}
			});
		}
	}

	/**
	 * Opens or closes a block for maintenance
	 */
	@Override
	@FXML protected void submit() 
	{
		// Get track and block
		String trackName = trackListBox.getValue();
		int blockId = blockListBox.getValue();
		
		// Check whether we want to open or close
		if (maintenanceSubmitButton.getText().equals("Close block"))
			ctcOffice.transitSystem.ctcCloseBlock(trackName, blockId);
		else
			ctcOffice.transitSystem.ctcOpenBlock(trackName, blockId);
		
		// Close window
		Stage currStage = (Stage) trackListBox.getScene().getWindow();
		currStage.close();
		
		// Update display
		ctcOffice.ctcController.displayTrack();
		ctcOffice.ctcController.displayLegend();
	}

}
