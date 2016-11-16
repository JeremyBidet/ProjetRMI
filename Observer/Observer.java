

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Observer extends UnicastRemoteObject implements IObserver{

	public Observer() throws RemoteException {
		System.out.println("Observer()");	
	}

	@Override
	public void newValue(int val) throws RemoteException {
		System.out.println("the New Value is : " + val);
	}

}
