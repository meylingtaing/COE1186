/**
 * Main controller for CTC Office. From here we can navigate to other views
 * and monitor the state of the transit system.
 * @author meyling
 */
package ctc;

import java.io.IOException;

import trackModel.Block;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CTCController  
{
	private CTC ctcOffice;
	
	// GUI related fields
	@FXML protected Button editTracksButton, navigateMainButton, addTrackButton,
	removeTrackButton, maintenanceButton, editRoutesButton, addTrainButton,
	removeTrainButton, routeTrainButton, suggestSetptButton, scheduleTrainButton;
	@FXML protected Pane displayBox;
	@FXML protected VBox trackLegendBox, trainLegendBox, infoBox;
	protected Label selectedLegend;
	
	@FXML protected Text selectedName;
	
	private String trackGui = "CTCTrackView.fxml";
	private String mainGui = "CTCView.fxml";
	private String routeGui = "CTCTrainView.fxml";
	private String addTrackForm = "addTrackForm.fxml";
	private String removeTrackForm = "removeTrackForm.fxml";
	private String maintenanceForm = "trackMaintenanceForm.fxml";
	private String addTrainForm = "addTrainForm.fxml";
	private String removeTrainForm = "removeTrainForm.fxml";
	private String routeTrainForm = "routeTrainForm.fxml";
	private String setpointForm = "suggestSetptForm.fxml";
	private String scheduleForm = "scheduleTrainForm.fxml";
	private int shrinkDisplay = 9;
	
	protected String selectedTrack = null;
	protected String selectedTrain = null;
	
	/**
	 * Sets the CTC office model that we are controlling
	 * @param ctcOffice
	 */
	public void setContext(CTC ctcOffice)
	{
		this.ctcOffice = ctcOffice;
		
		initialize();
	}

	/**
	 * Actions that are done on initialization of the controller
	 */
	public void initialize() 
	{
		if (ctcOffice != null)
		{
			ctcOffice.ctcController = this;
			displayTrack();
			displayLegend();
			displayInfo();
		}
	}
	
	/**
	 * Displays the track
	 */
	public void displayTrack()
	{
		// Clear the display first
		displayBox.getChildren().clear();
		
		for (TrackLayout track : ctcOffice.tracks.values())
		{
			int blockId = 0;
			for (Double[] blockCoordinates : track)
			{
				String strokeStyle = "";
				
				// Resizing the lines so they fit on the screen
				double startX = blockCoordinates[0]/shrinkDisplay+10;
				double startY = blockCoordinates[1]/shrinkDisplay+10;
				double endX = blockCoordinates[2]/shrinkDisplay+10;
				double endY = blockCoordinates[3]/shrinkDisplay+10;
				
				// For crisp looking lines
				startX = Math.floor(startX) + .5;
				startY = Math.floor(startY) + .5;
				endX = Math.floor(endX) + .5;
				endY = Math.floor(endY) + .5;
				
				final Line line = new Line(startX, startY, endX, endY);
				
				// Determine if block is station
				final Block block = ctcOffice.transitSystem.ctcGetBlock(track.toString(), blockId);
				
				// Add mouseovers to ALL blocks!
				{
					// Format line to represent station
					if (block.isStation())
					{
						strokeStyle = "-fx-stroke: #FFFFFF; -fx-stroke-width: 1px;";
						line.setStrokeType(StrokeType.OUTSIDE);
						line.setTranslateX(-.5);
						line.setTranslateY(-.5);
					}
					
					// Add mouseover popup with station name
					final Popup stationPopup = new Popup();
					
					// Add a mouseover for the line
					line.setOnMouseEntered(new EventHandler<MouseEvent>() {
						public void handle (MouseEvent event) {
							String popupString = "" + block.getBlockId();
							if (block.isStation())
								popupString += " " + block.getStationName() + " ";
							Label stationName = new Label(popupString);
							stationPopup.getContent().add(stationName);
							stationName.setStyle("-fx-background-color: #FFFFFF");
							stationPopup.show(ctcOffice.ctcStage, event.getScreenX(), event.getScreenY() - 20);
							/*
							Bloom bloom = new Bloom(.1);
							GaussianBlur blur = new GaussianBlur(2);
							blur.setInput(bloom);
							line.setEffect(blur);
							line.setStrokeType(StrokeType.OUTSIDE);
							//*/

						}
					});
					
					// Make sure popup disappears after mouse leaves
					line.setOnMouseExited(new EventHandler<MouseEvent>() {
						public void handle (MouseEvent event) {
							stationPopup.hide();
						}
					});
				}
				
				// Normal track color if block isn't station
				if (!block.isStation())
				{
					strokeStyle = "-fx-stroke: " + track.getColor() + ";";
				}
				
				// Display train if detected
				if (block.isTrainDetected())
				{
					strokeStyle = "-fx-stroke: #CC33FF; -fx-stroke-width: 3px;";
				}
				
				// Determine if block is closed for maintenance
				if (block.isClosed())
				{
					// Make dotted line
					strokeStyle += "-fx-stroke-dash-array: 0.2 4.0;";
				}
				
				// Display line
				line.setStyle(strokeStyle);
				line.setVisible(true);
				displayBox.getChildren().add(line);
				
				blockId++;
			}
		}
	}
	
	/**
	 * Display the legend
	 */
	protected void displayLegend()
	{
		// Clear legend contents
		trackLegendBox.getChildren().clear();
		trainLegendBox.getChildren().clear();
		
		// Display tracks on the side
		for (TrackLayout track : ctcOffice.tracks.values())
		{
			Rectangle trackSymbol = new Rectangle(20, 3);
			Label trackLabel = new Label(track.toString(), trackSymbol);
			trackLabel.getStyleClass().add("ctcLegend");
			String symbolStyle = "-fx-fill: " + track.getColor() + ";";
			trackSymbol.setStyle(symbolStyle);
			
			trackLegendBox.getChildren().add(trackLabel);
			
			trackLabel.setOnMouseClicked( new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event)
				{
					Label trackLegend = (Label) event.getSource();
					trackLegend.setStyle("-fx-border-width: 1px; -fx-border-color: #FFFFFF;");
					
					if (selectedLegend == trackLegend)
					{
						selectedLegend.setStyle("-fx-border-width: 0px;");
						selectedLegend = null;
						selectedTrack = null;
						selectedTrain = null;
					}
					
					else 
					{
						if (selectedLegend != null)
							selectedLegend.setStyle("-fx-border-width: 0px;");
						
						selectedLegend = trackLegend;
						selectedTrack = trackLegend.getText();
						selectedTrain = null;
					}
					
					displayInfo();
				}
			});
		}
		
		// Display trains on the side
		for (String trainName : ctcOffice.trains.keySet())
		{
			Circle trainSymbol = new Circle(3);
			String trainString = "#" + ctcOffice.trains.get(trainName) + " " + trainName;
			
			Label trainLabel = new Label(trainString, trainSymbol);
			trainLabel.getStyleClass().add("ctcLegend");
			trainSymbol.setStyle("-fx-fill: #CC33FF;");
			
			trainLegendBox.getChildren().add(trainLabel);
			
			trainLabel.setOnMouseClicked( new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event)
				{
					Label trainLegend = (Label) event.getSource();
					trainLegend.setStyle("-fx-border-width: 1px; -fx-border-color: #FFFFFF;");
					
					if (selectedLegend == trainLegend)
					{
						selectedLegend.setStyle("-fx-border-width: 0px;");
						selectedLegend = null;
						selectedTrain = null;
						selectedTrack = null;
					}
					
					else 
					{
						if (selectedLegend != null)
							selectedLegend.setStyle("-fx-border-width: 0px;");
						
						selectedLegend = trainLegend;
						selectedTrain = trainLegend.getText();
						selectedTrack = null;
					}
					displayInfo();
				}
				
			});
		}
	}
	
	/**
	 * Display the information about selected item
	 */
	public void displayInfo()
	{
		if (ctcOffice != null && ctcOffice.ctcStage != null)
		{
			infoBox.getChildren().clear();
			if (selectedTrain != null)
			{
				System.out.println("Selected train: " + selectedTrain);
				String[] parts = selectedTrain.split(" ");
				// Train id
				int trainId = ctcOffice.trains.get(parts[1]);
				
				selectedName.setText("Train " + trainId + ": " + parts[1]);
				
				// Add current location
				int currBlock = ctcOffice.transitSystem.trainPositions.get(trainId).getCurrBlock().getBlockId();
				Text location = new Text("Position: Block " + currBlock);
				
				// Add everything and display
				infoBox.getChildren().add(location);
				selectedName.setVisible(true);
			}
			else if (selectedTrack != null)
			{
				selectedName.setText("Track: " + selectedTrack);
				selectedName.setVisible(true);
			}
			else
			{
				selectedName.setVisible(false);
			}
			ctcOffice.ctcStage.sizeToScene();
		}
	}
	
	/**
	 * Navigate to the other views of the CTC
	 * @param event defines button that was pressed
	 */
	@FXML protected void navigate(ActionEvent event)
	{
		String fxmlFile = "";
		String title = "";
		Button buttonClicked = (Button) event.getSource();
		
		// Get the FXML file and the title of the new view
		if (buttonClicked == editTracksButton)
		{
			fxmlFile = trackGui;
			title = "CTC -- Track Manager";
		}
		else if (buttonClicked == navigateMainButton)
		{
			fxmlFile = mainGui;
			title = "CTC";
		}
		else if (buttonClicked == editRoutesButton)
		{
			fxmlFile = routeGui;
			title = "CTC -- Trains and Routes Manager";
		}
		
		// Navigate to the new view
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(fxmlFile));
			Parent root = (Parent) fxmlLoader.load();
			((CTCController)fxmlLoader.getController()).setContext(ctcOffice);
			
			ctcOffice.ctcStage.setScene(new Scene(root));
			ctcOffice.ctcStage.setTitle(title);
			ctcOffice.ctcStage.show();
		} 
		catch (IOException e) 
		{
			System.err.println("Error loading " + buttonClicked.getText());
		}
	}
	
	/**
	 * Displays a popup, which is usually just a form
	 * @param event
	 */
	@FXML protected void displayPopup(ActionEvent event)
	{
		Button clickedButton = (Button) event.getSource();
		String fxmlFile = "";
		String title = "";
		
		if (clickedButton == addTrackButton)
		{
			fxmlFile = addTrackForm;
			title = "Add new track";
		}
		else if (clickedButton == removeTrackButton)
		{
			fxmlFile = removeTrackForm;
			title = "Remove Track";
		}
		else if (clickedButton == maintenanceButton)
		{
			fxmlFile = maintenanceForm;
			title = "Track Maintenance";
		}
		else if (clickedButton == addTrainButton)
		{
			fxmlFile = addTrainForm;
			title = "Add train";
		}
		else if (clickedButton == removeTrainButton)
		{
			fxmlFile = removeTrainForm;
			title = "Remove train";
		}
		else if (clickedButton == routeTrainButton)
		{
			fxmlFile = routeTrainForm;
			title = "Route train";
		}
		else if (clickedButton == suggestSetptButton)
		{
			fxmlFile = setpointForm;
			title = "Suggest Setpoint and Authority";
		}
		else if (clickedButton == scheduleTrainButton)
		{
			fxmlFile = scheduleForm;
			title = "Schedule Train";
		}
		
		try 
		{
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource(fxmlFile));
			Parent root = (Parent) fxmlLoader.load();
			((FormController)fxmlLoader.getController()).setContext(ctcOffice);
			
			Stage popupStage = new Stage();
			popupStage.setScene(new Scene(root));
			popupStage.setTitle(title);
			popupStage.show();
		} 
		catch (IOException e) 
		{
			System.out.println("Can't load popup " + title);
			//e.printStackTrace();
		}
	}

}
