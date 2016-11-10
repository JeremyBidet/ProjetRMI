import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class AuthentificationImpl extends UnicastRemoteObject implements Authentification {

	private static final long serialVersionUID = 7031083944660693289L;

	private static final HashMap<String, User> loggedInUser = new HashMap<String, User>();
	
	public static User getUser(String token) {
		return loggedInUser.get(token);
	}
	
	protected AuthentificationImpl() throws RemoteException {
		super();
	}

	@Override
	public String login(String login, String password) throws RemoteException {
		//TODO:
		Utils.DB db = Utils.parseDB("resources/users");
		if(db.findSiblingByHeaders("login", login, "password").equals(password)) {
			String token = "";
			HashMap<String, Object> hm = db.findValuesByHeader("login", login);
			Role role = Integer.parseInt((String) hm.get("role")) == 1 ? new Professor() : new Student();
			User user = new UserImpl(login, (String) hm.get("firstname"), (String) hm.get("lastname"), role);
			loggedInUser.put(token, user);
			//TODO: enable session
			return token;
		}
		throw new RemoteException("Invalid login and/or password!");
	}

	@Override
	public boolean register(String login, String password) throws RemoteException {
		//TODO:
		// write in file
		// add to park users
		return false;
	}

	@Override
	public void forgotPassword(String login) throws RemoteException {
		//TODO:
		// email
	}

	@Override
	public boolean logoff() throws RemoteException {
		//TODO:
		// close session
		// remove from park users
		return false;
	}

}
