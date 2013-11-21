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
	private Route route;
	private TemperatureController temperature;
	private double trainMass = 37103.9;
	private double length = 32.2;
	private String conductor;
	//private static int clockSpeed;
	private TrainFailure failure;
	//public static ViewController view;
	private boolean eBrake = false;
	
	//DEBUG VARIABLES
	private static int trainIDGenerator = 2476;
	/*private static double powerGenerator = 100.0;
	private static double speedGenerator = 75;
	private static double accelerationGenerator = 23.21;
	private static int passengerGenerator = 34;
	private static double temperatureGenerator = 72.4;*/
	
	//TABLE COLUMN VARIABLES
	private SimpleIntegerProperty trainID;
	//private SimpleStringProperty setpoint;
	//private SimpleStringProperty speed;
	//private SimpleStringProperty acceleration;
	//private SimpleIntegerProperty passengerNumber;
	//private SimpleStringProperty temp;
	//private SimpleStringProperty fail;	
	//private SimpleStringProperty doorValue;
	//private SimpleStringProperty lightValue;
	
	public static void main(String args[])
	{
		//TrainView.createGUI();
		//TrainView tView = new TrainView();
		ViewController.data = FXCollections.observableArrayList();
		ViewController.data.add(new TrainModel(0.001, new Route(new Stack<String>()), 100, "SUPERUSER"));
		ViewController.data.add(new TrainModel(0.001, new Route(new Stack<String>()), 69.2, "ENGINEER"));
		ViewController.data.add(new TrainModel(0.001, new Route(new Stack<String>()), 67.4, "?????????"));
		//System.out.println("here!!");
		
		TrainView.createGUI();
		//ViewController.data.add(new TrainModel(new Route(new Stack<String>()), 100, "SUPERUSER"));
		//view.data.add(new TrainModel(new Route(new Stack<String>()), 70.2, "lala"));
		/*while (view == null)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}*/
		
		//System.out.println("here!!");
		
		//ViewController.data.add(new TrainModel(new Route(new Stack<String>()), 70.2, "SUPERUSER"));
		//view.data.add(new TrainModel(new Route(new Stack<String>()), 69.2, "ENGINEER"));
		//view.data.add(new TrainModel(new Route(new Stack<String>()), 67.4, "?????????"));
		
		//ViewController.updateGUI();
	}
	
	public TrainModel(double tick, Route trip, double t, String engineer)
	{
		doors = new DoorController();
		lights= new LightController();
		passengers = new PassengerManager();
		engine = new EngineModel(tick);
		temperature = new TemperatureController(t);
		failure = new TrainFailure();
		conductor = engineer;
		//clockSpeed = clock;
		route = trip;
		
		//INITIALIZE TABLE VARIABLES
		trainID = new SimpleIntegerProperty(trainIDGenerator);
		trainIDGenerator += 121;
		//setpoint = new SimpleStringProperty(powerGenerator + " KW");
		//powerGenerator -= 3.12;
		//speed = new SimpleStringProperty(speedGenerator + " MPH");
		//speedGenerator += 1.91;
		//acceleration = new SimpleStringProperty(accelerationGenerator + " m/s^2");
		//accelerationGenerator += 0.151;		
		//passengerNumber = new SimpleIntegerProperty(passengerGenerator);
		//passengerGenerator += 3;
		//temp = new SimpleStringProperty(temperatureGenerator + " F");
		//temperatureGenerator += 1.91;
		//fail = new SimpleStringProperty();
		//doorValue = new SimpleStringProperty(doors.getDoors() + "");
		//lightValue = new SimpleStringProperty(lights.getLights() + "");
		//ViewController.data.add(this);
	}
	
	/*public static void refreshGUI()
	{
		if (view != null)
			view.updateGUI();
		else
			System.out.println("No VC");
	}*/
	
	/*public static void setViewController(ViewController vc)
	{
		//System.out.println("hello");
		view = vc;
	}*/
	
	/*public static void addActiveTrains()
	{
		if (view != null)
			view.addTrain(new Route(new Stack<String>()), 70.2, "yolo");
		else
			System.out.println("view is uninitialized");
	}*/
	
	public int getTrainID()
	{
        return trainID.get();
    }
	
    /*public void setTrainID(int i)
    {
        trainID.set(i);
    }*/
    
    public String getSetpoint()
	{
        return engine.getSetpoint() + " watts";
    }
	
    public Double setSetpoint(double d)
    {
    	if (!eBrake)
    		return engine.calculateSetpoint(d, (trainMass + passengers.getTotalPassengerMass()));
    	else
    		return engine.pullEmergencyBrake(trainMass + passengers.getTotalPassengerMass());
    	
    	//return engine.getSetpoint();
    }
    
    public String getSpeed()
	{
        return engine.getSpeed() + " m/s";
    }
	
    /*public void setSpeed(String s)
    {
        speed.set(s);
    }
    
    public String getAcceleration()
	{
        return acceleration.get();
    }
	
    public void setAcceleration(String s)
    {
        acceleration.set(s);
    }*/
    
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
