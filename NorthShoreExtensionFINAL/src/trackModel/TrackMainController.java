package trackModel;


import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
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
	    private Button BreakButton;
	    
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
	    private TextField breakBlockIdText;
	    
	    @FXML
	    private Button breakRailButton;
	    
	    @FXML
	    private TitledPane breakRailWindow;

	    @FXML
	    private Button breakTrackCircuitButton;
	    
	    @FXML
	    private Button cancelBreakTrackButton;

	    @FXML
	    private Button inputTrackButton;
	    
	    @FXML
	    private Label blockDNElabel;

	    @FXML
	    private Label label;

	    @FXML
	    private Rectangle powerFailureBlock;

	    @FXML
	    private Button queryBlockIDButton;

	    @FXML
	    protected static ListView<String> trackLineList;

	    private int internalBrokenRailID;
	    private String internalBrokenRailLine;

	    @FXML
	    void BreakRailFunction(MouseEvent event) {
	    	blockDNElabel.setVisible(false);
	    	if(breakRailButton.getText().equals("Break Rail"))
	    	{
	    		breakRailWindow.setVisible(true);
	    	}
	    	else{
	    		Block b;
	        	b=Track.trackArray.get(internalBrokenRailLine).getBlock(internalBrokenRailID);
	        	b.setClosed(false);
	        	
	        	BrokenRailBlock.setVisible(true);
	        	BrokenRailRed.setVisible(false);
	        	breakRailButton.setText("Break Rail");
	    		
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
	    void BreakWithId(MouseEvent event) {
	    	int id= Integer.parseInt(breakBlockIdText.getText());
	    	
	    	Block b;
	    		
	    	
	    	try {
	    		String line = trackLineList.getSelectionModel().getSelectedItem();
	        	internalBrokenRailLine=line;
	    	
	    		b=Track.trackArray.get(line).getBlock(id);
	    		b.setClosed(true);
	    		internalBrokenRailID=id;
	    		
	    		BrokenRailBlock.setVisible(false);
	    		BrokenRailRed.setVisible(true);
	    		breakRailButton.setText("Broken Rail Fixed");	
	    	
	    		breakRailWindow.setVisible(false);


			} catch (Exception ex) {
				blockDNElabel.setVisible(true);
			}
	    }

	    @FXML
	    void CloseBrokenRailWindow(MouseEvent event) {
	    	breakRailWindow.setVisible(false);
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
	    	
	    	internalDeleteTrack(lineName, trackToDeleteLine);
	    	TrackMainController.trackLineList.setItems(Track.trackListData);
	    	if(Track.trackListData.isEmpty()){
	    		DeleteTrackButton.setDisable(true);
	    		queryBlockIDButton.setDisable(true);
	    		
	    		CutPowerButton.setDisable(true);
	    		breakTrackCircuitButton.setDisable(true);
	    		breakRailButton.setDisable(true);
	    	}
	    	
	    }
	    
	    public void internalDeleteTrack(String line, int Num){
	    	Track.trackListData.remove(Num);
	    	
	    	Track.trackArray.remove(line);
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
	    		
	    		CutPowerButton.setDisable(false);
	    		breakTrackCircuitButton.setDisable(false);
	    		breakRailButton.setDisable(false);
	    }

	    @FXML
	    void initialize() {
	    	assert BreakButton != null : "fx:id=\"BreakButton\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert BrokenCircuitBlock != null : "fx:id=\"BrokenCircuitBlock\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert BrokenCircuitRed != null : "fx:id=\"BrokenCircuitRed\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert BrokenPowerRed != null : "fx:id=\"BrokenPowerRed\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert BrokenRailBlock != null : "fx:id=\"BrokenRailBlock\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert BrokenRailRed != null : "fx:id=\"BrokenRailRed\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert CutPowerButton != null : "fx:id=\"CutPowerButton\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert DeleteTrackButton != null : "fx:id=\"DeleteTrackButton\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert breakBlockIdText != null : "fx:id=\"breakBlockIdText\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert breakRailButton != null : "fx:id=\"breakRailButton\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert breakTrackCircuitButton != null : "fx:id=\"breakTrackCircuitButton\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert inputTrackButton != null : "fx:id=\"inputTrackButton\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert powerFailureBlock != null : "fx:id=\"powerFailureBlock\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert queryBlockIDButton != null : "fx:id=\"queryBlockIDButton\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert trackLineList != null : "fx:id=\"trackLineList\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert breakRailWindow != null : "fx:id=\"breakRailWindow\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert cancelBreakTrackButton != null : "fx:id=\"cancelBreakTrackButton\" was not injected: check your FXML file 'GUI.fxml'.";
	        assert blockDNElabel != null : "fx:id=\"blockDNElabel\" was not injected: check your FXML file 'GUI.fxml'.";
	    
	    }

}
