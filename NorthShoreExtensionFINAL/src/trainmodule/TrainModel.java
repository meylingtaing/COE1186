/**
 * trainmodule
 *
 * Version 1
 *
 * This code was developed by Keith Payne
 */

package trainmodule;

import java.util.*;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TrainModel
{
	private DoorController doors;
	private LightController lights;
	private PassengerManager passengers;
	private EngineModel engine;
	public static ViewController vc;
	//private ctc.Route route;
	private TemperatureController temperature;
	private double trainMass = 37103.9;
	private double length = 32.2;
	private String conductor;
	//private static int clockSpeed;
	private TrainFailure failure;
	private boolean eBrake = false;
	
	//DEBUG VARIABLES
	private static int trainIDGenerator = 0;
	
	//TABLE COLUMN VARIABLES
	private SimpleIntegerProperty trainID;
	
	public static void main(String args[])
	{
		TrainView.createGUI();	
	}
	
	public TrainModel(int id)
	{
		this.trainID = new SimpleIntegerProperty(id);
		doors = new DoorController();
		lights= new LightController();
		passengers = new PassengerManager();
		engine = new EngineModel(0.1, trainID.getValue());
		temperature = new TemperatureController(72);
		failure = new TrainFailure();
		conductor = "engineer";
	}
	
	public TrainModel(double tick, /*ctc.Route trip,*/ double t, String engineer)
	{
		this(tick, t, engineer, 0);
	}
	
	public TrainModel(double tick, /*ctc.Route trip,*/ double t, String engineer, int id)
	{
		doors = new DoorController();
		lights= new LightController();
		passengers = new PassengerManager();
		trainID = new SimpleIntegerProperty(trainIDGenerator);
		engine = new EngineModel(tick, trainID.getValue());
		temperature = new TemperatureController(t);
		failure = new TrainFailure();
		conductor = engineer;

		//INITIALIZE TABLE VARIABLES
		
		this.trainID = new SimpleIntegerProperty(id);
	}
		
	public int getTrainID()
	{
        return trainID.get();
    }
    
    public String getSetpoint()
	{
        return engine.getSetpoint() + " watts";
    }
	
    public double setSetpoint(double d)
    {    	
    	double speed;
    	
    	if (!eBrake)
    		speed = engine.calculateSetpoint(d, (trainMass + passengers.getTotalPassengerMass()));
    	else
    		speed = engine.pullEmergencyBrake(trainMass + passengers.getTotalPassengerMass());    	
    	
    	updateG();
    	
    	return speed;
    }
    
    public double pullBrake(double load)
    {    	
    	double speed = engine.pullBrake(load, (trainMass + passengers.getTotalPassengerMass()));
    	
    	updateG();
    	
    	return speed;
    }
    
    public double pullEmergencyBrake()
    {    	
    	double speed = engine.pullEmergencyBrake(trainMass + passengers.getTotalPassengerMass());
    	
    	updateG();
    	
    	return speed;
    }
    
    private void updateG()
    {
    	if (vc != null)
    		vc.updateGUI();
    	else
    		System.out.println("No view controller");
    }
    
    public String getSpeed()
	{
        return engine.getSpeed() + " m/s";
    }
    
    public String getTemp()
	{
        return temperature.getTrainTemperature() + " F";
    }
	
    public void setTemp(double t)
    {
    	if (t <= temperature.getTrainTemperature())
    		temperature.heatTrain();
    	else
    	{
    		temperature.coolTrain();
    	}
    }
    
    public String getFail()
	{
    	String fail = "";
    	
    	for (String s : failure.getFailures())
    		fail = fail + s + " ";
        return fail;
    }
	
    public void throwEngineFailure()
    {
        failure.throwEngineFailure();
        engine.setEngineFailure(true);
    }
    
    public void throwSignalFailure()
    {
        failure.throwSignalFailure();
    }
    
    public void throwBrakeFailure()
    {
        failure.throwBrakeFailure();
        engine.setBrakeFailure(true);
    }
    
    public void fixEngineFailure()
    {
        failure.fixEngineFailure();
        engine.setEngineFailure(false);
    }
    
    public void fixSignalFailure()
    {
        failure.fixSignalFailure();
    }
    
    public void fixBrakeFailure()
    {
        failure.fixBrakeFailure();
        engine.setBrakeFailure(false);
    }
    
    public void fixAllFailures()
    {
        failure.fixAllFailures();
        engine.setEngineFailure(false);
        engine.setBrakeFailure(false);
    }
    
    public boolean isBrakeBroken()
	{
		return failure.isBrakeBroken();
	}
	
	public boolean isSignalBroken()
	{
		return failure.isSignalBroken();
	}
	
	public boolean isEngineBroken()
	{
		return failure.isEngineBroken();
	}
    
    public int getPassengerNumber()
	{
        return passengers.getPassengerCount();
    }
	
    public void setPassengerNumber()
    {
        passengers.updatePassengers();
    }
    
    public String getLightValue()
	{
        return lights.getLights();
    }
	
    public void setLightValue(boolean val)
    {
        if (val)
        {
        	lights.turnLightsOn();
        }
        else
        {
        	lights.turnLightsOff();
        }
    }
    
    public String getDoorValue()
	{
        return doors.getDoors();
    }
	
    public void setDoorValue(boolean val)
    {
        if (val)
        {
        	doors.openDoors();
        }
        else
        {
        	doors.closeDoors();
        }
    }
    
    public void pullEBrake()
    {
    	eBrake = true;
    }
    
    public void releaseEBrake()
    {
    	eBrake = false;
    }
}