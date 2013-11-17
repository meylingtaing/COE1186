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
	
	public Block(String[] blockInfo)
	{
		blockId = Integer.parseInt(blockInfo[0]);
		
		if (!blockInfo[5].equals("none"))
			stationName = blockInfo[5];
		else
			stationName = null;
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
}