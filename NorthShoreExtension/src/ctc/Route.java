/**
 * Holds routing information for a train
 */
package ctc;

import java.util.LinkedList;

import trackModel.Block;
import trackModel.TrackObject;
import trainModel.TrainModel;

public class Route 
{
	private TrainModel train;
	private TrackObject track;
	private LinkedList<Block> blockList;
	private boolean lastBlockForward;
	
	public Route(TrainModel train, TrackObject track)
	{
		this.train = train;
		this.track = track;
		
		this.blockList = new LinkedList<Block>();
		
		// Add first block
		addBlock(track.getBlock(0));
		lastBlockForward = true;
	}
	
	private void addBlock(Block block)
	{
		blockList.add(block);
	}
	
	/**
	 * This method is kind of complicated for adding a single block to a route
	 * @param block
	 */
	public void addSingleBlock(Block block)
	{
		double[] coordinates = blockList.getLast().getCoordinates();
		double[] endCoordinates = new double[2];
		
		if (lastBlockForward)
		{
			endCoordinates[0] = coordinates[0];
			endCoordinates[1] = coordinates[1];
		}
		else
		{
			endCoordinates[0] = coordinates[2];
			endCoordinates[1] = coordinates[3];
		}
		
		// Figure out direction of next block
		coordinates = block.getCoordinates();
		if (coordinates[0] == endCoordinates[0] && coordinates[1] == endCoordinates[1])
			lastBlockForward = true;
		else
			lastBlockForward = false;
		
		addBlock(block);
	}
	
	/**
	 * Attempts to add blocks to route depending on station user picked
	 * 
	 * @param station
	 * @return 			blockID of last block added to route
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
		while (!lastBlock.getStationName().equals(station))
		{
			// Direction
			int[] possibleNextBlocks;
			double[] coordinates = lastBlock.getCoordinates();
			double[] endCoordinates = new double[2];
			
			if (lastBlockForward)
			{
				possibleNextBlocks = lastBlock.getPossibleNextBlocks();
				endCoordinates[0] = coordinates[0];
				endCoordinates[1] = coordinates[1];
				
			}
			else
			{
				possibleNextBlocks = lastBlock.getPossiblePrevBlocks();
				endCoordinates[0] = coordinates[2];
				endCoordinates[1] = coordinates[3];
			}
			
			// No switch, only one next block possible
			if (possibleNextBlocks[1] == -1)
			{
				Block nextBlock = track.getBlock(possibleNextBlocks[0]);
				addBlock(nextBlock);
				
				// Figure out direction of next block
				coordinates = nextBlock.getCoordinates();
				if (coordinates[0] == endCoordinates[0] && coordinates[1] == endCoordinates[1])
					lastBlockForward = true;
				else
					lastBlockForward = false;
			}
			
			// Two possible next blocks, let user decide
			else
			{
				return lastBlock.getBlockID();
			}
		}
		
		return blockList.getLast().getBlockID();
	}
	
	public TrainModel getTrain()
	{
		return train;
	}
}
