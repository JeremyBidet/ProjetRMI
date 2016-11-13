import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class Comment extends UnicastRemoteObject implements IComment {

	private static final long serialVersionUID = 7051078013424508050L;

	private final int mark;
	private final IUser author;
	private final String comment;

	public Comment(int mark, IUser author, String comment) throws RemoteException {
		this.author = Objects.requireNonNull(author);
		this.mark = mark;
		this.comment = comment;
	}

	public int getMark() throws RemoteException {
		return mark;
	}

	public IUser getAuthor() throws RemoteException {
		return author;
	}

	public String getComment() throws RemoteException {
		return comment;
	}
}
