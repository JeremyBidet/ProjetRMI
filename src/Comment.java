
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Comment extends Remote {

	int getMark() throws RemoteException;

	User getAuthor() throws RemoteException;

	String getComment() throws RemoteException;
}
