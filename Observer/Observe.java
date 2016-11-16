import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Observe {

	public static void main(String[] args) throws RemoteException {
		Publisher pub = new BasicPublisher();
		
		String codebase = "file:///F:/publisher/"; 
		System.setProperty("java.rmi.server.codebase", codebase); 
		System.setProperty("java.security.policy", "no.policy"); 
		System.setSecurityManager(new RMISecurityManager());
		Publisher pub = (Publisher) Naming.lookup("rmi://localhost/Publish");
	}

}
