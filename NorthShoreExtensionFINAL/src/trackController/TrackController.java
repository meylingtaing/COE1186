package trackController;

import java.util.ArrayList;

import trackModel.Block;
import trackModel.TrackObject;


public class TrackController {

	public boolean trackState;
	public String ID;
	public ArrayList<Block> startAuthorityBlocks;
	public ArrayList<Block> endAuthorityBlocks;
	public ArrayList<Block> blocks;
	public ArrayList<TrainController> trainsInSection;
	public ArrayList<TrainController> trainsUnderControl;
	public ArrayList<Switch> switchesUnderControl;
	public Block startBlock;
	public Block switchBlock1;
	public Block switchBlock2;
	public Block terminalBlock;
	public boolean stalled = false;
	public TrainModule trainModule;
	public TrackObject trackModel;
	
	
	/*
	 * Between (inclusive) the start authority block and the terminal authority block we will pass authority to the
	 * trains that are in this section of track
	 * 
	 * The other trains that will still be within the PLC's scope will used for
	 * the authority calculation listed above
	 * 
	 * */
	
	public TrackController(String s,ArrayList<Block> b,ArrayList<Switch> switches)
	{
		ID = s;
		blocks = b;
		//trainsInSection = trainsOnTrack;
		//trainModule = tm;
		//getSwitchesInSections();
		getCrossingsInSections();
		
	}
	
	private void getCrossingsInSections() {
		
		
	}

	public void getSwitchesInSections()
	{
		for(int k=0; k<blocks.size();k++)
		{
			
		}
	}
	
	public void setStartAuthorityBlocks(ArrayList<Block> b){
		startAuthorityBlocks = b;
	}
	
	public void setEndAuthorityBlocks(ArrayList<Block> b){
		endAuthorityBlocks = b;
	}
	
	public void setSwitchBlock1(Block b)
	{
		switchBlock1 = b;
	}
	
	public void setSwitchBlock2(Block b)
	{
		switchBlock2 = b;
	}
	
	public boolean getTrackState()
	{
		return true;
	}
	
	public boolean isTrainInScope()
	{
		for(int k=0; k < blocks.size(); k++)
		{
			if(blocks.get(k).train != null)
				return true;
		}
		return false;
	}
	
	public boolean getBlockStatus()
	{
		return false;
	}
	
	public ArrayList<Block> getBlocks()
	{
		return blocks;
	}
	
	public void calculateFixedBlockAuthority()
	{			
		for(TrainController t:trainsUnderControl)
		{
			Block currB = t.getCurrentBlock();
			Block next = t.getRoute().getNextBlock(currB);
			Route r = t.getRoute();
			int start = r.route.indexOf(currB);
			
			int trainAuthority = 0;
			boolean quit = false;
			for(int j = start+1;quit == false ;j++)
			{
				if(next.isTrainPresent() == false && blocks.contains(next))
				{
					trainAuthority++;
					next = r.route.get(j);
				}
				else
				{
					quit = true;
				}
			}
			
			t.setAuthorityFixed(trainAuthority);
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
		for(Switch s:switchesUnderControl)
		{
			int id = s.getContainingBlock().getBlockId();
			Block prev = trackModel.getBlock(id -1);
			if(prev.isTrainPresent())
			{
				TrainController curr = prev.train;
				Route r1 = curr.getRoute();
				int prevInd = r1.route.indexOf(curr);
				if(s.direction.equals(r1.route.get(prevInd+2)))
				{
					//Switch in correct position
				}
				else
				{
					s.toggleState();
					//Notify model that switch state has changed
				}
			}
		}
	}
	
	public void handoff()
	{
		if(startAuthorityBlocks.size()>1)
		{
			for(Block b:startAuthorityBlocks)
			{
				if(b.isTrainPresent())
				{
					if(!trainsUnderControl.contains(b.train))
					{
						trainsUnderControl.add(b.train);
					}
				}
			}
		
			for(Block b:endAuthorityBlocks)
			{
				if(b.isTrainPresent())
				{
					if(trainsUnderControl.contains(b.train))
					{
						trainsUnderControl.remove((b.train));
					}
				}
			}
		}
		if(startAuthorityBlocks.size() == endAuthorityBlocks.size())
		{
			if(startAuthorityBlocks.get(0).equals(endAuthorityBlocks.get(0)))
			{
				Block b = startAuthorityBlocks.get(0);
				if(b.isTrainPresent())
				{
					TrainController tc = b.train;
					if(tc.getNextBlock().equals(b.getAuthorityExitBlock()))
					{
						trainsUnderControl.remove((b.train));
					}
					else
					{
						trainsUnderControl.add((b.train));
					}
				}
			}
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
			
		
		//Must ask the track Module about the Yes/No of trains
		//If yes in either the terminal Block or the start block
		//The train must be looked up and then added to this track controllers scope
		
	}
	}
	/**
	 * Create message handler that will be notified when a train is now under the track controllers control.
	 * 
	 * 
	 */
}
