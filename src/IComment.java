import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IComment extends Remote {
	
	public int getMark() throws RemoteException;

	public IUser getAuthor() throws RemoteException;

	public String getComment() throws RemoteException;
	
}
