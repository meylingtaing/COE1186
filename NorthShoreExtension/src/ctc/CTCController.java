/**
 * Main controller class for CTC
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
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CTCController 
{
	@FXML protected Button editTracksButton, editRoutesButton, addTrackButton, 
							navigateMainButton, addTrainButton;
	@FXML protected Pane displayBox;
	@FXML protected VBox legendBox, trackLegendBox, trainLegendBox;
	
	private String trackGui = "CTCTrackView.fxml";
	private String routeGui = "CTCRouteView.fxml";
	
	protected EventHandler<MouseEvent> selectLegendHandler;
	
	public void initialize() 
	{
		CTC.ctcController = this;
		
		// Based on permissions of user, disable certain buttons
		if (!CTC.currUser.isTrackCreator() && !CTC.currUser.isMaintenance())
			editTracksButton.setDisable(true);
		
		displayTrack();
		
		// Make the event handler for selecting a legend
		selectLegendHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event)
			{
				
			}
		};
	}
	
	/**
	 * Creates the track display
	 */
	protected void displayTrack() 
	{
		// Clear display first
		displayBox.getChildren().clear();
		
		for (int i = 0; i < CTC.tracks.size(); i++) {
			int j = 0;
			for (final Double[] block: CTC.tracks.get(i)) 
			{
				double startX = block[0]/10+10;
				double startY = block[1]/10+10;
				double endX = block[2]/10+10;
				double endY = block[3]/10+10;
				
				//*/ For crisp looking lines
				startX = Math.floor(startX) + .5;
				startY = Math.floor(startY) + .5;
				endX = Math.floor(endX) + .5;
				endY = Math.floor(endY) + .5;
				//*/
				Line line = new Line(startX, startY, endX, endY);
				
				String strokeStyle;
				// TODO: better integration stuff
				final Block currBlock = CTC.transitSystem.track.trackArray.get(CTC.tracks.get(i).toString()).getBlock(j);
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
					
				}
				else
					strokeStyle = "-fx-stroke: " + CTC.tracks.get(i).getColor() + ";";
				
				line.setStyle(strokeStyle);
				line.setVisible(true);
				displayBox.getChildren().add(line);
				
				j++;
			}
		}
		
		displayLegend();
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
		
		for (int i = 0; i < CTC.tracks.size(); i++) {
			// Display legend at the right of the track
			Rectangle trackSymbol = new Rectangle(20, 3);
			Label trackLabel = new Label(CTC.tracks.get(i).toString(), trackSymbol);
			trackLabel.getStyleClass().add("ctcLegend");
			String symbolStyle = "-fx-fill: " + CTC.tracks.get(i).getColor() + ";";
			trackSymbol.setStyle(symbolStyle);
			
			trackLegendBox.getChildren().add(trackLabel);
		}
		
		for (int i = 0; i < CTC.transitSystem.trains.size(); i++)
		{
			// Display trains in the legend
			Circle trainSymbol = new Circle(3);
			Label trainLabel = new Label(CTC.transitSystem.trains.get(i).getName(), trainSymbol);
			trainLabel.getStyleClass().add("ctcLegend");
			trainSymbol.setStyle("-fx-fill: #FFFFFF;");
			
			trainLegendBox.getChildren().add(trainLabel);
		}
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
}
