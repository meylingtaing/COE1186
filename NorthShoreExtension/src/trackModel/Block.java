/**
 * Placeholder for Block class
 * 
 * 
 */

package trackModel;

public class Block 
{
	private int blockId;
	private String stationName;
	//private boolean bidrectional;
	private int[] possibleNextBlocks;
	private int[] possiblePrevBlocks;
	private double[] coordinates;
	
	public Block(String[] blockInfo)
	{
		// Block ID
		blockId = Integer.parseInt(blockInfo[0]);
		
		// Coordinates
		coordinates = new double[4];
		for (int i = 1; i <= 4; i++)
			coordinates[i - 1] = Double.parseDouble(blockInfo[i]);
		
		// Station
		if (!blockInfo[5].equals("none"))
			stationName = blockInfo[5];
		else
			stationName = null;
		
		// Next block
		possibleNextBlocks = new int[] {Integer.parseInt(blockInfo[6]), 
										Integer.parseInt(blockInfo[7])};
		
		// Reversed direction
		possiblePrevBlocks = new int[] {Integer.parseInt(blockInfo[8]), 
										Integer.parseInt(blockInfo[9])};
	}
	
	public int getBlockID()
	{
		return blockId;
	}
	
	public double[] getCoordinates()
	{
		return coordinates;
	}
	
	public boolean isStation()
	{
		if (stationName != null)
			return true;
		
		return false;
	}
	
	public String getStationName()
	{
		return stationName;
	}
	
	public int[] getPossibleNextBlocks()
	{
		return possibleNextBlocks;
	}
	
	public int[] getPossiblePrevBlocks()
	{
		return possiblePrevBlocks;
	}
	
	public boolean isBidirectional()
	{
		if (possiblePrevBlocks[0] != -1)
			return true;
		
		return false;
	}
}