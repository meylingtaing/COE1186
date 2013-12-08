package trackModel;

public class Block {
	
	private String section;
	private int blockId;
	private double length;
	private double grade;
	private int speedLimit;
	private boolean station;
	private boolean switchBoo;
	private boolean underground;
	private boolean crossing;
	private boolean switchToYard;
	private boolean switchFromYard;
	private String stationName;
	private String infrastructure;
	private double elevation;
	private double cumElevation;
	private boolean BiDirectional;
	private boolean canGoToTwoPlaces;
	private int NextBlockId;
	private int SecondNextBlockId;
	private int thirdNextBlockId;
	private boolean isNextBlockSwitch;
	private int nextBlockSwitchId;
	private int currentlySwitchedTo;
	private double startX;
	private double startY;
	private double endX;
	private double endY;
	private boolean yard;
	private String line;
	private boolean closed;
	private int signalState;
	private int crossingSigState;
	
	private Block authorityEnterBlock;
	private Block authorityExitBlock;
	
	private boolean trainDetected;
	
	
	
	
	//Constructor
	public Block(String[] blockInfo)
	{
		
		line = blockInfo[0];
		section = blockInfo[1];
		blockId = Integer.parseInt(blockInfo[2]);
		length = Double.parseDouble(blockInfo[3]);
		grade = Double.parseDouble(blockInfo[4]);
		speedLimit = Integer.parseInt(blockInfo[5]);
		infrastructure = blockInfo[6];
		stationName = blockInfo[7];
		elevation = Double.parseDouble(blockInfo[8]);
		cumElevation = Double.parseDouble(blockInfo[9]);
		BiDirectional = Boolean.parseBoolean(blockInfo[10]);
		canGoToTwoPlaces = Boolean.parseBoolean(blockInfo[11]);
		NextBlockId = Integer.parseInt(blockInfo[12]);
		SecondNextBlockId = Integer.parseInt(blockInfo[13]);
		thirdNextBlockId = Integer.parseInt(blockInfo[14]);
		isNextBlockSwitch = Boolean.parseBoolean(blockInfo[15]);
		nextBlockSwitchId = Integer.parseInt(blockInfo[16]);
		currentlySwitchedTo = Integer.parseInt(blockInfo[17]);
		startX = Double.parseDouble(blockInfo[18]);
		startY = Double.parseDouble(blockInfo[19]);;
		endX = Double.parseDouble(blockInfo[20]);;
		endY = Double.parseDouble(blockInfo[21]);;
		
		station = false;
		switchBoo = false;
		underground = false;
		crossing = false;
		switchToYard = false;
		switchFromYard = false;
		signalState = 1;
		crossingSigState = 0;
		
		trainDetected=false;
		
		closed = false;
		
		
		if(infrastructure.contains("STATION")){
			station=true;
		}
		if(infrastructure.contains("SWITCH")){
			switchBoo=true;
		}
		if(infrastructure.contains("RAILWAY CROSSING")){
			crossing = true;
		}
		if(infrastructure.contains("UNDERGROUND")){
			underground = true;
		}
		if(infrastructure.contains("SWITCH TO YARD")){
			switchToYard = true;
		}
		if(infrastructure.contains("SWITCH FROM YARD")){
			switchFromYard = true;
		}
		if(infrastructure.contains("YARD")){
			yard = true;
		}
		
	}
	
	public String getTrackLine(){
		return line;
	}
	
	public boolean isTrainDetected()
	{
		return trainDetected;
	}
	
	public void setTrainDetected(boolean cond)
	{
		trainDetected=cond;
	}
	
	public void setAuthorityEnterBlock(Block b)
	{
		authorityEnterBlock=b;
	}
	
	public void setAuthorityExitBlock(Block b)
	{
		authorityExitBlock=b;
	}
	
	public Block getAuthorityEnterBlock()
	{
		return authorityEnterBlock;
	}
	
	public Block getAuthorityExitBlock()
	{
		return authorityExitBlock;
	}
	
	
	//the following methods are getters and setters for the block attributes
	
	public String getInfrastructure() {
		return infrastructure;
	}

	public void setInfrastructure(String infrastructure) {
		this.infrastructure = infrastructure;
	}

	public boolean isBiDirectional() {
		return BiDirectional;
	}

	public void setBiDirectional(boolean biDirectional) {
		BiDirectional = biDirectional;
	}

