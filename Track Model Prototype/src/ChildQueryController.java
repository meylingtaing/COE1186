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
    private Button childQueryButton;

    @FXML
    private Label gradeInfofield;
    
    @FXML
    private Label lengthInfoField;

    @FXML
    private Label railwayCrossingField;
    
    @FXML
    private Label sectionInfoField;

    @FXML
    private Label siwtchInfoField;

    @FXML
    private TextField trackLineField;

    @FXML
    private Label undergroundInfoField;


    @FXML
    void getBlockInfo(MouseEvent event) {
    	String trackLine = trackLineField.getText();
    	int blockNum = Integer.parseInt(BlockNumberField.getText());
    	
    	TrackObject currTrack = Track.trackArray.get(trackLine);
    	Block desiredBlock = currTrack.getBlock(blockNum);
    	
    	BlockNumberInfoField.setText(Integer.toString(blockNum));
    	SpeedLimitInfoField.setText(Integer.toString(desiredBlock.getSpeedLimit()));
    	gradeInfofield.setText(Double.toString(desiredBlock.getGrade()));
    	StationInfoField.setText(Boolean.toString(desiredBlock.isStation()));
    	railwayCrossingField.setText(Boolean.toString(desiredBlock.isCrossing()));
    	siwtchInfoField.setText(Boolean.toString(desiredBlock.isSwitchBoo()));
    	undergroundInfoField.setText(Boolean.toString(desiredBlock.isUnderground()));
    	lengthInfoField.setText(Double.toString(desiredBlock.getLength()));
    	sectionInfoField.setText(desiredBlock.getSection());
    	
    	
    	BlockInfoPane.setVisible(true);
    	
    	
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
        assert trackLineField != null : "fx:id=\"trackLineField\" was not injected: check your FXML file 'QueryBlock.fxml'.";
        assert undergroundInfoField != null : "fx:id=\"undergroundInfoField\" was not injected: check your FXML file 'QueryBlock.fxml'.";


    }

}
