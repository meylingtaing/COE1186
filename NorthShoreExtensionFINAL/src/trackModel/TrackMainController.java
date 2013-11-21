package trackModel;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



import javafx.scene.control.ListView;



public class TrackMainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Rectangle BrokenCircuitBlock;

    @FXML
    private Rectangle BrokenCircuitRed;

    @FXML
    private Rectangle BrokenPowerRed;

    @FXML
    private Rectangle BrokenRailBlock;

    @FXML
    private Rectangle BrokenRailRed;

    @FXML
    private Button CutPowerButton;

    @FXML
    private Button DeleteTrackButton;

    @FXML
    private Button breakRailButton;

    @FXML
    private Button breakTrackCircuitButton;

    @FXML
    private Button inputTrackButton;

    @FXML
    private Label label;

    @FXML
    private Rectangle powerFailureBlock;

    @FXML
    private Button queryBlockIDButton;

    @FXML
    protected static ListView<String> trackLineList;


    @FXML
    void BreakRailFunction(MouseEvent event) {
    	if(breakRailButton.getText().equals("Broken Rail Fixed")){
    		BrokenRailBlock.setVisible(true);
        	BrokenRailRed.setVisible(false);
        	breakRailButton.setText("Break Rail");
    	}
    	else{
    		BrokenRailBlock.setVisible(false);
        	BrokenRailRed.setVisible(true);
        	breakRailButton.setText("Broken Rail Fixed");	
    	}
    	
    }

    @FXML
    void BreakTrackCircuitFunction(MouseEvent event) {
    	if(breakTrackCircuitButton.getText().equals("Broken Circuit Fixed")){
    		BrokenCircuitBlock.setVisible(true);
        	BrokenCircuitRed.setVisible(false);
        	breakTrackCircuitButton.setText("Break Track Circuit"); 		
    	}
    	else{
    		BrokenCircuitBlock.setVisible(false);
        	BrokenCircuitRed.setVisible(true);
        	breakTrackCircuitButton.setText("Broken Circuit Fixed");	
    	}
    }

    @FXML
    void CutPowerFunction(MouseEvent event) {
    	if(CutPowerButton.getText().equals("Power Restored")){
    		powerFailureBlock.setVisible(true);
        	BrokenPowerRed.setVisible(false);
        	CutPowerButton.setText("Cut Power");
    	}
    	else{
    		powerFailureBlock.setVisible(false);
        	BrokenPowerRed.setVisible(true);
        	CutPowerButton.setText("Power Restored");
    	}
    	
    }

    @FXML
    void DeleteTrackFunction(MouseEvent event) {
    	int trackToDeleteLine= trackLineList.getSelectionModel().getSelectedIndex();
    	String lineName = trackLineList.getSelectionModel().getSelectedItem();
    	
    	Track.trackListData.remove(trackToDeleteLine);
    	TrackMainController.trackLineList.setItems(Track.trackListData);
    	Track.trackArray.remove(lineName);
    	if(Track.trackListData.isEmpty()){
    		DeleteTrackButton.setDisable(true);
    		queryBlockIDButton.setDisable(true);
    	}
    	
    }

    @FXML
    void QueryBlockIdFunction(MouseEvent event) {
    	Stage dialog = new Stage();
    	dialog.initStyle(StageStyle.UTILITY);
    	dialog.setHeight(500);
    	dialog.setWidth(500);
    		try {
    			AnchorPane page = (AnchorPane) FXMLLoader.load(TrackMainController.class.getResource("QueryBlock.fxml"));
    			Scene scene = new Scene(page);
    			dialog.setScene(scene);
    			dialog.setTitle("Track Model-QueryBlock");
    			dialog.show();


    		} catch (Exception ex) {
    			System.out.println(ex);
    		}
    	
    }

    @FXML
    void inputTrackFunction(MouseEvent event) {
    	
    	Stage dialog = new Stage();
    	dialog.initStyle(StageStyle.UTILITY);
    	dialog.setHeight(200);
    	dialog.setWidth(500);
    		try {
    			AnchorPane page = (AnchorPane) FXMLLoader.load(TrackMainController.class.getResource("newTrackAlertBox.fxml"));
    			Scene scene = new Scene(page);
    			dialog.setScene(scene);
    			dialog.setTitle("Track Model");
    			dialog.show();


    		} catch (Exception ex) {
    			System.out.println(ex);
    		}
    		DeleteTrackButton.setDisable(false);
    		queryBlockIDButton.setDisable(false);
    }

    @FXML
    void initialize() {
        assert BrokenCircuitBlock != null : "fx:id=\"BrokenCircuitBlock\" was not injected: check your FXML file 'GUI.fxml'.";
        assert BrokenCircuitRed != null : "fx:id=\"BrokenCircuitRed\" was not injected: check your FXML file 'GUI.fxml'.";
        assert BrokenPowerRed != null : "fx:id=\"BrokenPowerRed\" was not injected: check your FXML file 'GUI.fxml'.";
        assert BrokenRailBlock != null : "fx:id=\"BrokenRailBlock\" was not injected: check your FXML file 'GUI.fxml'.";
        assert BrokenRailRed != null : "fx:id=\"BrokenRailRed\" was not injected: check your FXML file 'GUI.fxml'.";
        assert CutPowerButton != null : "fx:id=\"CutPowerButton\" was not injected: check your FXML file 'GUI.fxml'.";
        assert DeleteTrackButton != null : "fx:id=\"DeleteTrackButton\" was not injected: check your FXML file 'GUI.fxml'.";
        assert breakRailButton != null : "fx:id=\"breakRailButton\" was not injected: check your FXML file 'GUI.fxml'.";
        assert breakTrackCircuitButton != null : "fx:id=\"breakTrackCircuitButton\" was not injected: check your FXML file 'GUI.fxml'.";
        assert inputTrackButton != null : "fx:id=\"inputTrackButton\" was not injected: check your FXML file 'GUI.fxml'.";
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'GUI.fxml'.";
        assert powerFailureBlock != null : "fx:id=\"powerFailureBlock\" was not injected: check your FXML file 'GUI.fxml'.";
        assert queryBlockIDButton != null : "fx:id=\"queryBlockIDButton\" was not injected: check your FXML file 'GUI.fxml'.";
        assert trackLineList != null : "fx:id=\"trackLineList\" was not injected: check your FXML file 'GUI.fxml'.";


    }

}
