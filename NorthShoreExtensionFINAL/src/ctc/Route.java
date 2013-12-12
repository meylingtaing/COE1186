/**
 * Holds routing information for a train
 */
package ctc;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

import trackModel.Block;
import trackModel.TrackObject;

public class Route 
{
	private int trainId;
	private TrackObject track;
	private LinkedList<Block> blockList;
	private boolean lastBlockForward;		// Direction of last block
	private Hashtable<Integer, Double> dwellTimes; // Block ID, dwell in secs
	
	private final boolean DEBUG = false;
	private double defaultDwellTime = 1.0;
	
	/**
	 * Route is initialized with the train and the track
	 * @param train
	 * @param track
	 */
	public Route(int train, TrackObject track)
	{
		this.trainId = train;
		this.track = track;
		
		this.blockList = new LinkedList<Block>();
		this.dwellTimes = new Hashtable<Integer, Double>();
		
		// Add first block
		blockList.add(track.getBlock(0));
		lastBlockForward = true;
	}
	
	/**
	 * Displays in a readable format
	 */
	public String toString()
	{
		StringBuffer routeString = new StringBuffer();
		routeString.append("Route for Train " + trainId + "\n");
		for (Block block : blockList)
		{
			routeString.append(block.getBlockID() + " ");
			/*
			if (i == 20)
			{
				routeString.append("\n");
				i = 0;
			}
			//*/
		}
		
		return routeString.toString();
	}
	
	/**
	 * Continues the route from the last block to the station
	 * @param station
	 * @return
	 */
	public int addStation(String station)
	{
		if (blockList.isEmpty())
		{
			System.err.println("Route.addStation(): Empty route?!");
			return -1;
		}
		
		Block lastBlock = blockList.getLast();
		
		// Add blocks to the list until we hit the station
		while (!lastBlock.isStation() || !lastBlock.getStationName().equals(station))
		{
			if (DEBUG)
				System.out.println(lastBlock.getBlockID() + " " + lastBlockForward);
			
			if (lastBlock.isStation())
				dwellTimes.put(lastBlock.getBlockID(), defaultDwellTime);
			
			int[] possibleNextBlocks;
			Double[] coordinates = lastBlock.getCoordinates();
			Double[] endCoordinates = new Double[2];
			
			// Determine the possible next blocks by the direction 
			// of the last block
			if (!lastBlock.isBiDirectional() || lastBlockForward)
			{
				possibleNextBlocks = lastBlock.getPossibleNextBlocks();
				endCoordinates[0] = coordinates[2];
				endCoordinates[1] = coordinates[3];
			}
			else
			{
				possibleNextBlocks = lastBlock.getPossiblePrevBlocks();
				endCoordinates[0] = coordinates[0];
				endCoordinates[1] = coordinates[1];
			}
			
			int nextBlockId; // the next block the train must travel
			
			// No switch, only next block possible
			if (possibleNextBlocks[1] == -1 || !lastBlockForward)
				nextBlockId = possibleNextBlocks[0];
			
			// Two possible next blocks
			else
			{
				// If we are routing to the yard, take that switch
				if (station.equals("YARD"))
				{
					if (track.getBlock(possibleNextBlocks[0]).isYard())
						nextBlockId = possibleNextBlocks[0];
					else
						nextBlockId = possibleNextBlocks[1];
				}
				// Not routing to yard
				else
				{
					// If either switch is yard, take the other one
					if (track.getBlock(possibleNextBlocks[0]).isYard())
						nextBlockId = possibleNextBlocks[1];
					else if (track.getBlock(possibleNextBlocks[1]).isYard())
						nextBlockId = possibleNextBlocks[0];
					// Otherwise, randomly pick one
					else
					{
						Random rand = new Random();
						int randomIndex = rand.nextInt(2);
						nextBlockId = possibleNextBlocks[randomIndex];
					}
				}
			}
			
			// Get and add the next block
			Block nextBlock = track.getBlock(nextBlockId);
			blockList.add(nextBlock);
			
			// Figure out direction of next block
			coordinates = nextBlock.getCoordinates();
			if (DEBUG)
			{
				System.out.println("End coordinates: " + endCoordinates[0] + " " + endCoordinates[1]);
				System.out.println("Next start coordinates: " + coordinates[0] + " " + coordinates[1]);
			}
			if (coordinates[0].equals(endCoordinates[0]) && coordinates[1].equals(endCoordinates[1]))
				lastBlockForward = true;
			else
				lastBlockForward = false;
			
			// Change the last block
			lastBlock = nextBlock;
		}
		
		dwellTimes.put(lastBlock.getBlockID(), defaultDwellTime);
		
		return blockList.getLast().getBlockID();
	}
	
	/**
	 * Update the station dwell time
	 */
	/*
	public void setDwellTime(int blockId, double min)
	{
		setDwellTime(blockId, (int)min*60);
	}
	//*/
	
	/**
	 * Update the station dwell time in seconds
	 */
	public void setDwellTime(int blockId, double dwellTime)
	{
		dwellTimes.put(blockId, dwellTime);
	}
	
	/**
	 * Get the dwell times
	 */
	public Hashtable<Integer, Double> getSchedule()
	{
		return dwellTimes;
	}
	
	/**
	 * Removes the first block if there is more
	 */
	public void removeBlock()
	{
		// Not removing if only one block left...
		if (blockList.size() > 1)
			blockList.removeFirst();
	}
	
	/**
	 * Gets the first block of the route
	 */
	public Block getFirstBlock()
	{
		return blockList.getFirst();
	}
	
	/**
	 * Gets the block list
	 */
	public LinkedList<Block> getBlockList()
	{
		return blockList;
	}
	
	/**
	 * Get train
	 */
	public int getTrain()
	{
		return trainId;
	}
}
