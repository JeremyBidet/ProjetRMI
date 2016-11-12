import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class Authentication extends UnicastRemoteObject implements IAuthentication {

	private static final long serialVersionUID = 7031083944660693289L;

	private static final HashMap<String, IUser> loggedInUser = new HashMap<String, IUser>();
	
	public static IUser getUser(String token) {
		return loggedInUser.get(token);
	}
	
	protected Authentication() throws RemoteException {
		super();
	}

	@Override
	public String login(String login, String password) throws RemoteException {
		if(loggedInUser.values().stream().anyMatch(u -> {
			try {
				return u.getLogin().equals(login);
			} catch (RemoteException e) {
				return false;
			}
		})) {
			throw new RemoteException("Already logged in!");
		}
		Utils.DB db = Utils.parseDB("resources/users");
		try {
			if(db.findSiblingByHeaderValue("login", login, "password").equals(password)) {
				String token = Utils.sha1(String.valueOf(System.currentTimeMillis()));
				HashMap<String, Object> hm = db.findValuesByHeader("login", login);
				Role role = Role.getById((int) hm.get("role"));
				IUser user = new User(login, (String) hm.get("firstname"), (String) hm.get("lastname"), role);
				loggedInUser.put(token, user);
				return token;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		throw new RemoteException("Invalid login and/or password!");
	}

	@Override
	public String register(String login, String firstname, String lastname, int role, String password) throws RemoteException {
		Utils.insertDB("resources/users", new Object[]{login, firstname, lastname, role, password} );
		String token = Utils.sha1(String.valueOf(System.currentTimeMillis()));
		loggedInUser.put(token, new User(login, firstname, lastname, Role.getById(role)));
		return token;
	}

	@Override
	public void forgotPassword(String login) throws RemoteException {
		//TODO:
		// email
	}

	@Override
	public boolean logoff(String token) throws RemoteException {
		if(loggedInUser.containsKey(token)) {
			loggedInUser.remove(token);
		}
		return false;
	}

}
