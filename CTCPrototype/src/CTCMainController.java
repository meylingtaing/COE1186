
import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CTCMainController extends VBox {
	private FXMLLoader fxmlLoader;
	private final String view = "CTCView.fxml";
	
	@FXML Pane displayBox;
	
	@FXML HBox buttonContainer;
	
	@FXML VBox infoContainer, legendBox;
	
	public CTCMainController() {
		// Connect to the display
        fxmlLoader = new FXMLLoader(getClass().getResource(view));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
        	System.out.println("Error: Couldn't load display");
        	exception.printStackTrace();
            System.exit(-1);
        }
    }
	
	/**
	 * Initializes this controller
	 */
	public void initialize() {

		// Add all of the buttons
		Button trackButton = new Button("Edit Tracks");
		Button routeButton = new Button("Edit Routes");
		
		if (CTC.currUser == null) {
			trackButton.setDisable(true);
			routeButton.setDisable(true);
		}
		
		buttonContainer.getChildren().addAll(trackButton, routeButton);
		
		displayTrack();
		displayTrains();
		
		trackButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				navigateTrack();
			}
		});
		
		routeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				navigateRoute();
			}
		});
	}
	
	protected void displayTrains() {
		for (int i = 0; i < CTC.numTrains; i++) {
			Label trainName = new Label(CTC.trains.get(i).name);
			//trainName.setStyle("-fx-text-fill: #FFFFFF");
			System.out.println("Displaying train " + CTC.trains.get(i).name);
			legendBox.getChildren().add(trainName);
		}
	}
	
	/**
	 * Creates the track display
	 */
	protected void displayTrack() {
		// Clear display first
		displayBox.getChildren().clear();
		legendBox.getChildren().clear();
		
		for (int i = 0; i < CTC.numTracks; i++) {
		
			for (final Block block: CTC.tracks[i]) {
				double startX = block.startX/10+10;
				double startY = block.startY/10+10;
				double endX = block.endX/10+10;
				double endY = block.endY/10+10;
				
				//*/ For crisp looking lines
				startX = Math.floor(startX) + .5;
				startY = Math.floor(startY) + .5;
				endX = Math.floor(endX) + .5;
				endY = Math.floor(endY) + .5;
				//*/
				Line line = new Line(startX, startY, endX, endY);
				
				String strokeStyle;
				if (block.hasStation()) {
					strokeStyle = "-fx-stroke: #FFFFFF; -fx-stroke-width: 1px;";
					line.setStrokeType(StrokeType.OUTSIDE);
					line.setTranslateX(-.5);
					line.setTranslateY(-.5);
					
					final Popup stationPopup = new Popup();
					// Add a mouseover for the line
					line.setOnMouseEntered(new EventHandler<MouseEvent>() {
						public void handle (MouseEvent event) {
							Label stationName = new Label(" " + block.stationName + " ");
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
					strokeStyle = "-fx-stroke: " + CTC.tracks[i].color + ";";
				
				line.setStyle(strokeStyle);
				line.setVisible(true);
				displayBox.getChildren().add(line);
			}
			
			// Display legend at the right of the track
			HBox trackLegend = new HBox();
			Rectangle trackSymbol = new Rectangle(20, 3);
			Label trackLabel = new Label(CTC.tracks[i].toString(), trackSymbol);
			String symbolStyle = "-fx-fill: " + CTC.tracks[i].color + ";";
			trackSymbol.setStyle(symbolStyle);
			
			trackLegend.getChildren().addAll(trackLabel);
			legendBox.getChildren().add(trackLegend);
		}
	}
	
	protected void navigateTrack() {
		Parent trackController = new CTCTrackController();
		CTC.ctcStage.setScene(new Scene(trackController));
	}
	
	protected void navigateRoute() {
		Parent routeController = new CTCRouteController();
		CTC.ctcStage.setScene(new Scene(routeController));
	}
	
	protected void navigateMain() {
		Parent mainController = new CTCMainController();
		CTC.ctcStage.setScene(new Scene(mainController));
	}
	
	protected void displayPopup(String viewFile, String title) {
		try {
			Stage newStage = new Stage();
			Parent newRoot = FXMLLoader.load(new File(viewFile).toURI().toURL());
			newStage.setScene(new Scene(newRoot));
			newStage.setTitle(title);
			newStage.show();
		} catch (IOException e) {
			System.out.println("Can't load " + title);
		}
	}
}
