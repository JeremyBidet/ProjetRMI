import java.io.Serializable;
import java.rmi.Remote;

public interface Subscriber extends Remote, Serializable {
	
	void update(int val);
	

}
