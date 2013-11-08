
public class User {
	private boolean admin;
	private boolean trackCreator;
	private boolean maintenance;
	private boolean dispatcher;
	
	public String name;
	
	public User(String name, boolean admin, boolean trackCreator, boolean maintenance, boolean dispatcher) {
		this.name = name;
		this.admin = admin;
		this.trackCreator = trackCreator;
		this.maintenance = maintenance;
		this.dispatcher = dispatcher;
	}
	
	public User(String name, String admin, String trackCreator, String maintenance, String dispatcher) {
		this.name = name;
		this.admin = admin.equals("1") ? true : false;
		this.trackCreator = trackCreator.equals("1") ? true : false;
		this.maintenance = maintenance.equals("1") ? true : false;
		this.dispatcher = dispatcher.equals("1") ? true : false;
			
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public boolean isTrackCreator() {
		return trackCreator;
	}
	
	public boolean isMaintenance() {
		return maintenance;
	}
	
	public boolean isDispatcher() {
		return dispatcher;
	}
	
}
