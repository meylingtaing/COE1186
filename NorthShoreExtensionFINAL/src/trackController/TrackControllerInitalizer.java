package trackController;

import java.util.ArrayList;

import nse.MainController;
import nse.TransitSystem;

import trackModel.Block;
import trackModel.TrackObject;


public class TrackControllerInitalizer {

	ArrayList<TrackController> plcs = new ArrayList<TrackController>();
	TrackObject model;
	TransitSystem trainsitSys;
	
	public TrackControllerInitalizer(TrackObject track, TransitSystem ts)
	{
		trainsitSys = ts;
		model = track;//MainController.transitSystem.ctcGetTrack("greenline");
	}
	
	public ArrayList<TrackController> initialize()
	{
		//Add the blocks in the order the trains will travel over them
		/*
		 * Create PLCS for green line 
		 * 
		 */
		//PLC 1 In control of Switch OQN
		//Needs to detect trains in sections M-N-O-P-Q
		ArrayList<Switch> switchesUnderPLC1Control = new ArrayList<Switch>(); 
		ArrayList<Block> plc1Blocks = new ArrayList<Block>();
		Block directionAPLC1 = null;
		Block directionBPLC1 = null;
		Block container = null;
		ArrayList<Block> startAuthoritys1 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys1 = new ArrayList<Block>();
		int blockAuthorityIndex=0;
		
		int index = 0;
		for(int k=74; k<=104;k++)
		{
			Block b = model.getBlock(k);
			plc1Blocks.add(b);
			if(k==77)
			{
				blockAuthorityIndex = index;
				startAuthoritys1.add(b);
				endAuthoritys1.add(b);
			}
			if(k==78)
			{
				plc1Blocks.get(blockAuthorityIndex).setAuthorityEnterBlock(b);
			}
			if(k==87)
			{
				directionAPLC1 = b;
			}
			if(k==100)
			{
				directionBPLC1 = b;
			}
			if(k==101)
			{
				plc1Blocks.get(blockAuthorityIndex).setAuthorityExitBlock(b);
			}
			index++;
		}
		index = 0;
		
		container = model.getBlock(86);
		Switch OQN = new Switch(directionAPLC1,directionBPLC1,container);
		container.setBlockSwitch(OQN);
		switchesUnderPLC1Control.add(OQN);
		TrackController plc1 = new TrackController("PLC 1",plc1Blocks,switchesUnderPLC1Control,trainsitSys,model);
		plc1.setEndAuthorityBlocks(endAuthoritys1);
		plc1.setStartAuthorityBlocks(startAuthoritys1);
		plc1.setStartingBlock(model.getBlock(74));
		plc1.setTerminalBlock(model.getBlock(104));
		plcs.add(plc1);
		
		//PLC 2 In control of Switch NRM
		//Needs to detect trains in sections Q-N-R-S-T-U-V-M-K
		ArrayList<Switch> switchesUnderPLC2Control = new ArrayList<Switch>(); 
		ArrayList<Block> plc2Blocks = new ArrayList<Block>();
		Block directionAPLC2 = null;
		Block directionBPLC2 = null;
		ArrayList<Block> startAuthoritys2 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys2 = new ArrayList<Block>();
		int authorityB1Index = 0;
		int authorityB2Index = 0;
		int authorityB3Index = 0;
		int authorityB4Index = 0;
		
		index = 0;
		for(int k=63; k<=121;k++)
		{
			if(k==86)
			{
				k=98;
			}
			
			Block b = model.getBlock(k);
			plc2Blocks.add(b);
			if(k==68)
			{
				b.setAuthorityEnterBlock(model.getBlock(67));
				authorityB1Index = index;
				startAuthoritys2.add(b);
			}
			if(k==70)
			{
				plc2Blocks.get(authorityB1Index).setAuthorityExitBlock(b);
			}
			if(k==76)
			{
				b.setAuthorityEnterBlock(plc2Blocks.get(index-1));
				authorityB2Index = index;
				endAuthoritys2.add(b);
			}
			if(k==77)
			{
				plc2Blocks.get(authorityB2Index).setAuthorityExitBlock(b);
			}
			if(k==101)
			{
				b.setAuthorityEnterBlock(plc2Blocks.get(index-1));
				authorityB3Index = index;
				startAuthoritys2.add(b);
			}
			if(k==102)
			{
				plc2Blocks.get(authorityB3Index).setAuthorityExitBlock(b);
			}
			if(k==116)
			{
				b.setAuthorityEnterBlock(plc2Blocks.get(index-1));
				authorityB4Index = index;
				endAuthoritys2.add(b);
			}
			if(k==117)
			{
				plc2Blocks.get(authorityB4Index).setAuthorityExitBlock(b);
			}
				
			if(k==77)
			{
				directionAPLC2 = b;
			}
			if(k==101)
			{
				directionBPLC2 = b;
			}
			index++;
			
		}
		index = 0;
		
		container = model.getBlock(76);
		Switch NRM = new Switch(directionAPLC2,directionBPLC2,container);
		container.setBlockSwitch(NRM);
		switchesUnderPLC2Control.add(NRM);
		TrackController plc2 = new TrackController("PLC 2",plc2Blocks,switchesUnderPLC2Control,trainsitSys,model);
		plc2.setEndAuthorityBlocks(endAuthoritys2);
		plc2.setStartAuthorityBlocks(startAuthoritys2);
		plc2.setStartingBlock(model.getBlock(63));
		plc2.setTerminalBlock(model.getBlock(121));
		plcs.add(plc2);
		
		//PLC 3 In control of Switches IJY1 JY2K
		//Needs to detect trains in sections I-J-K
		ArrayList<Switch> switchesUnderPLC3Control = new ArrayList<Switch>(); 
		ArrayList<Block> plc3Blocks = new ArrayList<Block>();
		Block directionAPLC3 = null;
		Block directionBPLC3 = null;
		Block direction2APLC3 = null;
		Block direction2BPLC3 = null;
		ArrayList<Block> startAuthoritys3 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys3 = new ArrayList<Block>();
		int authorityBIndex = 0;
		Block enter = null;
		Block exit = null;
		
		index = 0;
		for(int k=35; k<=69;k++)
		{
			
			Block b = model.getBlock(k);
			if(k!=35 && k!=69)
			{
				plc3Blocks.add(b);
			}
			if(k==35)
			{
				index--;
				enter = b;
			}
			if(k==69)
			{
				exit = b;
			}
			if(k==36)
			{
				b.setAuthorityEnterBlock(enter);
				authorityBIndex = index;
				startAuthoritys3.add(b);
			}
			if(k==37)
			{
				plc3Blocks.get(authorityBIndex).setAuthorityExitBlock(b);
			}
			if(k== 62)
			{
				b.setAuthorityEnterBlock(model.getBlock(0));
				b.setAuthorityExitBlock(model.getBlock(k +1));
				startAuthoritys3.add(b);
			}
			if(k==68)
			{
				b.setAuthorityEnterBlock(model.getBlock(k-1));
				authorityBIndex = index;
				endAuthoritys3.add(b);
			}
			if(k==69)
			{
				plc3Blocks.get(authorityBIndex).setAuthorityExitBlock(b);
			}
				
			if(k==59)
			{
				directionAPLC3 = b;
			}
			if(k==63)
			{
				direction2APLC3 = b;
			}
			if(k==68)
			{
				//Set Blocks To the yard
				directionBPLC3 = model.getBlock(0);
				direction2BPLC3 = model.getBlock(0);
				startAuthoritys3.add(model.getBlock(0));
			}
			index++;
		}
		index = 0;
		
		container = model.getBlock(58);
		Switch IJY = new Switch(directionAPLC3,directionBPLC3,container);
		container.setBlockSwitch(IJY);
		container = model.getBlock(62);
		Switch JYK = new Switch(direction2APLC3,direction2BPLC3,container);
		container.setBlockSwitch(JYK);
		switchesUnderPLC3Control.add(IJY);
		switchesUnderPLC3Control.add(JYK);
		TrackController plc3 = new TrackController("PLC 3",plc3Blocks,switchesUnderPLC3Control,trainsitSys,model);
		plc3.setEndAuthorityBlocks(endAuthoritys3);
		plc3.setStartAuthorityBlocks(startAuthoritys3);
		plc3.setStartingBlock(model.getBlock(36));
		plc3.setTerminalBlock(model.getBlock(68));
		plcs.add(plc3);
		
		//PLC 4 In control of Switch ZFG
		//Needs to detect trains in sections V-W-X-Y-Z-F-E-D-A-G-H-I	
		ArrayList<Switch> switchesUnderPLC4Control = new ArrayList<Switch>(); 
		ArrayList<Block> plc4Blocks = new ArrayList<Block>();
		Block directionAPLC4 = null;
		Block directionBPLC4 = null;
		ArrayList<Block> startAuthoritys4 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys4 = new ArrayList<Block>();
		authorityB1Index = 0;
		authorityB2Index = 0;
		Block enter2 = null;
		Block exit2 = null;
		
		
		for(int k=117; k<=150;k++)
		{
			
			
			Block b = model.getBlock(k);
			plc4Blocks.add(b);
			if(k==117)
			{
				enter = model.getBlock(k - 1);
				b.setAuthorityEnterBlock(enter);
				authorityB1Index = plc4Blocks.size();
				startAuthoritys4.add(b);
			}
			if(k==118)
			{
				plc4Blocks.get(authorityB1Index).setAuthorityExitBlock(b);
			}
			if(k==150)
			{
				enter2 = model.getBlock(k - 1);
				b.setAuthorityEnterBlock(enter2);
				authorityB1Index = plc4Blocks.size();
				exit2 = model.getBlock(28);
				b.setAuthorityExitBlock(exit2);
				endAuthoritys4.add(b);
				
			}
			
		
			
		}
		for(int k = 28; k > 0; k--)
		{
			if(k <= 3 || k >12)
			{
				Block b = model.getBlock(k);
				plc4Blocks.add(b);				
			}
	
		}
		for(int k = 29; k < 58; k++)
		{
			Block b = model.getBlock(k);
			plc4Blocks.add(b);
			if(k==29)
			{
				enter = model.getBlock(k - 1);
				b.setAuthorityEnterBlock(enter);
				authorityB1Index = plc4Blocks.size();
				startAuthoritys4.add(b);
			}
			if(k == 35)
			{
				plc4Blocks.get(authorityB1Index).setAuthorityExitBlock(b);
				endAuthoritys4.add(b);
			}
			if(k==28)
			{
				directionAPLC4 = b;
			}
			if(k==30)
			{
				directionBPLC4 = b;
			}
			
		}
		index = 0;
		
		container = model.getBlock(29);
		Switch ZFG = new Switch(directionAPLC4,directionBPLC4,container);
		container.setBlockSwitch(ZFG);
		switchesUnderPLC4Control.add(ZFG);
		TrackController plc4 = new TrackController("PLC 4",plc4Blocks,switchesUnderPLC4Control,trainsitSys, model);
		plc4.setEndAuthorityBlocks(endAuthoritys4);
		plc4.setStartAuthorityBlocks(startAuthoritys4);
		plc4.setStartingBlock(model.getBlock(117));
		plc4.setTerminalBlock(model.getBlock(35));
		plcs.add(plc4);
		
		//PLC 5 In control of Switch DCA
		//Needs to detect trains in sections Y-Z-F-E-D-A-B-C	
		ArrayList<Switch> switchesUnderPLC5Control = new ArrayList<Switch>(); 
		ArrayList<Block> plc5Blocks = new ArrayList<Block>();
		Block directionAPLC5 = null;
		Block directionBPLC5 = null;
		ArrayList<Block> startAuthoritys5 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys5 = new ArrayList<Block>();
		authorityBIndex = 0;
		
		index = 0;
		for(int k=147; k <= 150; k++)
		{
			Block b = model.getBlock(k);
			plc5Blocks.add(b);
		}
		for(int k = 28; k > 0; k--)
		{
			Block b = model.getBlock(k);
			plc5Blocks.add(b);
			if(k==28)
			{
				enter = model.getBlock(150); //Z
				b.setAuthorityEnterBlock(enter);
				authorityBIndex = plc5Blocks.size() - 1;
				startAuthoritys5.add(b);
				endAuthoritys5.add(b);
				exit = model.getBlock(k+1);
				plc5Blocks.get(authorityBIndex).setAuthorityExitBlock(exit);
				
			}
			if(k==11)
			{
				directionAPLC5 = b;
			}
			if(k==1)
			{
				directionBPLC5 = b;
			}
			
		}

		index = 0;
		
		container = model.getBlock(12);
		Switch DCA = new Switch(directionAPLC5,directionBPLC5,container);
		container.setBlockSwitch(DCA);
		switchesUnderPLC5Control.add(DCA);
		TrackController plc5 = new TrackController("PLC 5",plc5Blocks,switchesUnderPLC5Control,trainsitSys, model);
		plc5.setEndAuthorityBlocks(endAuthoritys5);
		plc5.setStartAuthorityBlocks(startAuthoritys5);
		plc5.setStartingBlock(model.getBlock(147));
		plc5.setTerminalBlock(model.getBlock(28));
		plcs.add(plc5);
		
		return plcs;
	}
	
}
