import java.rmi.*;

public interface Publisher extends Remote { 
	
	public void addSubscriber(Subscriber s) throws RemoteException;
	public void notifyAllSubscribers()throws RemoteException;
	public void removeSubscriber(Subscriber s) throws RemoteException; 
	public void removeAllSubscribers() throws RemoteException; 

}