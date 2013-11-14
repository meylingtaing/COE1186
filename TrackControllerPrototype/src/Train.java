import java.util.ArrayList;


public class Train {

	Block [] route;
	Block currentBlock;
	String id;
	int authority;
	ArrayList<TrackController> plcs;
	
	public Train(String name,Block [] path, Block currentB)
	{
		id = name;
		route = path;
		currentBlock = currentB;
	}
	
	public void goToNextBlock()
	{
		if(authority > 0){
		for(int k=0; k < route.length; k++)
		{
			if(route[k] == currentBlock)
			{
				if(k < route.length -1)
				{
					currentBlock.train = null;
					currentBlock = route[k+1];
					currentBlock.train = this;
					break;
				}
				else
				{
					currentBlock.train = null;
					currentBlock = route[0];
					currentBlock.train = this;
				}
			}
		}
		}
	}
	
	public Block getCurrentBlock()
	{
		return currentBlock;
	}
	
	public void setAuthority(int a)
	{
		authority = a;
	}
	
	public void setTrackControllers(ArrayList<TrackController> t)
	{
		plcs= t;
	}
	
	public int getAuthority()
	{
		return authority;
	}
	
	public ArrayList<TrackController> getControllers()
	{
		return plcs;
	}
	
	public String toString()
	{
		return "\nTRAIN ID: "+id+" in Block "+ currentBlock.id+" with authority: "+ authority;
	}
	
}
