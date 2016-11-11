import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.List;

public class Client {

	public static void main(String[] args) {
		
		try {
			String codebase = "file:///home/whyt/workspace/ProjetRMI/src/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "resources/no.policy");
			System.setSecurityManager(new RMISecurityManager());
			
			IAuthentication auth = (IAuthentication) Naming.lookup("rmi://localhost/AuthenticationService");
			IPark park = (IPark) Naming.lookup("rmi://localhost/ParkService");
			
			park.addVehicle(token, matricul, year, model, price);
			park.addComment(token, matricul, comment, mark);
			park.buy(token, vehicle);
			park.getComments(token, matricul);
			park.getVehicles(token);
			park.removeVehicle(token, matricul);
			park.rentVehicle(token, vehicle);
			park.returnVehicle(token, vehicle);
			park.searchByModel(model);
			park.searchByYear(year);
			
			System.out.println(park);
		} catch(Exception e) {
			System.err.println("Trouble: " + e);
		}
	}

}
