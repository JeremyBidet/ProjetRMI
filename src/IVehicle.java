
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IVehicle extends Remote {

	String getMatricul();

	double getPrice() throws RemoteException;

	int getYear() throws RemoteException;

	String getModel() throws RemoteException;

	int getNbRented() throws RemoteException;

	void rent() throws RemoteException;

}
