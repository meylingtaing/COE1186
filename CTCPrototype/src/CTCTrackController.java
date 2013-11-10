import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;


public class CTCTrackController extends CTCMainController {
	public CTCTrackController() {
		super();
	}
	
	/**
	 * Initializes controller
	 */
	public void initialize() {
		System.out.println("Initializing the CTC Track Controller");
		Button addTrackButton = new Button("Add track");
		Button removeTrackButton = new Button("Remove track");
		if (!CTC.currUser.isTrackCreator()) {
			addTrackButton.setDisable(true);
			removeTrackButton.setDisable(true);
		}
		buttonContainer.getChildren().addAll(addTrackButton, removeTrackButton);
		
		//*
		if (CTC.numTracks == 0) {
			displayAddTrack();
		}
		//*/
		
		displayInfo();
		
		addTrackButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayAddTrack();
			}
		});
	}
	
	public void displayInfo() {
		infoContainer.getChildren().clear();
		// Print stuff in info container
		for (int i = 0; i < CTC.numTracks; i++) {
			Track track = CTC.tracks[i];
			Text trackInfo = new Text(track.toString());
			infoContainer.getChildren().add(trackInfo);
		}
		CTC.ctcStage.sizeToScene();
	}
	
	private boolean addTrack(String trackName, String trackCsv) {
		Track newTrack = new Track(trackName);

		try {
			Scanner fileScan = new Scanner(new File(trackCsv));
			while (fileScan.hasNextLine()) {
				String line = fileScan.nextLine();
				String[] blockInfo = line.split(",");
				Double[] block = new Double[4];
				
				block[0] = Double.parseDouble(blockInfo[1]);
				block[1] = Double.parseDouble(blockInfo[2]);
				block[2] = Double.parseDouble(blockInfo[3]);
				block[3] = Double.parseDouble(blockInfo[4]);
				
				newTrack.addBlock(block);
			}
			fileScan.close();
		} 
		
		catch (FileNotFoundException e) {
			return false;
		}
		
		CTC.tracks[CTC.numTracks] = newTrack;
		CTC.numTracks++;
		displayTrack();
		displayInfo();
		return true;
	}
	
	public void displayAddTrack() {
		// Prompt user for a csv file
		final Popup trackPopup = new Popup();
		final TextField trackCsvInput = new TextField();
		final TextField trackNameInput = new TextField();
		Text trackNamePrompt = new Text("Track name");
		Text trackCsvPrompt = new Text("Csv file");
		final Text errorText = new Text("Error: file not found");
		errorText.setVisible(false);
		
		Button submitButton = new Button("Submit");
		Button cancelButton = new Button("Cancel");
		
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(submitButton, cancelButton);
		
		GridPane popupPane = new GridPane();
		popupPane.add(trackNamePrompt, 0, 0);
		popupPane.add(trackNameInput, 1, 0);
		popupPane.add(trackCsvPrompt, 0, 1);
		popupPane.add(trackCsvInput, 1, 1);
		popupPane.add(buttonBox, 1, 2);
		
		trackPopup.getContent().addAll(popupPane);
		popupPane.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
		trackPopup.show(CTC.ctcStage, 500, 300);
		
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				String trackCsv = trackCsvInput.getText();
				String trackName = trackNameInput.getText();
				if (addTrack(trackName, trackCsv))
					trackPopup.hide();
				else
					errorText.setVisible(true);
			}
		});
		
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				trackPopup.hide();
			}
		});
	}
}
