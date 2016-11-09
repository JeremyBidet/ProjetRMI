
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Student extends UnicastRemoteObject implements Role {

	private static final long serialVersionUID = 1L;

	protected Student() throws RemoteException {
		super();
	}

	@Override
	public String getRoleName() {
		return "Student";
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
		return 2;
	}

}
