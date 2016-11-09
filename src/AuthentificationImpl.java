import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class AuthentificationImpl extends UnicastRemoteObject implements Authentification {

	private static final long serialVersionUID = 7031083944660693289L;

	private static final HashMap<String, User> loggedInUser = new HashMap<>();
	
	protected AuthentificationImpl() throws RemoteException {
		super();
	}

	@Override
	public String login(String login, String password) {
		// check file
		// add to park users
		// enable session
		// return token
		return "";
	}

	@Override
	public boolean register(String login, String password) {
		// write in file
		// add to park users
		return false;
	}

	@Override
	public void forgotPassword(String login) {
		// email
	}

	@Override
	public boolean logoff() {
		// close session
		// remove from park users
		return false;
	}	

}
