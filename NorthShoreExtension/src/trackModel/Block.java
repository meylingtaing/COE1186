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
	
	public Block(String[] blockInfo)
	{
		// Block ID
		blockId = Integer.parseInt(blockInfo[0]);
		
		// Station
		if (!blockInfo[5].equals("none"))
			stationName = blockInfo[5];
		else
			stationName = null;
		
		// Next block, switch, reversed direction
		possibleNextBlocks = new int[] {Integer.parseInt(blockInfo[6]), 
										Integer.parseInt(blockInfo[8]),
										Integer.parseInt(blockInfo[7])};
	}
	
	public int getBlockID()
	{
		return blockId;
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
}