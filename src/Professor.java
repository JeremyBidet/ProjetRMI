import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Professor extends UnicastRemoteObject implements Role {

	private static final long serialVersionUID = 5533633395426928994L;

	protected Professor() throws RemoteException {
		super();
	}

	@Override
	public String getRoleName() {
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
