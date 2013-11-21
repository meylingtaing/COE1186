package trainmodule;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
//import javafx.concurrent.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewController
{
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML 
    private TextField nextStop;
    
    @FXML 
    private TextField currentStop;

    @FXML 
    private TableView trainTable;
       
    @FXML 
    private TableColumn idColumn;
    
    @FXML 
    private TableColumn setColumn;
    
    @FXML 
    private TableColumn speedColumn;
    
    @FXML 
    private TableColumn accColumn;
    
    @FXML 
    private TableColumn passColumn;
    
    @FXML 
    private TableColumn tempColumn;
    
    @FXML 
    private TableColumn doorColumn;
    
    @FXML 
    private TableColumn lightColumn;
    
    @FXML 
    private TableColumn failColumn;
    
    @FXML 
    private ComboBox trainChoice;

    public static ObservableList<TrainModel> data;
    private ObservableList<Integer> trains;
    private TrainModel selectedTrain;
    
    @FXML
    void initialize()
    {
    	/*data = FXCollections.observableArrayList(
    				new TrainModel(new Route(new Stack<String>()), 70.2, "SUPERUSER"), 
    				new TrainModel(new Route(new Stack<String>()), 69.2, "ENGINEER"), 
    				new TrainModel(new Route(new Stack<String>()), 67.4, "?????????")
    				);*/
    	
    	//data = FXCollections.observableArrayList();
    	//TrainModel.setViewController(this);
    	
    	//addTrain(new Route(new Stack<String>()), 70.2, "SUPERUSER");
    	//addTrain(new Route(new Stack<String>()), 69.2, "ENGINEER");
    	//addTrain(new Route(new Stack<String>()), 67.4, "?????????");
    	/*try {
			Thread.sleep(00);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, Integer>("trainID"));
    	setColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("setpoint"));
    	speedColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("speed"));
    	doorColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("doorValue"));
    	lightColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("lightValue"));
    	passColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, Integer>("passengerNumber"));
    	tempColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("temp"));
    	failColumn.setCellValueFactory(new PropertyValueFactory<TrainModel, String>("fail"));    	
    	
    	//addTrain(new Route(new Stack<String>()), 68.4, "Tester");
    	
    	//Start a thread that updates the GUI!!!!!!
    	
    	//while (true)
    	//{
    		/*Timer t = new Timer();
    		long delay = 100;
    		long period = 100;
    		Updater update = new Updater();
    		t.schedule(update, delay, period);*/
    	//}
    	
    	updateGUI();
    }
    
    public ObservableList<TrainModel> getActiveTrains()
    {
    	return data;
    }
    
    public void removeTrain(int i)
    {
    	for (TrainModel tm : data)
    	{
    		if (trainChoice.getValue().equals(tm.getTrainID()))
    		{
    			data.remove(tm);
    		}
    	} 
    	
    	//updateGUI();
    }
    
    public void addTrain(TrainModel t)
    {
    	data.add(t);
    	//updateGUI();
    }
    
    public void addTrain(/*ctc.Route r,*/ double t, String c)
    {
    	data.add(new TrainModel(0.001, /*r,*/ t, c));
    	//updateGUI();
    }
    
    public void updateGUI()
    {     	
    	trains = FXCollections.observableArrayList();
    	
    	for (TrainModel tm : data)
    	{
    		if (selectedTrain == null)
    			selectedTrain = tm;
    		trains.add(tm.getTrainID());
    	}
    	
    	//trainChoice.setItems(null);
    	//System.out.println("get past");
    	trainChoice.setItems(trains);
    	
    	if (selectedTrain != null)
    		trainChoice.setValue(selectedTrain.getTrainID());    	
    	
    	//trainTable.setItems(null);
    	trainTable.setItems(data);
    	
    	/*ObservableList<TrainModel> temp = FXCollections.observableArrayList();
    	
    	for (TrainModel t : data)
    	{
    		temp.add(t);
    		data.remove(t);
    	}
    	
    	for (TrainModel t : temp)
    	{
    		data.add(t);
    		temp.remove(t);
    	}*/
    	
    	((TableColumn)(trainTable.getColumns()).get(0)).setVisible(false);
    	((TableColumn)(trainTable.getColumns()).get(0)).setVisible(true);
    	
    	System.out.println("GUI REFRESH");
    }
    
    public void currentTrain()
    {   	
    	for (TrainModel tm : data)
    	{
    		if (trainChoice.getValue().equals(tm.getTrainID()))
    		{
    			//tm.refreshGUI();
    			//TrainModel.refreshGUI();
    			selectedTrain = tm;
    			//System.out.println(tm.getTrainID() + " " + trainChoice.getValue());
    		}
    	}    	
    	//System.out.println("changed to " + selectedTrain.getTrainID());
    }
    
    public void throwEngineFailure()
    {
    	if (selectedTrain != null && !selectedTrain.isEngineBroken())
    	{
    		System.out.println("TrainID: " + selectedTrain.getTrainID());
    		selectedTrain.throwEngineFailure();
    		updateGUI();
    	}
    	else
    		System.out.println("No selection");
    }
    
    public void throwSignalFailure()
    {
    	if (selectedTrain != null && !selectedTrain.isSignalBroken())
    	{
	    	selectedTrain.throwSignalFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    public void throwBrakeFailure()
    {
    	if (selectedTrain != null && !selectedTrain.isBrakeBroken())
    	{
	    	selectedTrain.throwBrakeFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    public void fixEngineFailure()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.fixEngineFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    public void fixSignalFailure()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.fixSignalFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    public void fixBrakeFailure()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.fixBrakeFailure();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    public void fixAllFailures()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.fixAllFailures();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    public void engageEBrake()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.pullEBrake();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
    
    public void disengageEBrake()
    {
    	if (selectedTrain != null)
    	{
	    	selectedTrain.releaseEBrake();
	    	updateGUI();
	    }
		else
			System.out.println("No selection");
    }
}