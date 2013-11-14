/**
 * Placeholder class for a block
 * @author meyling
 *
 */
public class Block {
	public Track track;
	public String stationName;
	public double startX, startY, endX, endY;
	public int blockId;
	
	public Block(int blockId, double startX, double startY, double endX, double endY, String stationName) {
		this.blockId = blockId;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.stationName = stationName;
	}
	
	public boolean hasStation() {
		if (stationName != null)
			return true;
		return false;
	}
}
