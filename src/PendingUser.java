import java.rmi.RemoteException;
import java.util.Map;

public class PendingUser implements Map.Entry<IUser, Long>, Comparable<PendingUser> {

	private IUser key;
	private Long value;

	public PendingUser(IUser user) {
		this.key = user;
		this.value = new Long(System.currentTimeMillis());
	}

	@Override
	public IUser getKey() {
		return this.key;
	}

	@Override
	public Long getValue() {
		return this.value;
	}

	@Override
	public Long setValue(Long value) {
		long old = this.value;
		this.value = value;
		return old;
	}

	@Override
	public String toString() {
		return this.key.toString() + "\t(" + this.value + ")";
	}
	
	@Override
	public boolean equals(Object o) {
		return ((PendingUser) o).key.equals(this.key);
	}

	@Override
	public int compareTo(PendingUser o) {
		try {
			int priority_order = this.key.getRole().priority - o.key.getRole().priority;
			int time_order = this.value.compareTo(o.value);
			return priority_order != 0 ? priority_order : time_order == 0 ? 1 : time_order;
		} catch (RemoteException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
