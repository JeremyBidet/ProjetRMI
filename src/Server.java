import java.rmi.Naming;

public class Server {

	public static void main(String[] args) {
		try {
			IAuthentication auth = new Authentication();
			IPark park = new Park();
			
			//TODO: add some vehicles with comments and user to test
			
			//admin@rmi.net pass@word1
			//jbidet@etud.u-pem.fr pass@word1
			//eramos@etud.u-pem.fr pass@word2
			//rforax@igm.u-pem.fr pass@word3
			//jramsa@etud.u-pem.fr pass@word4
			//mpbeal@igm.u-pem.fr pass@word5
			
			Naming.rebind("rmi://localhost/AuthenticationService", auth);
			Naming.rebind("rmi://localhost/ParkService", park);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
