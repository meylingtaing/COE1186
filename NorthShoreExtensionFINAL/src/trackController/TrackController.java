package trackController;

import java.util.ArrayList;
import java.util.LinkedList;

import ctc.Route;

import nse.TrainPosition;
import nse.TransitSystem;

import TrainController.TrainController;
import trackModel.Block;
import trackModel.TrackObject;
import trainmodule.TrainModel;


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
	public TrainModel trainModule;
	public TrackObject trackModel;
	public TransitSystem transitSys;
	
	/*
	 * Between (inclusive) the start authority block and the terminal authority block we will pass authority to the
	 * trains that are in this section of track
	 * 
	 * The other trains that will still be within the PLC's scope will used for
	 * the authority calculation listed above
	 * 
	 * */
	
	public TrackController(String s,ArrayList<Block> b,ArrayList<Switch> switches, TransitSystem ts)
	{
		ID = s;
		blocks = b;
		transitSys = ts;
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
			if(blocks.get(k).isTrainDetected())
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
		//Compare the results of the two different algorithms 
		//Set the lowest authority to the trains
		ArrayList<TrainWithAuthority> t1List;
		t1List = calculateFixedBlockAuthority1();
		ArrayList<TrainWithAuthority> t2List;
		t2List = calculateFixedBlockAuthority2();
	}
	
	
	public ArrayList<TrainWithAuthority> calculateFixedBlockAuthority1()
	{			
		ArrayList<TrainWithAuthority> trainList = new ArrayList<TrainWithAuthority>();
		for(TrainController t:trainsUnderControl)
		{
			int id = t.getModel().getTrainID();
			Route r = transitSys.routeList.get(id);
			TrainPosition tp = transitSys.trainPositions.get(id);
			
			Block currB = tp.getCurrBlock();
			
			LinkedList<Block> blist = r.getBlockList();
			int currInd = blist.indexOf(currB);
			Block next = blist.get(currInd + 1);
		
			int start = currInd;
			int trainAuthority = 0;
			boolean quit = false;
			for(int j = start+1;quit == false ;j++)
			{
				if(next.isTrainDetected() == false && blocks.contains(next))
				{
					trainAuthority++;
					next = blist.get(j);
				}
				else
				{
					quit = true;
				}
			}
			
			TrainWithAuthority ta = new TrainWithAuthority(t,trainAuthority);
			trainList.add(ta);
		}
		return trainList;
	}
	
	
	/*
	 * Method 2 for calculating authority.
	 * 
	 * Look at all blocks in the Track Controller's scope. If a train is found anywhere in the
	 * Scope, continue forward to find the next train
	 * 
	 * Compare the next train's route against the first found train's route
	 * 
	 * If the next train's future blocks will collide with the first train's route;
	 * Choose the train with higher priority and set the other train's authority to zero
	 */
	public ArrayList<TrainWithAuthority> calculateFixedBlockAuthority2()
	{
		ArrayList<TrainWithAuthority> trains = new ArrayList<TrainWithAuthority>();
		TrainController train1 = null;
		TrainController train2 = null;
		int authority = 0;
		boolean start = true;
		
		for(Block b: blocks)
		{
			if(b.isTrainDetected() && train2 == null)
			{
				train1 = transitSys.getTrainInBlock(b);
				start = false;
			}
			if(b.isTrainDetected() && train2 == null && start==false)
			{
				train2 = transitSys.getTrainInBlock(b);
				TrainWithAuthority ta = new TrainWithAuthority(train1,authority);
				/*
				 * Calculate the priority between the trains based on their routes
				 * Set the authority appropriately
				 * */
				trains.add(ta);
				authority = 0;
				train1 = train2;
				train2 = null;
			}
			else
			{
				authority++;
			}
		}
		return trains;
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
			if(prev.isTrainDetected())
			{
				TrainController curr = transitSys.getTrainInBlock(prev);
				int currIndex = curr.getModel().getTrainID();
				Route r1 = transitSys.routeList.get(currIndex);
				
				int prevInd = r1.getBlockList().indexOf(curr);
				if(s.direction.equals(r1.getBlockList().get(prevInd+2)))
				{
					//Switch in correct position
				}
				else
				{
					s.toggleState();
					Block switchBlock = s.getContainingBlock();
					
					//Can I send the train model a block that will show the current direction of the switch?
					switchBlock.setSwitchBoo(true);
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
				if(b.isTrainDetected())
				{
					TrainController tc = transitSys.getTrainInBlock(b);
					
					if(!trainsUnderControl.contains(tc))
					{
						trainsUnderControl.add(tc);
					}
				}
			}
		
			for(Block b:endAuthorityBlocks)
			{
				if(b.isTrainDetected())
				{
					TrainController tc = transitSys.getTrainInBlock(b);
					if(trainsUnderControl.contains(tc))
					{
						trainsUnderControl.remove((tc));
					}
				}
			}
		}
		if(startAuthorityBlocks.size() == endAuthorityBlocks.size())
		{
			if(startAuthorityBlocks.get(0).equals(endAuthorityBlocks.get(0)))
			{
				Block b = startAuthorityBlocks.get(0);
				if(b.isTrainDetected())
				{
					TrainController tc = transitSys.getTrainInBlock(b);
					Route r = transitSys.routeList.get(tc.getModel().getTrainID());
					LinkedList<Block> blist = r.getBlockList();
					int curr = blist.indexOf(b);
					
					if(blist.get(curr+1).equals(b.getAuthorityExitBlock()))
					{
						trainsUnderControl.remove((tc));
					}
					else
					{
						trainsUnderControl.add((tc));
					}
				}
			}
		}
	}
	
	public void detectTrains()
	{
		//if(startBlock.train != null)
		//{
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
		
	//}
	}
	/**
	 * Create message handler that will be notified when a train is now under the track controllers control.
	 * 
	 * 
	 */
}
