/**
 * Placeholder Track object
 */
package trackModel;

import java.util.Hashtable;

public class TrackObject 
{
	private String line;
	private Hashtable<Integer, Block> blockDB;
	
	/**
	 * Constructor 
	 */
	public TrackObject()
	{
		blockDB = new Hashtable<Integer, Block>();
	}
	
	public String getLine()
	{
		return line;
	}
	
	public void addBlock(Block b)
	{
		int id = b.getBlockID();
		blockDB.put(id, b);
	}
	
	public Block getBlock(int id)
	{
		return blockDB.get(id);
	}
}
