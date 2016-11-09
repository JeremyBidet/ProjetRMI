
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class CommentImpl extends UnicastRemoteObject implements Comment {

	private static final long serialVersionUID = -3669043204177646632L;

	private final int mark;
	private final User author;
	private final String comment;

	public CommentImpl(int mark, User author, String comment) throws RemoteException {
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
