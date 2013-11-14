
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
	private double elevation;
	private double cumElevation;
	
	//Constructor
	public Block(String[] blockInfo)
	{
		
		section = blockInfo[1];
		blockId = Integer.parseInt(blockInfo[2]);
		length = Double.parseDouble(blockInfo[3]);
		grade = Double.parseDouble(blockInfo[4]);
		speedLimit = Integer.parseInt(blockInfo[5]);
		stationName = blockInfo[6];
		elevation = Double.parseDouble(blockInfo[8]);
		cumElevation = Double.parseDouble(blockInfo[9]);
		
		station = false;
		switchBoo = false;
		underground = false;
		crossing = false;
		switchToYard = false;
		switchFromYard = false;
		
		
		if(stationName.contains("STATION")){
			station=true;
		}
		if(stationName.contains("SWITCH")){
			switchBoo=true;
		}
		if(stationName.contains("RAILWAY CROSSING")){
			crossing = true;
		}
		if(stationName.contains("UNDERGROUND")){
			underground = true;
		}
		if(stationName.contains("SWITCH TO YARD")){
			switchToYard = true;
		}
		if(stationName.contains("SWITCH FROM YARD")){
			switchFromYard = true;
		}
		
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
	
	

}
