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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

import javafx.application.Platform;
import TrainController.TrainController;
import trackModel.Track;
import trackModel.Block;
import trackModel.TrackObject;
import trainmodule.TrainModel;
import ctc.AddTrackFormController;
import ctc.AddTrainFormController;
import ctc.CTC;
import ctc.Route;
import nse.MainController;

public class TransitSystem implements Runnable
{
	private String configFile = "config.txt";
	
	// Instances of all of the modules
	public CTC ctc = new CTC(this);
	public Hashtable<String, TrackObject> trackArray = new Hashtable<String, TrackObject>();
	public Hashtable<Integer, TrainController> trains = new Hashtable<Integer, TrainController>();
	public Hashtable<Integer, Route> routeList = new Hashtable<Integer, Route>();
	public Hashtable<Integer, TrainPosition> trainPositions = new Hashtable<Integer, TrainPosition>();
	private int tickRate;
	public boolean simulated = false;
	//public MainController mainController;
	
	public TransitSystem()
	{
		this(1);
		
		loadConfigFile(configFile);
	}
	
	/**
	 * Loads configuration file with track data at startup
	 * @param configFile
	 */
	private void loadConfigFile(String configFile)
	{
		// Check the configuration file
		try 
		{
			Scanner configScan = new Scanner(new File(configFile));
			while (configScan.hasNextLine())
			{
				String line = configScan.nextLine();
				
				// Skip comments
				if (line.charAt(0) == '#')
					continue;
				
				String[] fields = line.split(",");
				
				switch(fields[0])
				{
				case "TRACK":
					AddTrackFormController.addTrack(fields[1], fields[3], fields[2], ctc);
					break;
				case "TRAIN":
					AddTrainFormController.addTrain(fields[1], ctc);
				default:
					// Maybe something should go here?
				}
				
			}
			configScan.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Configuration file " + configFile + " was not found.");
			System.out.println("No default settings will be loaded.");
		}
	}
	
	public TransitSystem(int tickRate)
	{
		this.tickRate = tickRate;
	}
	
	public void setTickRate(int tickRate)
	{
		this.tickRate = tickRate;
	}
	
	public boolean isTrainDetected(String track, int blockId)
	{
		for (TrainController train : trains.values())
		{
			if (trainPositions.get(train.model.getTrainID()).getCurrBlock().getBlockID() == blockId && 
					trainPositions.get(train.model.getTrainID()).getCurrTrack().getClass().equals(track))
				return true;
		}
		return false;
	}

	public TrainController getTrainInBlock(Block b)
	{
		for(TrainController train: trains.values())
		{
			if(trainPositions.get(train.model.getTrainID()).getCurrBlock().getBlockID() == b.getBlockId())//When red line added make sure the track is the same 
			{
				return train;
			}
		}
		
		return null;
	}
	
	/**
	 * Creates a physical track object in the system
	 * CTC -> TrackController
	 * @param trackName
	 * @param track
	 */
	public void ctcAddTrack(String trackName, TrackObject track)
	{
		// TODO: TrackController: Change this so TrackController is created here
		trackArray.put(trackName, track);
	}
	
	/**
	 * Removes a physical track object in the system
	 * CTC -> TrackController
	 */
	public void ctcRemoveTrack(String trackName)
	{
		//TODO: talk to TrackController first?
		trackArray.remove(trackName);
	}
	
	/**
	 * Adds a train to the system
	 * CTC -> TrainController
	 * @param train
	 */
	public void ctcAddTrain(TrainController train)
	{
		// TODO: I don't know, but maybe TrainController needs to do something here
		trains.put(train.model.getTrainID(), train);
	}
	
	/**
	 * Gets a Block object in order to get information about it
	 * CTC -> TrackModel (TrackController?)
	 * @param trackName
	 * @param blockId
	 * @return
	 */
	public Block ctcGetBlock(String trackName, int blockId)
	{
		// TODO: Does ctc need to talk to track controller to access this correctly?
		return trackArray.get(trackName).getBlock(blockId);
	}
	
	/**
	 * Get information about track
	 * CTC -> TrackController
	 */
	public TrackObject ctcGetTrack(String trackName)
	{
		// TODO: talk to track controller?
		return trackArray.get(trackName);
	}
	
	/**
	 * Closes a block for maintenance
	 * CTC -> TrackController
	 * @param trackName
	 * @param blockId
	 */
	public void ctcCloseBlock(String trackName, int blockId)
	{
		// TODO: talk to track controller first?
		trackArray.get(trackName).getBlock(blockId).setClosed(true);
	}
	
	/**
	 * Opens a block out of maintenance
	 * CTC -> TrackController
	 * @param trackName
	 * @param blockId
	 */
	public void ctcOpenBlock(String trackName, int blockId)
	{
		//TODO: talk to track controller first?
		trackArray.get(trackName).getBlock(blockId).setClosed(false);
	}
	
	/**
	 * Removes a train from the system
	 * CTC -> TrainController (should track controller be notified as well??)
	 * @param trainId
	 */
	public void ctcRemoveTrain(int trainId)
	{
		// TODO: notify track controller?
		trains.remove(trainId);
	}
	
	/**
	 * Send route to track controller
	 * CTC -> TrackController
	 * @param route
	 */
	public void ctcSendRoute(int trainID, Route route)
	{
		// TODO: fill this in!
		routeList.put(trainID, route);
	}
	
	/**
	 * Send setpoint to track controller
	 * CTC -> TrackController
	 * @param setpoint
	 */
	public void ctcSuggestSetpoint(int trainId, double setpoint)
	{
		// TODO: fill this in!
	}
	
	/**
	 * Send authority to track controller
	 * CTC -> TrackController
	 * @param blocks
	 */
	public void ctcSuggestAuthority(int trainId, int blocks)
	{
		//TODO: fill this in!
	}
	
	/**
	 * Sets the initial train position
	 * CTC -> ?
	 * @param trainID
	 */
	public void ctcSetInitialPosition(int trainID, String trackName)
	{
		TrackObject track = trackArray.get(trackName);
		trainPositions.put(trainID, new TrainPosition(track));
	}
	
	@Override
	public void run() 
	{
		while (true)
		{
			
			try
			{
				//*
				synchronized(this) {
                    while (!simulated)
                        wait();
                }
				
				Thread.sleep(500);
				for (TrainController train : trains.values())
				{
					train.cruiseControl(.2);
					System.out.println("Train " + train.model.getTrainID() + " position: ");
					System.out.print("Block: " + trainPositions.get(train.model.getTrainID()).getCurrBlock().getBlockID() + " ");
					System.out.println("\tDistance traveled: " + trainPositions.get(train.model.getTrainID()).getDistanceTraveled());
					
					//simulated = false;
					//synchronized(CTC.ctcController) {
					//	CTC.ctcController.displayTrains();
					//}
					//simulated = true;
					
					Platform.runLater(new Runnable() {
					    public void run() {
					        //ctc.ctcController.displayTrains();
					    }
					});
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
