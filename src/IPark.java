import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IPark extends Remote {

	boolean addVehicle(String token, String matricul, int year, String model, double price) throws RemoteException;

	boolean removeVehicle(String token, String matricul) throws RemoteException;

	boolean returnVehicle(String token, String matricul) throws RemoteException;

	boolean rentVehicle(String token, String matricul) throws RemoteException;

	List<IVehicle> searchBy(Map<String, Object> filters) throws RemoteException;

	boolean buy(String token, String matricul) throws RemoteException;
	
	List<IVehicle> getVehicles(String token) throws RemoteException;
	
	List<IComment> getComments(String token, String matricul) throws RemoteException;
	
	boolean addComment(String token, String matricul, String comment, int mark) throws RemoteException;
	
	int getPendingPosition(String token, String matricul) throws RemoteException;

	IUser getRental(String token, String matricul) throws RemoteException;
}
