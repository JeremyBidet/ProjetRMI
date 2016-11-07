
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserImpl extends UnicastRemoteObject implements User {
	private static final long serialVersionUID = 1L;
	private final String name;
	private final Role role;
	
	
	protected UserImpl(String name, Role role) throws RemoteException {
		this.name = name;
		this.role = role;
	}
	
	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public Role getRole() throws RemoteException {
		return role;
	}
	
	

}
