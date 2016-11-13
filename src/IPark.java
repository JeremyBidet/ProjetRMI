import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IPark extends Remote {

	boolean addVehicle(String token, String matricul, int year, String model, double price) throws RemoteException;

	boolean removeVehicle(String token, String matricul) throws RemoteException;

	boolean returnVehicle(String token, String vehicle) throws RemoteException;

	boolean rentVehicle(String token, String vehicle) throws RemoteException;

	List<IVehicle> searchByModel(String model) throws RemoteException;

	List<IVehicle> searchByYear(int year) throws RemoteException;

	boolean buy(String token, String matricul) throws RemoteException;
	
	List<IVehicle> getVehicles(String token) throws RemoteException;
	
	List<IComment> getComments(String token, String matricul) throws RemoteException;
	
	boolean addComment(String token, String matricul, String comment, int mark) throws RemoteException;
	
	ArrayList<PendingUser> pendingList(IVehicle v, String Token);
}
