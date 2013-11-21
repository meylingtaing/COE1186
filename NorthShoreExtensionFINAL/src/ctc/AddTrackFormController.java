/**
 * Controller for the Add Track Form in the CTC
 * @author meyling
 */
package ctc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import trackModel.Block;
import trackModel.TrackObject;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddTrackFormController extends FormController 
{
	
	@FXML private TextField trackCsvInput, trackNameInput;
	@FXML private ToggleGroup toggleColors;
	
	/**
	 * Grabs the user inputs from add track form
	 */
	public void submit()
	{
		String trackCsv = trackCsvInput.getText();
		String trackName = trackNameInput.getText();
		
		RadioButton selectedButton = (RadioButton) toggleColors.getSelectedToggle();
		String color = selectedButton.getText().toUpperCase();
		
		if (addTrack(trackName, trackCsv, color)) 
		{
			Stage currStage = (Stage) trackCsvInput.getScene().getWindow();
			currStage.close();
			CTC.ctcController.displayTrack();
			CTC.ctcController.displayLegend();
		}
		else
			errorMessage.setVisible(true);
	}
	
	/**
	 * Creates a track layout for CTC and track object for TrackModel
	 * @param trackName
	 * @param trackCsv
	 * @param color
	 * @return
	 */
	public boolean addTrack(String trackName, String trackCsv, String color) 
	{
		TrackLayout newTrackLayout = new TrackLayout(trackName, color);
		TrackObject newTrackObject = new TrackObject();
		newTrackObject.setLine(trackName);
		
		// Load coordinates from csv file
		try
		{
			Scanner fileScan = new Scanner(new File(trackCsv));
			while (fileScan.hasNextLine())
			{
				String line = fileScan.nextLine();
				String[] blockInfo = line.split(",");
				
				// Need coordinates for track layout
				Double[] block = new Double[4];
				
				block[0] = Double.parseDouble(blockInfo[1]);
				block[1] = Double.parseDouble(blockInfo[2]);
				block[2] = Double.parseDouble(blockInfo[3]);
				block[3] = Double.parseDouble(blockInfo[4]);
				
				if (!newTrackLayout.addBlock(block))
				{
					fileScan.close();
					System.err.println("Bad file");
					return false;
				}
				
				// Everything else in TrackObject Block
				Block newBlock = new Block(blockInfo);
				
				newTrackObject.addBlock(newBlock);
				
			}
			fileScan.close();
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Can't find file " + trackCsv);
			return false;
		}
		
		CTC.tracks.add(newTrackLayout);
		
		// TODO: figure out a better way to make a new track object.
		CTC.transitSystem.trackArray.put(trackName, newTrackObject);
		
		return true;
	}
}
