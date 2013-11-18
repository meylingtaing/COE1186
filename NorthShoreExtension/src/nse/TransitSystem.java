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

import java.util.ArrayList;

import trackModel.Track;
import trainModel.TrainModel;
import ctc.CTC;

public class TransitSystem 
{
	// Instances of all of the modules
	public CTC ctc = new CTC(this);
	public Track track = new Track();
	public ArrayList<TrainModel> trains = new ArrayList<TrainModel>();
	
	public TransitSystem()
	{
		
	}
	
}
