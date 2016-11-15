import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthentication extends Remote {

	String login(String login, String password) throws AuthenticationException, RemoteException;
	
	String register(String login, String firstname, String lastname, int role, String password) throws AuthenticationException, RemoteException;
	
	void forgotPassword(String login) throws RemoteException;
	
	boolean logoff(String token) throws AuthenticationException, RemoteException;
	
}
