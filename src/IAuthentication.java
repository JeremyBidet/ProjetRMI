import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IAuthentication extends Remote {

	String login(String login, String password) throws RemoteException, AuthenticationException;
	
	String register(String login, String firstname, String lastname, int role, String password) throws RemoteException, AuthenticationException;
	
	void forgotPassword(String login) throws RemoteException;
	
	boolean logoff(String token) throws RemoteException, AuthenticationException;
	
}
