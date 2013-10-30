import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;

public class CTCController {
	ArrayList<Double[]> blockCoordinates;
	int blockCount;
	
	@FXML
	BorderPane trackDisplay;
	
	public void initialize() {
		
		// Load track layout from CSV file
		blockCoordinates = new ArrayList<Double[]>();
		blockCount = 0;
		
		System.out.println("Successful start?");
	}
	
	/**
	 * Creates the track display
	 */
	private void displayTrack() {
		
		for (int i = 0; i < blockCount; i++) {
			double startX = blockCoordinates.get(i)[0]/10+10;
			double startY = blockCoordinates.get(i)[1]/10+10;
			double endX = blockCoordinates.get(i)[2]/10+10;
			double endY = blockCoordinates.get(i)[3]/10+10;
			
			Line line = new Line(startX, startY, endX, endY);
			trackDisplay.getChildren().add(line);
		}
		
	}
	
	@FXML
	public void editTracks() {
		System.out.println("Clicked Edit tracks");
		
		// Automatic, ask user for track stuff if we don't have any yet
		if (blockCount == 0) {
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
							
							blockCoordinates.add(block);
							blockCount++;
						}
						fileScan.close();
						trackPopup.hide();
					} 
					
					catch (FileNotFoundException e) {
						//System.out.println("Could not find " + trackCsv);
						errorText.setVisible(true);
					}
					// */
					
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
			trackPopup.show(trackDisplay, 600, 500);
		}
	}
	
	@FXML
	public void routeTrains() {
		
	}
	
	@FXML
	public void simulate() {
		
	}
}
