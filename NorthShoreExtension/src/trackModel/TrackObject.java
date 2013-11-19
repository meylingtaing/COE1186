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
	public TrackObject(String name)
	{
		blockDB = new Hashtable<Integer, Block>();
		line = name;
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
