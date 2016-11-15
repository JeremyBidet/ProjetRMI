import java.rmi.RemoteException;

public class ParkException extends RemoteException {
	
	private static final long serialVersionUID = 2268510614639550246L;

	public ParkException(String message) {
		super(message);
	}

}
