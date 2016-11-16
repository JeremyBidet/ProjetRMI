
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class Observed extends UnicastRemoteObject implements IObserved{

	private ArrayList<IObserver> listOfObservers;
	private int value= 0 ;
	
	public Observed() throws RemoteException {
		listOfObservers = new ArrayList<IObserver>();
		System.out.println("Observed()");
	}

	@Override
	public synchronized void subscribe(IObserver o) throws RemoteException {
		System.out.println(o.getClass() + " s'enregistre");
		listOfObservers.add(o);
	}

	@Override
	public synchronized void unsubscribe(IObserver o) throws RemoteException {
		listOfObservers.remove(o);
	}
	
	public void changeValue(int newVal) throws RemoteException{
		this.value = newVal;
		for(IObserver o : listOfObservers)
		{
			try {
				o.newValue(newVal);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}
