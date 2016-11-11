import java.util.Arrays;

public enum Role {
	
	Student(2, true, true),
	Professor(1, true, true);
	
	public int priority;
	public boolean addVehicle;
	public boolean removeVehicle;
	
	private Role(int priority, boolean addVehicle, boolean removeVehicle) {
		this.priority = priority;
		this.addVehicle = addVehicle;
		this.removeVehicle = removeVehicle;
	}
	
	public static Role getById(int id) {
		return Arrays.asList(Role.values()).stream().filter(r -> r.ordinal() == id).findAny().get();
	}
	
}
