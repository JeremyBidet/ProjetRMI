
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserImpl extends UnicastRemoteObject implements User {

	private static final long serialVersionUID = 5158506707349816920L;
	private final String login;
	private final String firstname;
	private final String lastname;
	private final Role role;

	protected UserImpl(String login, String firstname, String lastname, Role role) throws RemoteException {
		this.login = login;
		this.firstname = firstname;
		this.lastname = lastname;
		this.role = role;
	}

	@Override
	public String getLogin() throws RemoteException {
		return this.login;
	}

	@Override
	public String getFirstname() throws RemoteException {
		return this.firstname;
	}

	@Override
	public String getLastname() throws RemoteException {
		return this.lastname;
	}

	@Override
	public Role getRole() throws RemoteException {
		return role;
	}

	@Override
	public String toString() {
		return this.login + ":" + this.role.getRoleName() + ":" + this.role.getPriority();
	}

	@Override
	public boolean equals(Object o) {
		return ((UserImpl) o).login.equals(this.login);
	}

}
