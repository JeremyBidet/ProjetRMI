import java.rmi.RemoteException;

public class AuthenticationException extends RemoteException {

	private static final long serialVersionUID = 5472454312463351082L;
	
	public AuthenticationException(String message) {
		super(message);
	}

}
