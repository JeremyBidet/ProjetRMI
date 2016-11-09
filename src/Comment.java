import java.util.Objects;

public class Comment {

	private final int mark;
	private final User author;
	private final String comment;

	public Comment(int mark, User author, String comment) {
		this.author = Objects.requireNonNull(author);
		this.mark = mark;
		this.comment = comment;
	}

	public int getMark() {
		return mark;
	}

	public User getAuthor() {
		return author;
	}

	public String getComment() {
		return comment;
	}
}
