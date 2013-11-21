/**
 * This is the main class for our train system simulation project.
 * It contains instances of all of our individual modules
 * 
 * Not exactly sure how we want to do this yet. I just have everything public
 * so modules can easily access other modules. There's probably a better
 * way though.
 * @author meyling
 */
package nse;

import java.util.Hashtable;

import TrainController.TrainController;
import trackModel.TrackObject;
import trainmodule.TrainModel;
import ctc.CTC;
import ctc.Route;

public class TransitSystem implements Runnable
{
	// Instances of all of the modules
	public CTC ctc = new CTC(this);
	public Hashtable<String, TrackObject> trackArray = new Hashtable<String, TrackObject>();
	public Hashtable<Integer, TrainController> trains = new Hashtable<Integer, TrainController>();
	public Hashtable<Integer, Route> routeList = new Hashtable<Integer, Route>();
	public Hashtable<Integer, TrainPosition> trainPositions = new Hashtable<Integer, TrainPosition>();
	private int tickRate;
	
	
	public TransitSystem()
	{
		this(1);
	}
	
	public TransitSystem(int tickRate)
	{
		this.tickRate = tickRate;
	}
	
	public void setTickRate(int tickRate)
	{
		this.tickRate = tickRate;
	}

	@Override
	public void run() 
	{
		
		while (true)
		{
			try
			{
				for (TrainController train : trains.values())
				{
					train.cruiseControl();
					System.out.println(train.model.getTrainID() + " ");
					System.out.println(trainPositions.get(train.model.getTrainID()));
				}
			}
			catch (Exception e)
			{
				
			}
		}
		// TODO Auto-generated method stub
		/*
		while (true)
		{
			try
			{
				Thread.sleep(1000/tickRate);
				
				for (TrainModel train : trains.values())
				{
					// Move train
					double distance = train.getVelocity() * 1;
					System.out.println(train.getName() + " " + distance);
					System.out.println(trainPositions.get(train.getName()));
					trainPositions.get(train.getName()).moveTrain(distance);
				}
				
				for (TrainPosition train : trainPositions.values())
				{
					System.out.println(train.getCurrBlock().getBlockID() + ": " + train.getDistanceTraveled());
				}
			}
			catch (Exception e)
			{
				System.err.println("Something screwy happened");
			}
		}
	//*/	
	}
	
	
}
