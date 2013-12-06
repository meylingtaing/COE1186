/**
 * This is for the CTC to hold the layout of a track. This is redundant data,
 * because the track layout will also ne in the TrackModel, but this is just
 * for the CTC.
 * @author meyling
 */
package ctc;

import java.util.ArrayList;
import java.util.Iterator;

public class TrackLayout implements Iterable<Double[]>
{
	private String name;
	private ArrayList<Double[]> blockCoordinates;
	//private Hashtable<Integer, Double[]> blockCoordinates;
	private String color;
	
	/**
	 * Initializes track layout for CTC to hold
	 * @param name
	 * @param color
	 */
	public TrackLayout(String name, String color)
	{
		this.name = name;
		this.color = color;
		blockCoordinates = new ArrayList<Double[]>();
	}
	
	/**
	 * Adds a block to the track layout
	 * @param startX
	 * @param startY
	 * @param endX
	 * @param endY
	 * @return		true on successful block creation, otherwise false
	 */
	public boolean addBlock(double startX, double startY, double endX, double endY)
	{
		Double[] block = new Double[4];
		block[0] = startX;
		block[1] = startY;
		block[2] = endX;
		block[3] = endY;
		
		return addBlock(block);
	}
	
	/**
	 * Adds block to the track layout
	 * @param block is an array with startX, startY, endX, endY
	 * @return
	 */
	public boolean addBlock(Double[] block)
	{
		// Make sure the coordinates are legal
		if (block.length < 4)
			return false;
		if (block[0] < 0 || block[1] < 0 || block[2] < 0 || block[3] < 0)
			return false;
		
		blockCoordinates.add(block);
		return true;
	}
	
	/**
	 * Gets number of blocks in the track
	 * @return number of blocks
	 */
	public int getNumBlocks()
	{
		return blockCoordinates.size();
	}
	
	/**
	 * Gets the color of the track as seen on the CTC
	 * @return
	 */
	public String getColor()
	{
		return color;
	}

	/**
	 * Allows blocks of the tracks to be iterated
	 */
	@Override
	public Iterator<Double[]> iterator() 
	{
		return blockCoordinates.iterator();
	}
	
	/**
	 * Returns the name of the track
	 */
	public String toString()
	{
		return name;
	}
}
