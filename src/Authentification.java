import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Authentification extends Remote {

	String login(String login, String password) throws RemoteException;
	boolean register(String login, String password) throws RemoteException;
	void forgotPassword(String login) throws RemoteException;
	boolean logoff() throws RemoteException;
	
}
