

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserved extends Remote{

	public void subscribe(IObserver o) throws RemoteException;
	public void unsubscribe(IObserver o) throws RemoteException;
	public void changeValue(int val) throws RemoteException;
	
	
}
