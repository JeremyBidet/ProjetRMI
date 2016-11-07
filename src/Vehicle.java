
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Vehicle extends Remote {

	double getPrice() throws RemoteException;
	
	int getYear() throws RemoteException;
	
	String getModel() throws RemoteException;
	
	int getNbRented() throws RemoteException;
	
	Commentary[] getComment() throws RemoteException;
	
	String toString();
	
}
