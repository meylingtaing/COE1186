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
	private String switchBlock;
	private double length;
	
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
		
		// Switch
		switchBlock = blockInfo[10];
		
		// Length
		length = Double.parseDouble(blockInfo[11]);
	}
	
	public int getBlockID()
	{
		return blockId;
	}
	
	public double getLength()
	{
		return length;
	}
	
	/**
	 * Placeholder method...
	 * @return
	 */
	public int getNextBlockId()
	{
		return possibleNextBlocks[0];
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
	
	public boolean isToYard()
	{
		if (switchBlock.contains("YARD"))
		{
			return true;
		}
		return false;
	}
}