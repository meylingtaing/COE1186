/**
 * Controller for adding a new track
 * @author meyling
 */
package ctc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import trackModel.Block;
import trackModel.TrackObject;

public class AddTrackFormController extends FormController 
{
	// GUI related fields
	@FXML private TextField trackCsvInput, trackNameInput;
	@FXML private ToggleGroup toggleColors;
	
	private static boolean DEBUG = false;
	
	/**
	 * Grabs inputs and passes them to addTrack()
	 */
	@Override
	@FXML protected void submit() 
	{
		
		String trackCsv = trackCsvInput.getText();
		String trackName = trackNameInput.getText();
		
		RadioButton selectedButton = (RadioButton) toggleColors.getSelectedToggle();
		String color = selectedButton.getText().toUpperCase();
		
		if (addTrack(trackName, trackCsv, color, ctcOffice)) 
		{
			Stage currStage = (Stage) trackCsvInput.getScene().getWindow();
			currStage.close();
			
			// Update displays
			ctcOffice.ctcController.displayTrack();
			ctcOffice.ctcController.displayLegend();
			ctcOffice.ctcStage.sizeToScene();
		}
		else
			errorMessage.setVisible(true);

	}
	
	/**
	 * Adds a track to north shore extension
	 * @param trackName
	 * @param trackCsv
	 * @param color
	 * @return
	 */
	public static boolean addTrack(String trackName, String trackCsv, String color, CTC ctc)
	{
		TrackLayout newTrackLayout = new TrackLayout(trackName, color);
		TrackObject newTrack = new TrackObject();
		
		try 
		{
			Scanner fileScan = new Scanner(new File(trackCsv));
			while (fileScan.hasNextLine())
			{
				String line = fileScan.nextLine();
				if (line.charAt(0) == '#')
					continue;
				
				String[] blockInfo = line.split(",");
				
				// Add block to track
				Block newBlock = new Block(blockInfo);
				newTrack.addBlock(newBlock);
				
				// Add block to CTC's track database
				Double[] blockCoordinates = newBlock.getCoordinates();
				newTrackLayout.addBlock(blockCoordinates);
				
			}
			
			// Add track to the transit system
			ctc.transitSystem.ctcAddTrack(trackName, newTrack);
			
			// Add track to CTC's track database
			ctc.tracks.put(trackName, newTrackLayout);
			
			fileScan.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("Can't load track file");
			if (DEBUG)
				e.printStackTrace();
			return false;
		}
		catch (Exception e)
		{
			System.err.println("Bad file.");
			if (DEBUG)
				e.printStackTrace();
			return false;
		}
		return true;
	}
}
