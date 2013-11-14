import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class deals with trains
 * @author meyling
 *
 */
public class CTCRouteController extends CTCMainController {
	private String addTrainFile = "addTrainForm.fxml";
	
	@FXML private TextField trainNameInput;
	@FXML private Text errorMessage;
	
	public CTCRouteController() {
		super();
	}
	
	public void initialize() {
		displayTrack();
		displayTrains();
		
		Button addTrainButton = new Button("Add train");
		Button navigateMainButton = new Button("Back to main");
		buttonContainer.getChildren().addAll(addTrainButton, navigateMainButton);
	
		navigateMainButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				navigateMain();
			}
		});
		
		addTrainButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				displayPopup(addTrainFile, "Add new train");
			}
		});
	}
	
	private boolean addTrain(String name) {
		Train newTrain = new Train(name);
		CTC.trains.add(newTrain);
		CTC.numTrains++;
		displayTrack();
		displayTrains();
		return true;
	}
	
	@FXML protected void cancel(ActionEvent event) {
		Node currNode = (Node) event.getSource();
		Stage currStage = (Stage) currNode.getScene().getWindow();
		currStage.close();
		navigateRoute();
	}
	
	@FXML private void submitNewTrain() {
		String trainName = trainNameInput.getText();
		
		if (addTrain(trainName)) {
			Stage currStage = (Stage) trainNameInput.getScene().getWindow();
			currStage.close();
			navigateRoute();
		}
		else
			errorMessage.setVisible(true);
	}
}