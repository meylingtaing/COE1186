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

import trackModel.TrackObject;
import trainModel.TrainModel;
import ctc.CTC;
import ctc.Route;

public class TransitSystem 
{
	// Instances of all of the modules
	public CTC ctc = new CTC(this);
	public Hashtable<String, TrackObject> trackArray = new Hashtable<String, TrackObject>();
	public Hashtable<String, TrainModel> trains = new Hashtable<String, TrainModel>();
	public Hashtable<String, Route> routeList = new Hashtable<String, Route>();
	
	
	
	public TransitSystem()
	{
		
	}
	
	public void startSimulation()
	{
		
	}
	
}
