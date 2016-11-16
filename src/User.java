
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class User extends UnicastRemoteObject implements IUser {
	
	private static final long serialVersionUID = 5158506707349816920L;
	private final String login;
	private final String firstname;
	private final String lastname;
	private final Role role;

	protected User(String login, String firstname, String lastname, Role role) throws RemoteException {
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
		return this.login + ":" + this.role.name() + ":" + this.role.name();
	}

	@Override
	public boolean equals(Object o) {
		return ((User) o).login.equals(this.login);
	}
	
	public boolean equals(String login) {
		return this.login.equals(login);
	}
	//ajouter la méthode newValue( afficher dans la console un message ou il y a le nom de l'utilisateur qui est notifié ) ;

}
