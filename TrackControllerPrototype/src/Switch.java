
public class Switch {

	Block direction;
	Block possibleD1, possibleD2;
	boolean state;
	TrackController requster;
	
	public Switch(Block A, Block B)
	{
		possibleD1 = A;
		possibleD2 = B;
		direction = A;
	}
	
	public void toggleState(TrackController sender)
	{
		if(requster == sender)
		{
	    if(direction == possibleD1)
	    {
	    	direction = possibleD2;
	    }
	    else
	    {
	    	direction = possibleD1;
	    }
		}
	    
	}
	
	public void setRequester(TrackController t)
	{
		requster = t;
	}
	
	public TrackController getRequester()
	{
		return requster;
	}
	
	public boolean hasRequester()
	{
		if(requster != null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Block getSwitchDirection()
	{
		return direction;
	}
	
	public String toString()
	{
		return "Current Switch Direction "+direction.id;
	}
}
