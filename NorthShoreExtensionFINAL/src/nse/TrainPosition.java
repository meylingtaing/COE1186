
/**
 * This holds the position for a train -- there is a lot of redundancy in this
 * @author meyling
 */

package nse;

import ctc.Route;
import trackModel.Block;
import trackModel.TrackObject;

public class TrainPosition {
	private TrackObject currTrack;
	private Route route;
	private Block currBlock;
	private boolean forward;
	private double distanceTraveled;
	
	public TrainPosition(TrackObject currTrack)
	{
		currBlock = currTrack.getBlock(0);
		this.currTrack = currTrack;
		forward = true;
		distanceTraveled = 0;
		currBlock.setTrainDetected(true);
	}
	
	public void setRoute(Route route)
	{
		this.route = route;
	}
	
	public void setPosition(Block block, boolean direction, double distance)
	{
		currBlock.setTrainDetected(false); //train leaves current block
		currBlock = block;
		currBlock.setTrainDetected(true); //train enters new block
		forward = direction;
		distanceTraveled = distance;
	}
	
	public void moveTrain(double distance)
	{
		System.out.println("Trying to move train a distance " + distance);
		//*
		while (distance > 0)
		{	
			// Move towards end of block
			if (distance >= (currBlock.getLength() - distanceTraveled))
			{
				//System.out.println("Need to move to next block");
				distance = distance - (currBlock.getLength() - distanceTraveled);
				
				// Remove block from route
				route.removeBlock();
				
				// Go to the next block
				// TODO: what if route has no more blocks?
				currBlock.setTrainDetected(false); //train leaves current block
				currBlock = route.getFirstBlock();
				currBlock.setTrainDetected(true); //train enters new block
				
				distanceTraveled = 0;
			}
			else
			{
				//System.out.println("Don't need to move to next block");
				distanceTraveled = distance + distanceTraveled;
				distance = 0;
			}
			
		}
		
		//*/
	}
	
	public TrackObject getCurrTrack()
	{
		return currTrack;
	}
	
	public Block getCurrBlock()
	{
		return currBlock;
	}
	
	public boolean isForward()
	{
		return forward;
	}
	
	public double getDistanceTraveled()
	{
		return distanceTraveled;
	}
}
