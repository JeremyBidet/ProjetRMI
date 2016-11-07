
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Professor extends UnicastRemoteObject implements Role {

	protected Professor() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public String getRole() {
		return "Professor";
	}

	@Override
	public boolean canAddVehicle() {
		return true;
	}

	@Override
	public boolean canRemoveVehicle() {
		return true;
	}

	@Override
	public int getPriority() {
		return 1;
	}

}
