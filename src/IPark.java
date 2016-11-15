import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IPark extends Remote {

	List<IVehicle> getVehicles(String token) throws AuthenticationException;
	
	List<IVehicle> getRentedVehicles(String token) throws AuthenticationException;

	boolean addVehicle(String token, String matricul, int year, String model, double price) throws RemoteException, AuthenticationException;

	boolean removeVehicle(String token, String matricul) throws RemoteException, AuthenticationException, ParkException;

	boolean returnVehicle(String token, String matricul) throws RemoteException, AuthenticationException, ParkException;

	boolean rentVehicle(String token, String matricul) throws RemoteException, AuthenticationException, ParkException;

	int getPendingPosition(String token, String matricul) throws RemoteException, AuthenticationException;

	IUser getRental(String token, String matricul) throws RemoteException, AuthenticationException;
	
	List<IVehicle> searchBy(String token, String list, Map<String, Object> filters) throws AuthenticationException;

	boolean buy(String token, String matricul) throws RemoteException, AuthenticationException, ParkException;
	
	List<IComment> getComments(String token, String matricul) throws RemoteException, AuthenticationException, ParkException;
	
	boolean addComment(String token, String matricul, String comment, int mark) throws RemoteException, AuthenticationException;
	
}
