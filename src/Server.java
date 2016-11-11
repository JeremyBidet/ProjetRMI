import java.rmi.Naming;

public class Server {

	public static void main(String[] args) {
		try {
			IAuthentication auth = new Authentication();
			IPark park = new Park();
			
			//TODO: add some vehicles with comments and user to test
			
			Naming.rebind("rmi://localhost/AuthenticationService", auth);
			Naming.rebind("rmi://localhost/ParkService", park);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
