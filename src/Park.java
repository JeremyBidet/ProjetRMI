import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Park extends Remote {

	boolean addVehicle(String matricul, int year, String model, double price) throws RemoteException;

	boolean removeVehicle(String matricul) throws RemoteException;

	boolean returnVehicle(String vehicle) throws RemoteException;

	boolean rentVehicle(String vehicle) throws RemoteException;

	Vehicle[] searchByModel(String model) throws RemoteException;

	Vehicle[] searchByYear(int year) throws RemoteException;

	boolean isBuyable(String vehicle) throws RemoteException;

	boolean buy(String vehicle) throws RemoteException;

	double getPrice(String vehicle) throws RemoteException;

	Vehicle[] getVehicles() throws RemoteException;

}
