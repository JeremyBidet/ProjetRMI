import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class Authentication extends UnicastRemoteObject implements IAuthentication {

	private static final long serialVersionUID = 7031083944660693289L;

	private static final String usersDBPath = "/home/whyt/workspace/ProjetRMI/resources/users";
	
	private static final HashMap<String, IUser> loggedInUser = new HashMap<String, IUser>();
	
	public static IUser getUser(String token) {
		return loggedInUser.get(token);
	}
	
	protected Authentication() throws RemoteException {
		super();
	}

	@Override
	public String login(String login, String password) throws RemoteException, AuthenticationException {
		if(loggedInUser.values().stream().anyMatch(u -> {
			try {
				return u.getLogin().equals(login);
			} catch (RemoteException e) {
				return false;
			}
		})) {
			throw new AuthenticationException("Already logged in!");
		}
		Utils.DB db = Utils.parseDB(usersDBPath);
		if(db.findSiblingByHeaderValue("login", login, "password").equals(password)) {
			String token = Utils.sha1(String.valueOf(System.currentTimeMillis()));
			HashMap<String, Object> hm = db.findValuesByHeader("login", login);
			Role role = Role.getById((int) hm.get("role"));
			IUser user = new User(login, (String) hm.get("firstname"), (String) hm.get("lastname"), role);
			loggedInUser.put(token, user);
			return token;
		} else {
			throw new AuthenticationException("Invalid login and/or password!");
		}
	}

	@Override
	public String register(String login, String firstname, String lastname, int role, String password) throws RemoteException, AuthenticationException {
		if(Utils.insertDB(usersDBPath, new Object[]{login, firstname, lastname, role, password} )) {
			String token = Utils.sha1(String.valueOf(System.currentTimeMillis()));
			loggedInUser.put(token, new User(login, firstname, lastname, Role.getById(role)));
			return token;
		} else {
			throw new AuthenticationException("This login already exists!");
		}
	}

	@Override
	public void forgotPassword(String login) throws RemoteException {
		//TODO: send new random password by email and update password in db
	}

	@Override
	public boolean logoff(String token) throws RemoteException, AuthenticationException {
		if(loggedInUser.containsKey(token)) {
			loggedInUser.remove(token);
			return true;
		}
		throw new AuthenticationException("You are not logged in!");
	}

}
