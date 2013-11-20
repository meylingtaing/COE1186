import java.util.ArrayList;


public class TrackControllerInitalizer {

	ArrayList<TrackController> plcs = new ArrayList<TrackController>();
	TrackModel model;
	
	public TrackControllerInitalizer(TrackModel track)
	{
		model = track;
	}
	
	public ArrayList<TrackController> initialize()
	{
		/*
		 * Create PLCS for green line 
		 * 
		 */
		//PLC 1 In control of Switch OQN
		//Needs to detect trains in sections M-N-O-P-Q
		ArrayList<Switch> switchesUnderPLC1Control = new ArrayList<Switch>(); 
		Block [] plc1Blocks = new Block[30];
		Block directionAPLC1 = null;
		Block directionBPLC1 = null;
		ArrayList<Block> startAuthoritys1 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys1 = new ArrayList<Block>();
		
		int index = 0;
		for(int k=74; k<=104;k++)
		{
			Block b = model.getBlock(k);
			plc1Blocks[index] = b;
			if(k==77)
			{
				startAuthoritys1.add(b);
				endAuthoritys1.add(b);
			}
			if(k==87)
			{
				directionAPLC1 = b;
			}
			if(k==100)
			{
				directionBPLC1 = b;
			}
			index++;
		}
		index = 0;
		
		Switch OQN = new Switch(directionAPLC1,directionBPLC1);
		switchesUnderPLC1Control.add(OQN);
		TrackController plc1 = new TrackController("PLC 1",plc1Blocks,switchesUnderPLC1Control);
		plc1.setEndAuthorityBlocks(endAuthoritys1);
		plc1.setStartAuthorityBlocks(startAuthoritys1);
		plc1.setStartingBlock(plc1Blocks[0]);
		plc1.setTerminalBlock(plc1Blocks[29]);
		plcs.add(plc1);
		
		//PLC 2 In control of Switch NRM
		//Needs to detect trains in sections Q-N-R-S-T-U-V-M-K
		ArrayList<Switch> switchesUnderPLC2Control = new ArrayList<Switch>(); 
		Block [] plc2Blocks = new Block[47];
		Block directionAPLC2 = null;
		Block directionBPLC2 = null;
		ArrayList<Block> startAuthoritys2 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys2 = new ArrayList<Block>();
		
		index = 0;
		for(int k=63; k<=121;k++)
		{
			if(k==86)
			{
				k=98;
			}
			Block b = model.getBlock(k);
			plc2Blocks[index] = b;
			if(k==69)
			{
				startAuthoritys2.add(b);
			}
			if(k==76)
			{
				endAuthoritys2.add(b);
			}
			if(k==101)
			{
				startAuthoritys2.add(b);
			}
			if(k==116)
			{
				endAuthoritys2.add(b);
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
		
		Switch NRM = new Switch(directionAPLC2,directionBPLC2);
		switchesUnderPLC2Control.add(NRM);
		TrackController plc2 = new TrackController("PLC 2",plc2Blocks,switchesUnderPLC2Control);
		plc2.setEndAuthorityBlocks(endAuthoritys2);
		plc2.setStartAuthorityBlocks(startAuthoritys2);
		plc2.setStartingBlock(plc2Blocks[0]);
		plc2.setTerminalBlock(plc2Blocks[46]);
		plcs.add(plc2);
		
		//PLC 3 In control of Switches IJY1 JY2K
		//Needs to detect trains in sections I-J-K
		ArrayList<Switch> switchesUnderPLC3Control = new ArrayList<Switch>(); 
		Block [] plc3Blocks = new Block[32];
		Block directionAPLC3 = null;
		Block directionBPLC3 = null;
		Block direction2APLC3 = null;
		Block direction2BPLC3 = null;
		ArrayList<Block> startAuthoritys3 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys3 = new ArrayList<Block>();
		
		index = 0;
		for(int k=36; k<=68;k++)
		{
			
			Block b = model.getBlock(k);
			plc3Blocks[index] = b;
			if(k==36)
			{
				startAuthoritys3.add(b);
			}
			if(k==68)
			{
				endAuthoritys3.add(b);
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
				directionBPLC3 = null;
				direction2BPLC3 = null;
			}
			index++;
		}
		index = 0;
		
		Switch IJY = new Switch(directionAPLC3,directionBPLC3);
		Switch JYK = new Switch(direction2APLC3,direction2BPLC3);
		switchesUnderPLC3Control.add(IJY);
		switchesUnderPLC3Control.add(JYK);
		TrackController plc3 = new TrackController("PLC 3",plc3Blocks,switchesUnderPLC3Control);
		plc3.setEndAuthorityBlocks(endAuthoritys3);
		plc3.setStartAuthorityBlocks(startAuthoritys3);
		plc3.setStartingBlock(plc3Blocks[0]);
		plc3.setTerminalBlock(plc3Blocks[32]);
		plcs.add(plc3);
		
		//PLC 4 In control of Switch ZFG
		//Needs to detect trains in sections V-W-X-Y-Z-F-E-D-A-G-H-I	
		ArrayList<Switch> switchesUnderPLC4Control = new ArrayList<Switch>(); 
		Block [] plc4Blocks = new Block[82];
		Block directionAPLC4 = null;
		Block directionBPLC4 = null;
		ArrayList<Block> startAuthoritys4 = new ArrayList<Block>();
		ArrayList<Block> endAuthoritys4 = new ArrayList<Block>();
		
		index = 0;
		for(int k=0; k<=150;k++)
		{
			if(k==4)
			{
				k=13;
			}
			if(k==57)
			{
				k=118;
			}
				
			Block b = model.getBlock(k);
			plc4Blocks[index] = b;
			if(k==117)
			{
				startAuthoritys4.add(b);
			}
			if(k==150)
			{
				endAuthoritys4.add(b);
			}
			if(k==29)
			{
				startAuthoritys4.add(b);
			}
			if(k == 35)
			{
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
		
			index++;
		}
		index = 0;
		
		Switch ZFG = new Switch(directionAPLC4,directionBPLC4);
		switchesUnderPLC4Control.add(ZFG);
		TrackController plc4 = new TrackController("PLC 4",plc4Blocks,switchesUnderPLC4Control);
		plc4.setEndAuthorityBlocks(endAuthoritys4);
		plc4.setStartAuthorityBlocks(startAuthoritys4);
		plc4.setStartingBlock(plc4Blocks[0]);
		plc4.setTerminalBlock(plc4Blocks[81]);
		plcs.add(plc4);
		
		return plcs;
	}
	
}
