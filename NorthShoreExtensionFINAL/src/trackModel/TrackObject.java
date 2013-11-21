package trackModel;
import java.util.Hashtable;
import java.util.Iterator;


public class TrackObject implements Iterable<Block> {
	
	private String line;
	private Hashtable<Integer, Block> blockDB;
	
	//constructor 
	public TrackObject(){
		blockDB= new Hashtable<Integer, Block>();
	}
	
	public void addBlock(Block b){
		int id = b.getBlockID();
		blockDB.put(id, b);
		
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
