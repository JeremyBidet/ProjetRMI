
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Vehicle extends Remote {

	String getMatricul();

	double getPrice() throws RemoteException;

	int getYear() throws RemoteException;

	String getModel() throws RemoteException;

	int getNbRented() throws RemoteException;

	void rent() throws RemoteException;

	@Override
	public String toString();

	@Override
	public boolean equals(Object o);

	@Override
	public int hashCode();

}
