
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote {

	public String getLogin() throws RemoteException;

	public String getFirstname() throws RemoteException;

	public String getLastname() throws RemoteException;

	public Role getRole() throws RemoteException;

	@Override
	public String toString();

	@Override
	public boolean equals(Object o);

}
