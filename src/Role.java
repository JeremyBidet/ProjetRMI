
import java.rmi.Remote;

public interface Role extends Remote {
	
	String getRole();
	
	boolean canAddVehicle();
	
	boolean canRemoveVehicle();
	
	int getPriority();
}
