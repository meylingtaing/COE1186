/**
 * Placeholder Track object
 */
package trackModel;

import java.util.Hashtable;
import java.util.Iterator;

public class TrackObject implements Iterable<Block>
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

	@Override
	public Iterator<Block> iterator() 
	{
		return blockDB.values().iterator();
	}
}
