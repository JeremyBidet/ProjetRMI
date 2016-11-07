
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Commentary extends Remote{

	int getMark() throws RemoteException;
	
	User getAuthor() throws RemoteException;

	String getComment() throws RemoteException;
}
