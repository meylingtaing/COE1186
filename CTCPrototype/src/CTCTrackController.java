/**
 * The functionality of the Track Manager in CTC
 * 
 * @author meyling
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class CTCTrackController extends CTCMainController {
	
	private String addTrackFile = "addTrackForm.fxml";
	private HBox selectedTrackLegend;
	
	@FXML private TextField trackCsvInput, trackNameInput;
	@FXML private Text errorMessage;
	@FXML private ToggleGroup toggleColors;
	
	private Button removeTrackButton;
	
	public CTCTrackController() {
		super();
	}
	
	/**
	 * Initializes controller
	 */
	public void initialize() {
		selectedTrackLegend = null;
		Button addTrackButton = new Button("Add track");
		removeTrackButton = new Button("Remove track");
		removeTrackButton.setDisable(true);
		Button navigateMainButton = new Button("Back to Main");
		if (!CTC.currUser.isTrackCreator()) {
			addTrackButton.setDisable(true);
		}
		buttonContainer.getChildren().addAll(addTrackButton, removeTrackButton, navigateMainButton);
		
		//*
		if (CTC.numTracks == 0) {
			//displayAddTrack();
		}
		//*/
		
		displayTrack();
		displayTrains();
		//displayInfo();
		
		addTrackButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayPopup(addTrackFile, "Add new track");
			}
		});
		
		removeTrackButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				removeTrack();
			}
		});
		
		navigateMainButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				navigateMain();
			}
		});
	}
	
	protected void displayTrack() {
		super.displayTrack();
		
		// This will allow the user to select tracks for deleting
		ObservableList<Node> trackLegends = legendBox.getChildren();
		for (final Node trackLegend : trackLegends) {
			// Make the legend click-able
			trackLegend.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					trackLegend.setStyle("-fx-border-width: 1px; -fx-border-color: #FFFFFF;");
					
					if (CTC.currUser.isTrackCreator())
						removeTrackButton.setDisable(false);
					
					if (selectedTrackLegend != null)
						selectedTrackLegend.setStyle("-fx-border-width: 0px;");
					selectedTrackLegend = (HBox) trackLegend;
				}
			});
		}
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
	
	private boolean addTrack(String trackName, String trackCsv, String color) {
		Track newTrack = new Track(trackName);
		newTrack.color = color;

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
				
				String station;
				if (blockInfo[5].equals("none")) {
					station = null;
				}
				else {
					station = blockInfo[5];
				}
				
				newTrack.addBlock(block, station);
			}
			fileScan.close();
		} 
		
		catch (FileNotFoundException e) {
			return false;
		}
		
		CTC.tracks[CTC.numTracks] = newTrack;
		CTC.numTracks++;
		return true;
	}
	
	@FXML private void submitNewTrack() {
		String trackCsv = trackCsvInput.getText();
		String trackName = trackNameInput.getText();
		
		RadioButton selectedButton = (RadioButton) toggleColors.getSelectedToggle();
		String color = selectedButton.getText().toUpperCase();
		
		if (addTrack(trackName, trackCsv, color)) {
			Stage currStage = (Stage) trackCsvInput.getScene().getWindow();
			currStage.close();
			navigateTrack();
		}
		else
			errorMessage.setVisible(true);
	}
	
	@FXML private void enter(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			submitNewTrack();
		}
	}
	
	@FXML protected void cancel(ActionEvent event) {
		Node currNode = (Node) event.getSource();
		Stage currStage = (Stage) currNode.getScene().getWindow();
		currStage.close();
		navigateTrack();
	}
	
	private void removeTrack() {
		if (selectedTrackLegend == null)
			return;
		
		for (int i = 0; i < CTC.numTracks; i++) {
			Label selectedLabel = (Label) selectedTrackLegend.getChildren().get(0);
			String selectedTrackName = selectedLabel.getText();
			if (selectedTrackName.equals(CTC.tracks[i].toString())) {
				selectedTrackLegend = null;
				CTC.tracks[i] = null;
				
				for (; i < CTC.numTracks - 1; i++) {
					CTC.tracks[i] = CTC.tracks[i+1];
				}
				
				CTC.numTracks--;
				break;
			}
		}
		
		displayTrack();
		displayInfo();
		
	}
}
