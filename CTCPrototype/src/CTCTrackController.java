import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(20);
		Button addTrackButton = new Button("Add track");
		Button removeTrackButton = new Button("Remove track");
		if (!CTC.currUser.isTrackCreator()) {
			addTrackButton.setDisable(true);
			removeTrackButton.setDisable(true);
		}
		buttonBox.getChildren().addAll(addTrackButton, removeTrackButton);
		this.getChildren().add(buttonBox);
		
		if (CTC.numTracks == 0) {
			addTrack();
		}
		
		addTrackButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				addTrack();
			}
		});
	}
	
	public void addTrack() {
		// Prompt user for a csv file
		final Popup trackPopup = new Popup();
		final TextField inputTrack = new TextField();
		Text newTrackText = new Text("Enter name of csv file");
		final Text errorText = new Text("Error: file not found");
		errorText.setVisible(false);
		
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				// Get the user input
				String trackCsv = inputTrack.getText();
				Track newTrack = new Track();
				//*
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
					trackPopup.hide();
				} 
				
				catch (FileNotFoundException e) {
					//System.out.println("Could not find " + trackCsv);
					errorText.setVisible(true);
				}
				// */
				
				CTC.tracks[CTC.numTracks] = newTrack;
				CTC.numTracks++;
				displayTrack();
			}
		});
		
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				trackPopup.hide();
			}
		});
		
		HBox trackPopupHBox = new HBox();
		trackPopupHBox.getChildren().addAll(inputTrack, submitButton, cancelButton);
		VBox trackPopupContent = new VBox();
		trackPopupContent.getChildren().addAll(newTrackText, trackPopupHBox, errorText);
		trackPopup.getContent().addAll(trackPopupContent);
		trackPopupContent.setStyle("-fx-background-color: cornsilk; -fx-padding: 10;");
		trackPopup.show(CTC.ctcStage, 500, 300);
	}
}
