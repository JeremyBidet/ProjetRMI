
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {

	public void newValue(int val) throws RemoteException;
}
