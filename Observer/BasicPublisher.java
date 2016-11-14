import java.util.ArrayList;
import java.util.List;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;


public class BasicPublisher extends UnicastRemoteObject implements Publisher {

	private int value;
	private final List<Subscriber> subscriber = new ArrayList<>();


	protected BasicPublisher() throws RemoteException {
		super();
		this.value = (int)(Math.random());
	}

	@Override
	public void addSubscriber(Subscriber s) throws RemoteException {
		subscriber.add(s);
	}

	@Override
	public void notifyAllSubscribers() throws RemoteException {
		for (Subscriber s : subscriber) {
			s.update(value);
		}
	}

	@Override
	public void removeSubscriber(Subscriber s) throws RemoteException {
		subscriber.remove(s);	
	}
	
	@Override
	public void removeAllSubscribers() throws RemoteException {
		subscriber.clear();
		
	}
	
	public void changevalue() throws RemoteException{
		this.value = (int)(Math.random());
		notifyAllSubscribers();
	}
	
}