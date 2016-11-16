import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface IPark extends Remote {
	
	List<IVehicle> getVehicles(String token) throws AuthenticationException, RemoteException;
	
	List<IVehicle> getRentedVehicles(String token) throws AuthenticationException, RemoteException;

	boolean addVehicle(String token, String matricul, int year, String model, double price) throws RemoteException, AuthenticationException, ParkException;

	boolean removeVehicle(String token, String matricul) throws AuthenticationException, ParkException, RemoteException;

	boolean returnVehicle(String token, String matricul) throws AuthenticationException, ParkException, RemoteException;

	boolean rentVehicle(String token, String matricul) throws AuthenticationException, ParkException, RemoteException;

	int getPendingPosition(String token, String matricul) throws AuthenticationException, RemoteException;

	IUser getRental(String token, String matricul) throws AuthenticationException, RemoteException;
	
	List<IVehicle> searchVehiclesBy(String token, Map<String, Object> filters) throws AuthenticationException, RemoteException;
	
	List<IVehicle> searchUserRentedVehiclesBy(String token, Map<String, Object> filters) throws AuthenticationException, RemoteException;

	boolean buy(String token, String matricul) throws AuthenticationException, ParkException, RemoteException;
	
	List<IComment> getComments(String token, String matricul) throws AuthenticationException, ParkException, RemoteException;
	
	boolean addComment(String token, String matricul, String comment, int mark) throws AuthenticationException, RemoteException;
	
}
