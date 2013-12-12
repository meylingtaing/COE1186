/**
 * On Track Trainwreck
 * ChildQueryController.java
 * Purpose: Controls the GUI to interface with querying for a block by ID.
 * Returns stats by block
 * 
 * @author Sarah Bunke
 * @version 2.0 12/12/13
 * 
 */
package trackModel;




import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class ChildQueryController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private AnchorPane BlockInfoPane;

	@FXML
	private TextField BlockNumberField;

	@FXML
	private Label BlockNumberInfoField;

	@FXML
	private Label SpeedLimitInfoField;

	@FXML
	private Label StationInfoField;
	
	@FXML
    private Label blockIDRangeErrorLabel;

	@FXML
	private Button childQueryButton;

	@FXML
	private Label gradeInfofield;

	@FXML
    private Label isClosedField;
	
	@FXML
	private Label lengthInfoField;

	@FXML
	private Label railwayCrossingField;

	@FXML
	private Label sectionInfoField;

	@FXML
	private Label siwtchInfoField;
	
	@FXML
    private Label stationName;

	@FXML
    private Label trackDNELabel;
	
	@FXML
	private TextField trackLineField;
	
	 @FXML
	    private Label trainDetecedField;

	@FXML
	private Label undergroundInfoField;

	
	/**when query button is clicked, returns block information, handles errors
     * 
     * @param MouseEvent event
     */
	@FXML
	void getBlockInfo(MouseEvent event) {
		String trackLine = trackLineField.getText();
		String blockNumString = BlockNumberField.getText();
		int blockNum;

		if(!Track.trackListData.contains(trackLine) || trackLine.equals("") || blockNumString.equals("")){
			trackDNELabel.setVisible(true);
			BlockInfoPane.setVisible(false);

		}
		else{
			trackDNELabel.setVisible(false);
			blockNum = Integer.parseInt(blockNumString);
			TrackObject currTrack = Track.trackArray.get(trackLine);
			//try{
				Block desiredBlock = currTrack.getBlock(blockNum);
				blockIDRangeErrorLabel.setVisible(false);
				BlockNumberInfoField.setText(Integer.toString(blockNum));
				SpeedLimitInfoField.setText(Integer.toString(desiredBlock.getSpeedLimit()));
				gradeInfofield.setText(Double.toString(desiredBlock.getGrade()));
				StationInfoField.setText(Boolean.toString(desiredBlock.isStation()));
				railwayCrossingField.setText(Boolean.toString(desiredBlock.isCrossing()));
				siwtchInfoField.setText(Boolean.toString(desiredBlock.isSwitchBoo()));
				undergroundInfoField.setText(Boolean.toString(desiredBlock.isUnderground()));
				lengthInfoField.setText(Double.toString(desiredBlock.getLength()));
				sectionInfoField.setText(desiredBlock.getSection());
				trainDetecedField.setText(Boolean.toString(desiredBlock.isTrainDetected()));
				stationName.setText(desiredBlock.getStationName());
				isClosedField.setText(Boolean.toString(desiredBlock.isClosed()));


				BlockInfoPane.setVisible(true);
			/*}catch(NullPointerException e){
				BlockInfoPane.setVisible(false);
				blockIDRangeErrorLabel.setVisible(true);
				
			}
			*/
			
		}

	}

	@FXML
    void initialize() {
        assert BlockInfoPane != null : "fx:id=\"BlockInfoPane\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert BlockNumberField != null : "fx:id=\"BlockNumberField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert BlockNumberInfoField != null : "fx:id=\"BlockNumberInfoField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert SpeedLimitInfoField != null : "fx:id=\"SpeedLimitInfoField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert StationInfoField != null : "fx:id=\"StationInfoField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert childQueryButton != null : "fx:id=\"childQueryButton\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert gradeInfofield != null : "fx:id=\"gradeInfofield\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert lengthInfoField != null : "fx:id=\"lengthInfoField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert railwayCrossingField != null : "fx:id=\"railwayCrossingField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert sectionInfoField != null : "fx:id=\"sectionInfoField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert siwtchInfoField != null : "fx:id=\"siwtchInfoField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert trackDNELabel != null : "fx:id=\"trackDNELabel\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert trackLineField != null : "fx:id=\"trackLineField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert undergroundInfoField != null : "fx:id=\"undergroundInfoField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert blockIDRangeErrorLabel != null : "fx:id=\"blockIDRangeErrorLabel\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert trainDetecedField != null : "fx:id=\"trainDetecedField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert stationName != null : "fx:id=\"stationName\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert isClosedField != null : "fx:id=\"isClosedField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
	}
}
