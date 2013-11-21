package trackController;

import trackModel.Block;


public class Switch {

	Block direction;
	Block possibleD1, possibleD2;
	boolean state;
	Block containingBlock;
	
	public Switch(Block A, Block B,Block container)
	{
		possibleD1 = A;
		possibleD2 = B;
		direction = A;
		containingBlock = container;
	}
	
	public void toggleState()
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
	
	public Block getContainingBlock()
	{
		return containingBlock;
	}

	public Block getSwitchDirection()
	{
		return direction;
	}
	
	public String toString()
	{
		return "Current Switch Direction "+direction.getBlockId();
	}
}
