import java.util.ArrayList;


public class TrackController {

	public boolean trackState;
	public String ID;
	public Block blocks[];
	public ArrayList<Train> trainsInSection;
	public Block startBlock;
	public Block priorTerminalBlock;
	public Block terminalBlock;
	public boolean stalled = false;
	
	
	public TrackController(String s,Block b[],ArrayList<Train> trainsOnTrack)
	{
		ID = s;
		blocks = b;
		trainsInSection = trainsOnTrack;
	}
	
	public boolean getTrackState()
	{
		return true;
	}
	
	public boolean isTrainInSection()
	{
		for(int k=0; k < blocks.length; k++)
		{
			if(blocks[k].train != null)
				return true;
		}
		return false;
	}
	
	public boolean getBlockStatus()
	{
		return false;
	}
	
	public Block[] getBlocks()
	{
		return blocks;
	}
	
	public void calculateFixedBlockAuthority()
	{
		int authority = 0;
		boolean stop = false;
		int k = 0;
		/*
		for(int j = 1; j < blocks.length; j++)
		{
			if(blocks[j].train != null && blocks[j]!= terminalBlock)
			{
				
				//Check how far in advance the train may go
				k = j + 1;
				while(k < blocks.length && stop == false)
				{
				
					
					
				
					if(blocks[k].train != null)
					{
						stop = true;
						//Would typically Communicate this to train controller
						blocks[j].train.setAuthority(authority);
					}
					
					authority++;
					k++;
				}
				
				authority = 0;
				stop = false;
			}
		}
		*/
		for(Train t:trainsInSection)
		{
			Block currB = t.currentBlock;
			for(int j = 0; j < blocks.length; j++)
			{
				if(blocks[j] == currB && blocks[j]!= terminalBlock)
				{
					
					//Check how far in advance the train may go
					k = j + 1;
					while(k < blocks.length && stop == false)
					{
					
						
						
					
						if(blocks[k].train != null)
						{
							stop = true;
							//Would typically Communicate this to train controller
							blocks[j].train.setAuthority(authority);
						}
						
						authority++;
						k++;
					}
					
					if(stop == false)
						blocks[j].train.setAuthority(authority);
						
					authority = 0;
					stop = false;
				}
			}
		}
	}
	
	public void loadPLCProgram()
	{
		//Load a text file that tell the controller of the switches
	}
	
	public void runPLCProgram()
	{
		//Switch based on train destination and switches
		//The plc program will alter a switch when the train is present in block prior
		
	}
	
	public void setTerminalBlock(Block end)
	{
		terminalBlock = end;
	}
	
	public Block getTerminalBlock()
	{
		return terminalBlock;
	}
	
	public void setStartingBlock(Block Start)
	{
		startBlock = Start;
	}
	
	public Block getStartingBlock()
	{
		return startBlock;
	}
	
	public void checkSwitchStatus()
	{
		//Ensure that two trains are not attempting to change the same switch
		if(priorTerminalBlock.train != null)
		{
			boolean check = false;
			Switch s = terminalBlock.getSwitch();
			for(int i=0; i < priorTerminalBlock.train.route.length; i++)
			{
				if(s.direction == priorTerminalBlock.train.route[i])
				{
					check = true;
					break;
				}
			}
			
			if(!check)
			{	
				if(!s.hasRequester())
				{
					s.setRequester(this);
					s.toggleState(this);
					s.setRequester(null);
				}
				else
				{
					priorTerminalBlock.train.setAuthority(0);
					stalled = true;
				}
			}
			
		}
	}
	
	public void handoff()
	{
		if(terminalBlock.train != null)
		{
			//Remove train from our control since it's passed the switch and onto a new PLC
			boolean ret = trainsInSection.remove(terminalBlock.train);
			assert(ret);
		}
	}
	
	public void detectTrains()
	{
		if(startBlock.train != null)
		{
			//ensure that the train is headed this way
			//This route would be typically given by CTC
			/*
			Block [] b = startBlock.train.route;
			
			int checkCount = 0;
			for(int i=0; i < blocks.length; i++)
			{
				for(int k=0; k < b.length; k++)
				{
					if(blocks[i]==b[k])
						checkCount++;						
				}
				
			}
			
			if(checkCount >=5)
			{
				trainsInSection.add(startBlock.train);
				//calc fixed b auth
			}*/
			ArrayList<TrackController> plcs = startBlock.train.getControllers();
			if(plcs != null){
				for(TrackController t:plcs)
				{
					if(t.equals(this))
					{
						trainsInSection.add(startBlock.train);
					}
				}
			}
		}
		
	}
	/**
	 * Create message handler that will be notified when a train is now under the track controllers control.
	 * 
	 * 
	 */
}
