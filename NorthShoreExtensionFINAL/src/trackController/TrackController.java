package trackController;

import java.util.ArrayList;
import java.util.LinkedList;

import ctc.Route;

import nse.MainController;
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
	public Block [] bArray;
	public TrainModel trainModule;
	public TrackObject trackModel;
	public TransitSystem transitSys;
	public ArrayList<TrainWithSetpoint> trainsWithSetSpeed;
	public ArrayList<TrainWithAuthority> trainsWithAuthoritySuggestion;
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
		trainsWithSetSpeed = new ArrayList<TrainWithSetpoint>();
		trainsWithAuthoritySuggestion = new ArrayList<TrainWithAuthority>();
		trainsUnderControl = new ArrayList<TrainController>();
		switchesUnderControl = switches;
		trackModel = MainController.transitSystem.ctcGetTrack("greenline");
		//trainsInSection = trainsOnTrack;
		//trainModule = tm;
		//getSwitchesInSections();
		getCrossingsInSections();
		bArray = new Block[blocks.size()];
		for(int i = 0; i < blocks.size(); i++)
		{
			bArray[i] = blocks.get(i);
		}
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
	
	public void sendSetPoint()
	{
		//Send the set point information to the train controller
		for(TrainController tc:trainsUnderControl)
		{
			for(TrainWithSetpoint t: trainsWithSetSpeed)
			{
				if(t.train.equals(tc))
				{
					TrainPosition tp = transitSys.trainPositions.get(tc.getModel().getTrainID());
					Block curr = tp.getCurrBlock();
					if(t.suggestedSetPoint <= curr.getSpeedLimit())
					{
						tc.setSpeedSetpoint(t.suggestedSetPoint);
					}
					else
					{
						//The Blocks Speed limit has went down change setpoint speed
						t.suggestedSetPoint = curr.getSpeedLimit();
						tc.setSpeedSetpoint(t.suggestedSetPoint);
					}
				}
			}
		}
	}
	
	//Return false if the suggested speed is faster than the block speed limit
	public boolean suggestSetPoint(int trainID, double suggestion)
	{
		for(TrainWithSetpoint ts: trainsWithSetSpeed)
		{
			if(ts.train.getModel().getTrainID() == trainID)
			{
				//Make sure the suggestion is safe for the current block
				TrainPosition tp = transitSys.trainPositions.get(trainID);
				Block curr = tp.getCurrBlock();
				//The yard blocks dont have a speed limit
				if(curr.getSpeedLimit() >= suggestion || curr.getBlockId() == 0)
				{
					ts.suggestedSetPoint = suggestion;
					ts.train.setSpeedSetpoint(suggestion);
					return true;
				}
				else
				{
					//Not a safe suggestion
					return false;
				}
			}
		}
		//Train not found in this plc -- BAD
		return false;
	}
	
	public boolean suggestAuthority(int trainID, int blockNum)
	{
		for(TrainController tc: trainsUnderControl)
		{
			if(tc.getModel().getTrainID() == trainID)
			{
				trainsWithAuthoritySuggestion.add(new TrainWithAuthority(tc,blockNum));
				return true;
			}
		}
		return false;
	}
	
	public void calculateFixedBlockAuthority()
	{
		//Compare the results of the two different algorithms
		//Also take into account CTC suggestions
		//Set the lowest authority to the trains
		ArrayList<TrainWithAuthority> t1List;
		t1List = calculateFixedBlockAuthority1();
		ArrayList<TrainWithAuthority> t2List;
		t2List = calculateFixedBlockAuthority2();
	}
	
	public void calculateSignalStates()
	{
		//Traverse backwards through the trains blocks.
		//When a train is detected in the scope set the block it is in to be red
		//Set the blocks behind accordingly
		Block end = bArray[bArray.length - 1];
		int setNum = transitSys.getPossibleFutureSignals(transitSys,end);
		if(bArray != null)
		{
			boolean check = false;
			for(int i = bArray.length -1; i >= 0; i--)
			{
				check = false;
				if(bArray[i].isTrainDetected())
				{
					setNum = 4;
					bArray[i].setSignalState(setNum);
					check = true;
				}
				if(!bArray[i].isTrainDetected() && setNum == 4)
				{
					setNum--;
				}
				if(setNum == 3)
				{
					bArray[i].setSignalState(setNum);
					setNum--;
					check = true;
				}
				if(bArray[i].isCrossing() && setNum >= 2)
				{
					//Turn on the RR Signal in the block
					bArray[i].setCrossingSignalState(1);
				}
				if(bArray[i].isCrossing() && setNum < 2)
				{
					//Turn off crossing signals
					bArray[i].setCrossingSignalState(0);
				}
				if(setNum == 2 && !check)
				{
					bArray[i].setSignalState(setNum);
					setNum--;
					check = true;
				}
				if(setNum == 1 && !check)
				{
					bArray[i].setSignalState(setNum);
				}
			}
		}
	}
	
	public ArrayList<TrainWithAuthority> calculateFixedBlockAuthority1()
	{			
		//What if a block is under maintenece?
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
			int check = 0;
			
			for(int j = start+1;quit == false ;j++)
			{
				if(next.isTrainDetected() == false && blocks.contains(next) && !next.isClosed() && j < blist.size())
				{
					trainAuthority++;
					next = blist.get(j);
				}
				/*
				while(!blocks.contains(next) && check < 3 && j < blist.size())
					{
						next = blist.get(j);
						if(next.isTrainDetected() == false && !next.isClosed())
						{
							trainAuthority++;
							j++;
						}
						check++;
					}*/
				else
					//(check == 3)
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
			if(b.isTrainDetected() && train2 == null && start == true)
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
			if(start == false && !b.isClosed())
			{
				TrainWithAuthority ta = new TrainWithAuthority(train1,authority);
				authority = 0;
				start = true;
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
		//Allow user to modify state
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
					Block switchBlock = s.getContainingBlock();
					switchBlock.setCurrentlySwitchedTo(s.direction.getBlockId());
				}
				else
				{
					s.toggleState();
					Block switchBlock = s.getContainingBlock();
					
					//Can I send the train model a block that will show the current direction of the switch?
					switchBlock.setCurrentlySwitchedTo(s.direction.getBlockId());
					//Notify model that switch state has changed
				}
			}
		}
		
		int x = 0;
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
						TrainWithSetpoint t = new TrainWithSetpoint(tc, b.getSpeedLimit());
						TrainWithAuthority ta = new TrainWithAuthority(tc,tc.authoritySetpointFixed);
						trainsWithAuthoritySuggestion.add(ta);
						trainsWithSetSpeed.add(t);
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
						int indToRemove = -1;
						//Remove From set point array
						for(TrainWithSetpoint t : trainsWithSetSpeed)
						{
							if(t.train.equals(tc))
							{
								//Notify CTC that this PLC is loosing suggestion
								transitSys.setSegHandoffFlag(1);
								//trainsWithSetSpeed.remove(t);
								indToRemove = trainsWithSetSpeed.indexOf(t);
								break;
							}
						}
						
						if(indToRemove > -1)
						{
							trainsWithSetSpeed.remove(indToRemove);
						}
						
						//Remove From authority suggestion array
						indToRemove = -1;
						for(TrainWithAuthority t : trainsWithAuthoritySuggestion)
						{
							if(t.train.equals(tc))
							{
								//Notify CTC that this PLC is loosing suggestion
								transitSys.setSegHandoffFlag(1);
								indToRemove = trainsWithAuthoritySuggestion.indexOf(t);
								break;
							}
						}
						
						if(indToRemove > -1)
						{
							trainsWithAuthoritySuggestion.remove(indToRemove);
						}
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
				if(blist.size() > curr + 1)
				{
					if(blist.get(curr+1).equals(b.getAuthorityExitBlock()))
					{
						trainsUnderControl.remove((tc));
						
						int indToRemove = -1;
						for(TrainWithSetpoint t : trainsWithSetSpeed)
						{
							if(t.train.equals(tc))
							{
								//Notify CTC that this PLC is loosing suggestion
								transitSys.setSegHandoffFlag(1);
								//trainsWithSetSpeed.remove(t);
								indToRemove = trainsWithSetSpeed.indexOf(t);
								break;
							}
						}
						
						if(indToRemove > -1)
						{
							trainsWithSetSpeed.remove(indToRemove);
						}
						
						//Remove From authority suggestion array
						for(TrainWithAuthority t : trainsWithAuthoritySuggestion)
						{
							if(t.train.equals(tc))
							{
								//Notify CTC that this PLC is loosing suggestion
								transitSys.setSegHandoffFlag(1);
								//trainsWithAuthoritySuggestion.remove(t);
								indToRemove = trainsWithAuthoritySuggestion.indexOf(t);
								break;
							}
						}
						
						if(indToRemove > -1)
						{
							trainsWithAuthoritySuggestion.remove(indToRemove);
						}
					}
					else
					{
						trainsUnderControl.add((tc));
						TrainWithSetpoint t = new TrainWithSetpoint(tc, b.getSpeedLimit());;
						//PROBLEM
						TrainWithAuthority ta = new TrainWithAuthority(tc,tc.authoritySetpointFixed);
						trainsWithAuthoritySuggestion.add(ta);
						trainsWithSetSpeed.add(t);
					}
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
