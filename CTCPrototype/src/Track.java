/**
 * Placeholder class for Track to be used in CTC prototype
 * A track consists of blocks, each of which have an ID
 * @author meyling
 *
 */

import java.util.ArrayList;
import java.util.Iterator;

public class Track implements Iterable<Double[]> {
	private ArrayList<Double[]> blocks;
	private int numBlocks;
	
	public Track() {
		blocks = new ArrayList<Double[]>();
		numBlocks = 0;
	}
	
	public void addBlock(Double[] block) {
		blocks.add(block);
		numBlocks++;
	}
	
	public void addBlock(double startX, double startY, double endX, double endY) {
		Double[] block = new Double[4];
		block[0] = startX;
		block[1] = startY;
		block[2] = endX;
		block[3] = endY;
		
		this.addBlock(block);
	}

	public Iterator<Double[]> iterator() {
		return blocks.iterator();
	}
	
	public int getBlockCount() {
		return numBlocks;
	}
}
