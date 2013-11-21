/**
 * Holds routing information for a train
 */
package ctc;

import java.util.LinkedList;
import java.util.Random;

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
	
	/**
	 * Present the route in readable string form
	 * 
	 */
	public String toString() 
	{
		StringBuffer routeString = new StringBuffer();
		int i = 0;
		for (Block block : blockList)
		{
			routeString.append(block.getBlockID() + " ");
			if (i == 20)
			{
				routeString.append("\n");
				i = 0;
			}
			
			i++;
		}
		
		return routeString.toString();
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
		while (!lastBlock.isStation() || !lastBlock.getStationName().equals(station))
		{
			//System.out.println("Block " + lastBlock.getBlockID());
			// Direction
			int[] possibleNextBlocks;
			double[] coordinates = lastBlock.getCoordinates();
			double[] endCoordinates = new double[2];
			
			if (lastBlockForward || !lastBlock.isBidirectional())
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
			
			//System.out.println("Possble next block: " + possibleNextBlocks[0]);
			int nextBlockId;
			
			// No switch, only one next block possible
			if (possibleNextBlocks[1] == -1)
			{
				nextBlockId = possibleNextBlocks[0];
			}
			
			// Two possible next blocks, randomly pick one
			// Really terrible logic and flow here...
			else
			{
				if (station.equals("YARD"))
				{
					if (track.getBlock(possibleNextBlocks[0]).isToYard())
					{
						nextBlockId = possibleNextBlocks[0];
					}
					else
						nextBlockId = possibleNextBlocks[1];
				}
				else
				{
					if (track.getBlock(possibleNextBlocks[0]).isToYard())
						nextBlockId = possibleNextBlocks[1];
					else if (track.getBlock(possibleNextBlocks[1]).isToYard())
						nextBlockId = possibleNextBlocks[0];
					else
					{
						// Randomly pick one
						Random rand = new Random();
						int randomIndex = rand.nextInt(2);
						nextBlockId = possibleNextBlocks[randomIndex];
					}
				}
			}
			
			Block nextBlock = track.getBlock(nextBlockId);
			//System.out.println("Next block: " + nextBlock);
			addBlock(nextBlock);
			
			// Figure out direction of next block
			coordinates = nextBlock.getCoordinates();
			if (coordinates[0] == endCoordinates[0] && coordinates[1] == endCoordinates[1])
				lastBlockForward = true;
			else
				lastBlockForward = false;
			
			lastBlock = blockList.getLast();
			//System.out.println("Last block " + lastBlock);
		}
		
		return blockList.getLast().getBlockID();
	}
	
	public TrainModel getTrain()
	{
		return train;
	}
	
	public TrackObject getTrack()
	{
		return track;
	}
}
