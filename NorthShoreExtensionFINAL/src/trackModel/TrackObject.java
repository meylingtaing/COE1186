/**
 * On Track Trainwreck
 * TrackObject.java
 * Purpose: Holds block database for each track line as well as other setters and getters
 * 
 * @author Sarah Bunke
 * @version 2.0 12/12/13
 * 
 */
package trackModel;
import java.util.Hashtable;
import java.util.Iterator;


public class TrackObject implements Iterable<Block> {
	
	private String line;
	private Hashtable<Integer, Block> blockDB;
	private boolean circuitDown;
	private boolean powerDown;
	
	/**
	 * Constructor method for TrackObject.java
	 * creates block database 
	 */
	public TrackObject(){
		blockDB= new Hashtable<Integer, Block>();
		circuitDown = false;
		powerDown = false;
	}
	
	/**
	 * adds blocks of track to DB
	 * @param b ->block to add
	 */
	public void addBlock(Block b){
		int id = b.getBlockID();
		blockDB.put(id, b);
		
	}

	public boolean getPowerDown(){
		return powerDown;
	}
	
	public boolean getCircuitDown(){
		return circuitDown;
	}
	
	public void setPowerDown(boolean b)
	{
		powerDown=b;
	}
	
	public void setCircuitDown(boolean b)
	{
		circuitDown=b;
	}	
	
	public void setLine(String lineName){
		line = lineName;
	}
	
	public String getLine(){
		return line;
	}
	
	public Block getBlock(int id){
		return blockDB.get(id);
	}

	@Override
	public Iterator<Block> iterator() {
		return blockDB.values().iterator();
	}

}
