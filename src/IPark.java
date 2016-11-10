import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPark extends Remote {

	boolean addVehicle(String token, String matricul, int year, String model, double price) throws RemoteException;

	boolean removeVehicle(String token, String matricul) throws RemoteException;

	boolean returnVehicle(String token, String vehicle) throws RemoteException;

	boolean rentVehicle(String token, String vehicle) throws RemoteException;

	IVehicle[] searchByModel(String model) throws RemoteException;

	IVehicle[] searchByYear(int year) throws RemoteException;

	boolean buy(String token, String vehicle) throws RemoteException;
	
	IVehicle[] getVehicles(String token) throws RemoteException;
	
	Comment[] getComments(String token, String matricul) throws RemoteException;
	
	boolean addComment(String token, String matricul, String comment, int mark) throws RemoteException;

}
