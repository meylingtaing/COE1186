package trackModel;
import java.util.Hashtable;


public class TrackObject {
	
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

}
