/**
 * On Track Trainwreck
 * TrackMainController.java
 * Purpose: Controls the main GUI for the track model
 * 
 * @author Sarah Bunke
 * @version 2.0 12/12/13
 * 
 */
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



import javafx.scene.control.ListView;



public class TrackMainController {
	//GUI variables
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
		    private Label GreenCircuitDownLabel;

		    @FXML
		    private Label GreenpowerDownLabel;

		    @FXML
		    private Label RedCircuitDownLabel;

		    @FXML
		    private Label RedPowerDownLabel;
		    
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
		    private Button doneButton;

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
		    
		    @FXML
		    private Button trackStatsButton; 

		    @FXML
		    private Pane trackStatsWind;

		    private int internalBrokenRailID; //holds current broken block
		    private String internalBrokenRailLine; //line of current broken block
		    private String internalCircuitDownLine;
		    private String internalPowerDownLine;

		    /**
		     * GUI function that is called when user clicks break rail button, breaks up window to enter
		     * a block id to break
		     * @param event
		     */
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
		    
		    /**
		     * GUI function that is called when break track circuit button is called
		     * toggles status indicators appropriately 
		     * @param event
		     */
		    @FXML
		    void BreakTrackCircuitFunction(MouseEvent event) {
		    	if(breakTrackCircuitButton.getText().equals("Broken Circuit Fixed")){
		    		TrackObject t = Track.trackArray.get(internalCircuitDownLine);
		    		t.setCircuitDown(false);
		    		
		    		BrokenCircuitBlock.setVisible(true);
		        	BrokenCircuitRed.setVisible(false);
		        	breakTrackCircuitButton.setText("Break Track Circuit"); 		
		    	}
		    	else{
		    		try{
		    			internalCircuitDownLine = trackLineList.getSelectionModel().getSelectedItem();
		    			TrackObject t = Track.trackArray.get(internalCircuitDownLine);
		    			t.setCircuitDown(true);
		    			
		    			BrokenCircuitBlock.setVisible(false);
		    			BrokenCircuitRed.setVisible(true);
		    			breakTrackCircuitButton.setText("Broken Circuit Fixed");	
		    		}catch (Exception e){
		    			
		    		}
		    	}
		    }
		    
		    /**
		     * window that is brought when BreakRailFunction is called
		     * handles mouse events for when user enters a block id
		     * @param event
		     */
		    @FXML
		    void BreakWithId(MouseEvent event) {
		    	int id= Integer.parseInt(breakBlockIdText.getText());
		    	String line = trackLineList.getSelectionModel().getSelectedItem();
		    	int result = internalBreakWithId(id, line);
		    	if(result==1){
		    		BrokenRailBlock.setVisible(false);
		    		BrokenRailRed.setVisible(true);
		    		breakRailButton.setText("Broken Rail Fixed");	
		    	
		    		breakRailWindow.setVisible(false);
		    	}
		    	else{
		    		blockDNElabel.setVisible(true);
		    	}
		    }
		    
		    /**
		     * internal method that is called once user enters a block id to break
		     * 
		     * @param id -> id of block to break
		     * @param line ->track line block is on
		     * @return ->reflects success/failure
		     */
		    public int internalBreakWithId(int id, String line){
		    	Block b;	
		    	try {
		        	internalBrokenRailLine=line;
		    	
		    		b=Track.trackArray.get(line).getBlock(id);
		    		b.setClosed(true);
		    		internalBrokenRailID=id;
		    		return 1;

				} catch (Exception ex) {
					return 0;
					
				}
		    }

		    /**
		     * closes broken rail window
		     * @param event
		     */
		    @FXML
		    void CloseBrokenRailWindow(MouseEvent event) {
		    	breakRailWindow.setVisible(false);
		    }
		    
		    /**
		     * GUI function called when cut power button is clicked
		     * toggles status indicators appropriately
		     * @param event
		     */
		    @FXML
		    void CutPowerFunction(MouseEvent event) {
		    	if(CutPowerButton.getText().equals("Power Restored")){
		    		TrackObject t = Track.trackArray.get(internalPowerDownLine);
		    		t.setPowerDown(false);
		    		internalPowerDownLine=null;
		    		
		    		powerFailureBlock.setVisible(true);
		        	BrokenPowerRed.setVisible(false);
		        	CutPowerButton.setText("Cut Power");
		    	}
		    	else{
		    		try{
		    			internalPowerDownLine = trackLineList.getSelectionModel().getSelectedItem();
		    			TrackObject t = Track.trackArray.get(internalPowerDownLine);
		    			t.setPowerDown(true);
		    		
		    			powerFailureBlock.setVisible(false);
		    			BrokenPowerRed.setVisible(true);
		    			CutPowerButton.setText("Power Restored");
		    		}catch (Exception e){
		    			
		    		}
		    	}
		    	
		    }

