/**
 * Contains the coordinates of a track
 */
package ctc;

import java.util.ArrayList;
import java.util.Iterator;

public class TrackLayout implements Iterable<Double[]>
{
	public String name;
	private ArrayList<Double[]> blockCoordinates;
	private String color;
	
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
	
	public int getNumBlocks()
	{
		return blockCoordinates.size();
	}
	
	public String getColor()
	{
		return color;
	}

	@Override
	public Iterator<Double[]> iterator() {
		return blockCoordinates.iterator();
	}
	
	public String toString()
	{
		return name;
	}
}
