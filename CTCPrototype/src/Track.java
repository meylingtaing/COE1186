/**
 * Placeholder class for Track to be used in CTC prototype
 * A track consists of blocks, each of which have an ID
 * @author meyling
 *
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Track implements Iterable<Block> {
	private ArrayList<Block> blocks;
	private int numBlocks;
	private String name;
	public String color;
	
	public Track(String name) {
		this.name = name;
		blocks = new ArrayList<Block>();
		numBlocks = 0;
		color = "#FFFFFF";
	}
	
	public Track() {
		Random rand = new Random();
		int trackNo = rand.nextInt(10000);
		name = ("Track " + trackNo);
		blocks = new ArrayList<Block>();
		numBlocks = 0;
	}
	
	public void addBlock(Double[] block, String stationName) {
		Block newBlock = new Block(numBlocks, block[0], block[1], block[2], block[3], stationName);
		blocks.add(newBlock);
		numBlocks++;
	}
	
	public void addBlock(double startX, double startY, double endX, double endY) {
		Double[] block = new Double[4];
		block[0] = startX;
		block[1] = startY;
		block[2] = endX;
		block[3] = endY;
		
		this.addBlock(block, null);
	}

	public Iterator<Block> iterator() {
		return blocks.iterator();
	}

	public int getBlockCount() {
		return numBlocks;
	}
	
	public String toString() {
		return name;
	}
}
