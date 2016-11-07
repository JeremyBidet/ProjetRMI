
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface User extends Remote{
	
	public String getName() throws RemoteException;
	
	public Role getRole() throws RemoteException;
}