		    /**
		     * GUI handler called when delete track button is clicked
		     * takes input from GUI to select which track to delete and 
		     * sends to internalDeleteTrack
		     * @param event
		     */
		    @FXML
		    void DeleteTrackFunction(MouseEvent event) {
		    	int trackToDeleteLine= trackLineList.getSelectionModel().getSelectedIndex();
		    	String lineName = trackLineList.getSelectionModel().getSelectedItem();
		    	
		    	if(lineName!= null){
		    		internalDeleteTrack(lineName, trackToDeleteLine);
		    		TrackMainController.trackLineList.setItems(Track.trackListData);
		    		if(Track.trackListData.isEmpty()){
		    			DeleteTrackButton.setDisable(true);
		    			queryBlockIDButton.setDisable(true);
		    			
		    			CutPowerButton.setDisable(true);
		    			breakTrackCircuitButton.setDisable(true);
		    			breakRailButton.setDisable(true);
		    			trackStatsButton.setDisable(true);
		    		}
		    	}
		    }
		    
		    /**
		     * Internal delete function
		     * @param line ->line to delete
		     * @param Num ->index to delete from trackArray
		     */
		    public void internalDeleteTrack(String line, int Num){
		    	Track.trackListData.remove(Num);
		    	
		    	Track.trackArray.remove(line);
		    }

		    /**
		     * GUI function to handle when user clicks query block by id button
		     * opens new stage from QueryBlock.fxml
		     * and passes control to ChildQueryController.java
		     * @param event
		     */
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

		    /**
		     * GUI function that handles when user clicks new track button
		     * opens newTrackAlertBox.fxml and passes control to NewTrackController.java
		     * @param event
		     */
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
		    		trackStatsButton.setDisable(false);
		    		
		    		CutPowerButton.setDisable(false);
		    		breakTrackCircuitButton.setDisable(false);
		    		breakRailButton.setDisable(false);
		    }
		    
		    /**
		     * closes track stats pane
		     * @param event
		     */
		    @FXML
		    void closeTrackStats(MouseEvent event) {
		    	GreenCircuitDownLabel.setText("N/A");
	    		GreenpowerDownLabel.setText("N/A");
	    		RedCircuitDownLabel.setText("N/A");
	    		RedPowerDownLabel.setText("N/A");
		    	trackStatsWind.setVisible(false);
		    }
		    
		    /**
		     * GUI function to open information about the track
		     * @param MouseEvent event
		     */
		    @FXML
		    void trackStatsMethod(MouseEvent event) {
		    	GreenCircuitDownLabel.setText("N/A");
	    		GreenpowerDownLabel.setText("N/A");
	    		RedCircuitDownLabel.setText("N/A");
	    		RedPowerDownLabel.setText("N/A");
		    	trackStatsWind.setVisible(true);
		    	
		    	
		    	TrackObject greenTrack = Track.trackArray.get("Green");
		    	if(greenTrack!=null){
		    		GreenCircuitDownLabel.setText(Boolean.toString(greenTrack.getCircuitDown()));
		    		GreenpowerDownLabel.setText(Boolean.toString(greenTrack.getPowerDown()));
		    	}
		    	TrackObject redTrack = Track.trackArray.get("Red");
		    	if(redTrack!=null){
		    		RedCircuitDownLabel.setText(Boolean.toString(redTrack.getCircuitDown()));
		    		RedPowerDownLabel.setText(Boolean.toString(redTrack.getPowerDown()));
		    	}
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
		        assert trackStatsButton != null : "fx:id=\"trackStatsButton\" was not injected: check your FXML file 'GUI.fxml'.";
		        assert trackStatsWind != null : "fx:id=\"trackStatsWind\" was not injected: check your FXML file 'GUI.fxml'.";
		        assert GreenCircuitDownLabel != null : "fx:id=\"GreenCircuitDownLabel\" was not injected: check your FXML file 'GUI.fxml'.";
		        assert GreenpowerDownLabel != null : "fx:id=\"GreenpowerDownLabel\" was not injected: check your FXML file 'GUI.fxml'.";
		        assert RedCircuitDownLabel != null : "fx:id=\"RedCircuitDownLabel\" was not injected: check your FXML file 'GUI.fxml'.";
		        assert RedPowerDownLabel != null : "fx:id=\"RedPowerDownLabel\" was not injected: check your FXML file 'GUI.fxml'.";
		        assert doneButton != null : "fx:id=\"doneButton\" was not injected: check your FXML file 'GUI.fxml'.";
		        

		    }

}
