/**
 * Main controller class for CTC
 * @author meyling
 */
package ctc;

import java.io.IOException;

import TrainController.TrainController;
import nse.TrainPosition;
import trackModel.Block;
import trainmodule.TrainModel;
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
	@FXML protected Button editTracksButton, editRoutesButton, addTrackButton, 
							navigateMainButton, addTrainButton, routeTrainButton,
							suggestSetptButton;
	@FXML protected Pane displayBox;
	@FXML protected VBox legendBox, trackLegendBox, trainLegendBox, infoContainer;
	
	private String trackGui = "CTCTrackView.fxml";
	private String routeGui = "CTCRouteView.fxml";
	
	protected EventHandler<MouseEvent> selectLegendHandler, selectStationHandler;
	
	protected Block selectedStation;
	
	public void initialize() 
	{
		CTC.ctcController = this;
		
		// Based on permissions of user, disable certain buttons
		if (!CTC.currUser.isTrackCreator() && !CTC.currUser.isMaintenance())
			editTracksButton.setDisable(true);
		
		displayTrack();
		displayLegend();
	}
	
	/**
	 * Creates the track display
	 */
	public void displayTrack() 
	{
		//System.out.println("Refreshing track, number of tracks " + CTC.tracks.size());
		// Clear display first
		displayBox.getChildren().clear();
		
		
		for (int i = 0; i < CTC.tracks.size(); i++) {
			int j = 0;
			
			for (final Double[] block: CTC.tracks.get(i)) 
			{
				
				double startX = block[0]/9+10;
				double startY = block[1]/9+10;
				double endX = block[2]/9+10;
				double endY = block[3]/9+10;
				
				//*/ For crisp looking lines
				startX = Math.floor(startX) + .5;
				startY = Math.floor(startY) + .5;
				endX = Math.floor(endX) + .5;
				endY = Math.floor(endY) + .5;
				//*/
				Line line = new Line(startX, startY, endX, endY);
				
				String strokeStyle;
				// TODO: better integration stuff
				final Block currBlock = CTC.transitSystem.trackArray.get(CTC.tracks.get(i).toString()).getBlock(j);
				//System.out.println("Display: " + CTC.transitSystem.track.trackArray.get(CTC.tracks.get(i).toString()));
				
				
				if (currBlock.isStation()) 
				{
					strokeStyle = "-fx-stroke: #FFFFFF; -fx-stroke-width: 1px;";
					line.setStrokeType(StrokeType.OUTSIDE);
					line.setTranslateX(-.5);
					line.setTranslateY(-.5);
					
					final Popup stationPopup = new Popup();
					// Add a mouseover for the line
					line.setOnMouseEntered(new EventHandler<MouseEvent>() {
						public void handle (MouseEvent event) {
							Label stationName = new Label(" " + currBlock.getStationName() + " ");
							stationPopup.getContent().add(stationName);
							stationName.setStyle("-fx-background-color: #FFFFFF");
							stationPopup.show(CTC.ctcStage, event.getScreenX(), event.getScreenY() - 20);
						}
					});
					
					line.setOnMouseExited(new EventHandler<MouseEvent>() {
						public void handle (MouseEvent event) {
							stationPopup.hide();
						}
					});
					
					line.setOnMouseClicked(new EventHandler<MouseEvent>() {
						public void handle(MouseEvent event)
						{
							selectedStation = currBlock;
							
						}
					});
					
				}
				else
				{
					strokeStyle = "-fx-stroke: " + CTC.tracks.get(i).getColor() + ";";
				}
				
				// Trying to display line differently if train is present
				// System.out.println("Simulated? " + CTC.transitSystem.simulated);
				// System.out.println("Checking track " + CTC.tracks.get(i).toString());
				// System.out.println("Checking block " + currBlock.getBlockId());
				/*
				if (CTC.transitSystem.simulated && CTC.transitSystem.isTrainDetected(CTC.tracks.get(i).toString(), currBlock.getBlockId()))
				{
					strokeStyle = "-fx-stroke: #9933FF; -fx-stroke-width: 2px;";
					System.out.println("Showing the train on the track");
				}
				//*/
				
				line.setStyle(strokeStyle);
				line.setVisible(true);
				displayBox.getChildren().add(line);
				
				j++;
			}
		}
	}
	
	/**
	 * Displays the position of the trains on the track
	 */
	public void displayTrains()
	{
		displayTrack();
		System.out.println("Called displayTrains()");
		System.out.println(CTC.transitSystem.trains.get(0));
		CTC.transitSystem.simulated = false;
		//for (TrainController train : CTC.transitSystem.trains.values())
		//{
			TrainController train = CTC.transitSystem.trains.get(0);
			// We should probably actually talk to each other but for now just using global
			TrainPosition trainPosition = CTC.transitSystem.trainPositions.get(train.model.getTrainID());
			Block currBlock = trainPosition.getCurrBlock();
			Line line = new Line(currBlock.getStartX()/9+10, currBlock.getStartY()/9+10, currBlock.getEndX()/9+10, currBlock.getEndY()/9+10);
			line.setStyle("-fx-stroke: #CC33FF; -fx-stroke-width: 3px;");
			System.out.println("Displaying the train");
				displayBox.getChildren().add(line);
			
			
		//}
			CTC.transitSystem.simulated = true;
			/*
			synchronized(CTC.transitSystem) {
				CTC.transitSystem.notify();
			}
			//*/
	}
	
	protected void makeObjectsSelectable()
	{
		
	}
	
	/**
	 * Display the legend
	 */
	protected void displayLegend()
	{
		trackLegendBox.getChildren().clear();
		trainLegendBox.getChildren().clear();
		
		for (int i = 0; i < CTC.tracks.size(); i++) 
		{
			// Display legend at the right of the track
			Rectangle trackSymbol = new Rectangle(20, 3);
			Label trackLabel = new Label(CTC.tracks.get(i).toString(), trackSymbol);
			trackLabel.getStyleClass().add("ctcLegend");
			String symbolStyle = "-fx-fill: " + CTC.tracks.get(i).getColor() + ";";
			trackSymbol.setStyle(symbolStyle);
			
			trackLegendBox.getChildren().add(trackLabel);
		}
		
		for (TrainController train : CTC.transitSystem.trains.values())
		{
			// Display trains in the legend
			Circle trainSymbol = new Circle(3);
			String trainString = CTC.trains.get(train.model.getTrainID());
			Label trainLabel = new Label(trainString, trainSymbol);
			trainLabel.getStyleClass().add("ctcLegend");
			trainSymbol.setStyle("-fx-fill: #FFFFFF;");
			
			trainLegendBox.getChildren().add(trainLabel);
		}
	}
	
	/**
	 * Display route information for selected train
	 * @param route
	 */
	public void displayRouteInfo(Route route)
	{
		Text train = new Text("Train " + route.getTrain().model.getTrainID() + ": ");
		Text routeInfo = new Text(route.toString());
		infoContainer.getChildren().clear();
		infoContainer.getChildren().addAll(train, routeInfo);
		CTC.ctcStage.sizeToScene();
	}
	
	/**
	 * Navigate to other views of the CTC
	 * @param event		identifies the button that was clicked
	 */
	//*
	@FXML protected void navigate(ActionEvent event) 
	{
		String fxmlFile = "";
		String title = "";
		Button buttonClicked = (Button) event.getSource();
		
		// Get the fxml file and the title of the new view
		if (buttonClicked == editTracksButton)
		{
			fxmlFile = trackGui;
			title = "CTC -- Track Manager";
		}
		else if (buttonClicked == navigateMainButton)
		{
			fxmlFile = "CTCView.fxml";
			title = "CTC";
		}
		else if (buttonClicked == editRoutesButton)
		{
			fxmlFile = routeGui;
			title = "CTC -- Train and Route Manager";
		}
		else
		{
			System.err.println("Didn't click a valid button??");
			return;
		}
		
		// Navigate to the new view
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
			CTC.ctcStage.setScene(new Scene(root));
			CTC.ctcStage.setTitle(title);
			CTC.ctcStage.show();
		} 
		catch (IOException e) 
		{
			System.err.println("Error loading " + buttonClicked.getText());
		}
	}
	//*/
	
	/**
	 * Displays a popup form whenever an appropriate button is pressed
	 * @param viewFile
	 * @param title
	 */
	@FXML protected void displayPopup(ActionEvent event) 
	{
		Button clickedButton = (Button) event.getSource();
		String viewFile = "";
		String title = "";
		
		if (clickedButton == addTrackButton)
		{
			viewFile = "addTrackForm.fxml";
			title = "Add New Track";
		}
		else if (clickedButton == addTrainButton)
		{
			viewFile = "addTrainForm.fxml";
			title = "Add New Train";
		}
		else if (clickedButton == routeTrainButton)
		{
			viewFile = "routeTrainForm.fxml";
			title = "Choose Track";
		}
		else if (clickedButton == suggestSetptButton)
		{
			viewFile = "suggestSetptForm.fxml";
			title = "Suggest Setpoint";
		}
		else
		{
			System.err.println("Didn't click a valid button??");
			return;
		}
		
		try 
		{
			Stage newStage = new Stage();
			Parent newRoot = FXMLLoader.load(getClass().getResource(viewFile));
			newStage.setScene(new Scene(newRoot));
			newStage.setTitle(title);
			newStage.show();
		} 
		catch (IOException e) 
		{
			System.out.println("Can't load " + title);
		}
	}
	
	/**
	 * TODO: implement this method
	 * @return
	 */
	public TrainController getSelectedTrain()
	{
		return null;
	}
}
