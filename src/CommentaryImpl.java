
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class CommentaryImpl extends UnicastRemoteObject implements Commentary {

	private final int mark;
	private final User author;
	private final String comment;
	
	public CommentaryImpl(int mark, User author, String comment) throws RemoteException {
		this.author = Objects.requireNonNull(author);
		this.mark = mark;
		this.comment = comment;
	}
	
	@Override
	public int getMark() throws RemoteException {
		return mark;
	}

	@Override
	public User getAuthor() throws RemoteException {
		return author;
	}

	@Override
	public String getComment() throws RemoteException {
		return comment;
	}

}
