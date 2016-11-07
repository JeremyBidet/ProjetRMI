import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Park extends Remote {

	boolean addVehicle(Vehicle vehicle, User user) throws RemoteException;

	boolean removeVehicle(Vehicle vehicle, User user) throws RemoteException;

	boolean isAvailable(Vehicle vehicle) throws RemoteException;

	boolean rentVehicle(Vehicle vehicle, User user) throws RemoteException;

	boolean returnVehicle(Vehicle vehicle, User user) throws RemoteException;

	boolean addToWishList(Vehicle book, User user) throws RemoteException;

	Vehicle[] searchByModel(String model) throws RemoteException;

	Vehicle[] searchByYear(int year) throws RemoteException;

	boolean isBuyable(Vehicle vehicle) throws RemoteException;

	boolean buy(Vehicle vehicle, User user) throws RemoteException;

	double getPrice(Vehicle vehicle) throws RemoteException;

	Vehicle[] getVehicles() throws RemoteException;

	User connect(String name) throws RemoteException;

	boolean IsValidUser(User user) throws RemoteException;
	
	void disconnect(User user) throws RemoteException;
	
}
