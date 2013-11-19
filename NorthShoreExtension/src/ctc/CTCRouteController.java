/**
 * Controller for train and routing manager for CTC
 * 
 * @author meyling
 */
package ctc;

import trainModel.TrainModel;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class CTCRouteController extends CTCController {
	
	@FXML private Button removeTrainButton;
	
	private Label selectedTrainLegend;
	
	public void initialize()
	{	
		CTC.ctcController = this;
		
		if (!CTC.currUser.isDispatcher())
			addTrainButton.setDisable(true);
		
		selectLegendHandler = new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event)
			{
				Node trainLegend = (Node) event.getSource();
				trainLegend.setStyle("-fx-border-width: 1px; -fx-border-color: #FFFFFF;");
				
				if (CTC.currUser.isTrackCreator())
				{
					removeTrainButton.setDisable(false);
					routeTrainButton.setDisable(false);
				}
					
				
				if (selectedTrainLegend == trainLegend)
				{
					selectedTrainLegend.setStyle("-fx-border-width: 0px;");
					selectedTrainLegend = null;
					removeTrainButton.setDisable(true);
					routeTrainButton.setDisable(true);
				}
				
				else 
				{
					if (selectedTrainLegend != null)
						selectedTrainLegend.setStyle("-fx-border-width: 0px;");
					
					selectedTrainLegend = (Label) trainLegend;
				}
			}
		};
		
		displayTrack();
		displayLegend();
	}
	
	protected void displayLegend()
	{
		super.displayLegend();
		ObservableList<Node> trainLegends = trainLegendBox.getChildren();
		for (final Node trainLegend : trainLegends)
		{
			// Make the legend click-able
			System.out.println("Adding a clickable ");
			trainLegend.setOnMouseClicked(selectLegendHandler);
			System.out.println("Added the handler for legend");
		}
	}
	
	@FXML private void removeTrain()
	{
		if (selectedTrainLegend == null)
			return;
		
		// Remove from transit system
		for (TrainModel train : CTC.transitSystem.trains)
		{
			if (train.getName().equals(selectedTrainLegend.getText()))
			{
				CTC.transitSystem.trains.remove(train);
				break;
			}
		}
		
		selectedTrainLegend = null;
		removeTrainButton.setDisable(true);
		displayLegend();
		displayTrack();
	}
}
