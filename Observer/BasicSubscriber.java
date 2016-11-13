import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BasicSubscriber extends UnicastRemoteObject implements Subscriber {

	int value = 0;
	protected BasicSubscriber() throws RemoteException {
		super();
	}

	@Override
	public void update(int val) {
		this.value = val;		
	}

}
