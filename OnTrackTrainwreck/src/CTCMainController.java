
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class CTCMainController extends VBox {
	FXMLLoader fxmlLoader;
	
	@FXML
	Pane displayBox;
	
	public CTCMainController() {
        fxmlLoader = new FXMLLoader(getClass().getResource("CTCView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
	
	/**
	 * Initializes this controller
	 */
	public void initialize() {
		System.out.println("Initializing the main CTC view");
		HBox buttonBox = new HBox();
		buttonBox.setSpacing(20);
		Button trackButton = new Button("Edit Tracks");
		if (CTC.currUser == null)
			trackButton.setDisable(true);
		trackButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				navigateTrack();
			}
		});
		Button routeButton = new Button("Edit Routes");
		buttonBox.getChildren().addAll(trackButton, routeButton);
		//this.getChildren().clear();
		this.getChildren().add(buttonBox);
	}
	
	/**
	 * Creates the track display
	 */
	protected void displayTrack() {
		
		for (int i = 0; i < CTC.blockCount; i++) {
			double startX = CTC.blockCoordinates.get(i)[0]/10+10;
			double startY = CTC.blockCoordinates.get(i)[1]/10+10;
			double endX = CTC.blockCoordinates.get(i)[2]/10+10;
			double endY = CTC.blockCoordinates.get(i)[3]/10+10;
			
			Line line = new Line(startX, startY, endX, endY);
			line.setStyle("-fx-fill: #FFFFFF;");
			displayBox.getChildren().add(line);
			//System.out.println(line.getStyleClass());
		}
		
	}
	
	/**
	 * Navigate to track view
	 */
	protected void navigateTrack() {
		Parent trackController = new CTCTrackController();
		CTC.ctcStage.setScene(new Scene(trackController));
	}
}