	public boolean isCanGoToTwoPlaces() {
		return canGoToTwoPlaces;
	}

	public void setCanGoToTwoPlaces(boolean canGoToTwoPlaces) {
		this.canGoToTwoPlaces = canGoToTwoPlaces;
	}

	public int getNextBlockId() {
		return NextBlockId;
	}

	public void setNextBlockId(int nextBlockId) {
		NextBlockId = nextBlockId;
	}

	public int getSecondNextBlockId() {
		return SecondNextBlockId;
	}

	public void setSecondNextBlockId(int secondNextBlockId) {
		SecondNextBlockId = secondNextBlockId;
	}

	public int getThirdNextBlockId() {
		return thirdNextBlockId;
	}

	public void setThirdNextBlockId(int thirdNextBlockId) {
		this.thirdNextBlockId = thirdNextBlockId;
	}

	public boolean isNextBlockSwitch() {
		return isNextBlockSwitch;
	}

	public void setNextBlockSwitch(boolean isNextBlockSwitch) {
		this.isNextBlockSwitch = isNextBlockSwitch;
	}

	public int getNextBlockSwitchId() {
		return nextBlockSwitchId;
	}

	public void setNextBlockSwitchId(int nextBlockSwitchId) {
		this.nextBlockSwitchId = nextBlockSwitchId;
	}

	public int getCurrentlySwitchedTo() {
		return currentlySwitchedTo;
	}

	public void setCurrentlySwitchedTo(int currentlySwitchedTo) {
		this.currentlySwitchedTo = currentlySwitchedTo;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

	public double getEndX() {
		return endX;
	}

	public void setEndX(double endX) {
		this.endX = endX;
	}

	public double getEndY() {
		return endY;
	}

	public void setEndY(double endY) {
		this.endY = endY;
	}

	public boolean isYard() {
		return yard;
	}

	public void setYard(boolean yard) {
		this.yard = yard;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public int getBlockId() {
		return blockId;
	}

	public void setBlockId(int blockId) {
		this.blockId = blockId;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getGrade() {
		return grade;
	}

	public void setGrade(double grade) {
		this.grade = grade;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
	}

	public boolean isStation() {
		return station;
	}

	public void setStation(boolean station) {
		this.station = station;
	}

	public boolean isSwitchBoo() {
		return switchBoo;
	}

	public void setSwitchBoo(boolean switchBoo) {
		this.switchBoo = switchBoo;
	}

	public boolean isUnderground() {
		return underground;
	}

	public void setUnderground(boolean underground) {
		this.underground = underground;
	}

	public boolean isCrossing() {
		return crossing;
	}

	public void setCrossing(boolean crossing) {
		this.crossing = crossing;
	}

	public boolean isSwitchToYard() {
		return switchToYard;
	}

	public void setSwitchToYard(boolean switchToYard) {
		this.switchToYard = switchToYard;
	}

	public boolean isSwitchFromYard() {
		return switchFromYard;
	}

	public void setSwitchFromYard(boolean switchFromYard) {
		this.switchFromYard = switchFromYard;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public double getCumElevation() {
		return cumElevation;
	}

	public void setCumElevation(double cumElevation) {
		this.cumElevation = cumElevation;
	}

	public int getBlockID(){
		return this.blockId;
	}
	
	public Double[] getCoordinates()
	{
		Double[] coordinates = {startX, startY, endX, endY};
		return coordinates;
	}
	
	public int[] getPossibleNextBlocks()
	{
		int[] next = {NextBlockId, thirdNextBlockId};
		return next;
	}
	
	public int[] getPossiblePrevBlocks()
	{
		// change this for redline
		int[] prev = {SecondNextBlockId, -1};
		return prev;
	}
	
	public boolean isClosed()
	{
		return closed;
	}
	
	public void setClosed(boolean closed)
	{
		this.closed = closed;
	}
	
	/*
	 * Signal States
	 * 1 - Super Green
	 * 2 - Green
	 * 3 - Yellow
	 * 4 - Red
	 * */
	public void setSignalState(int state)
	{
		signalState = state;
	}
	
	public int getSignalState()
	{
		return signalState;
	}
	
	// 0 - Crossing sig off
	// 1 - Crossing sig on
	public void setCrossingSignalState(int state)
	{
		crossingSigState = state;
	}
	
	public int getCrossingSignalState()
	{
		return crossingSigState;
	}

}
