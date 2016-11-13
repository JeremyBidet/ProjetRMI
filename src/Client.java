import java.util.List;
import java.util.function.Consumer;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

@SuppressWarnings("deprecation")
public class Client {

	public static void main(String[] args) {
		
		try {
			String codebase = "file:///home/whyt/workspace/ProjetRMI/src/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "/home/whyt/workspace/ProjetRMI/resources/all.policy");
			System.setSecurityManager(new RMISecurityManager());
			
			IAuthentication auth = (IAuthentication) Naming.lookup("rmi://localhost/AuthenticationService");
			IPark park = (IPark) Naming.lookup("rmi://localhost/ParkService");
			
			/**
			 * Park & Auth INIT...
			 */
			String login = "jbidet@etud.u-pem.fr";
			String password = "531cd7bcd239add214ba4cf98f079699ac768248";
			String token = auth.login(login, password);
			
			String token1 = auth.login("admin@rmi.net", "531cd7bcd239add214ba4cf98f079699ac768248");
			String token2 = auth.login("rforax@igm.u-pem.fr", "2bfc3c09b05aa9912c2ed1eae9114d256000b03e");
			String token3 = auth.login("jramsa@etud.u-pem.fr", "21dbd6c1ea7e975c8c24f2649daae9c0c533ecee");
			
			park.addVehicle(token1, "1", 2016, "Clio", 5000);
			park.addVehicle(token1, "2", 2009, "Megane", 2000);
			park.addVehicle(token1, "3", 2014, "Twingo", 2);
			park.addVehicle(token1, "4", 2009, "Vegan", 10000);
			park.addVehicle(token1, "5", 2000, "208", 13000);
			
			park.addComment(token1, "1", "wahou", 5);
			park.addComment(token2, "1", "bof bof bof je prefere me la péter avec mes regex", 1);
			park.addComment(token1, "1", "Mouai il a tout sali le mec d'avant", 2);
			park.addComment(token2, "1", "jen ai marre d'ecrire des comments", 1);
			
			park.rentVehicle(token1, "1");
			park.rentVehicle(token3, "1");
			
			Consumer<IVehicle> c1 = (v) -> {
				try {
					System.out.println("["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + "#"+v.getNbRented() + " @"+v.getPrice()+"$");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			};
			
			Consumer<IComment> c2 = (c) -> {
				try {
					System.out.println(c.getAuthor().getLogin()+": " + c.getComment() + "("+c.getMark()+")");
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			};
			
			/**
			 * Park TESTS...
			 */
			List<IVehicle> vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			System.out.println("jbidet add ac-338-xm: " + park.addVehicle(token, "AC-338-XM", 2009, "Opel Corsa Rouge", 999999999));
			System.out.println("jbidet add aa-666-aa: " + park.addVehicle(token, "AA-666-AA", 2009, "Opel Corsa Rouge", 666666666));
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			
			List<IComment> comments = park.getComments(token, "AC-338-XM");
			comments.stream().forEach(c2);
			System.out.println("jbidet comment ac-338-xm: " + park.addComment(token, "AC-338-XM", "La plus belle", 5));
			comments = park.getComments(token, "AC-338-XM");
			comments.stream().forEach(c2);
			
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			System.out.println("jbidet remove 5: " + park.removeVehicle(token, "5"));
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			
			// TODO: check pending list before and after rent try /////////////////////////
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			System.out.println("jbidet rent 1 (pending): " + park.rentVehicle(token, "1"));
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			///////////////////////////////////////////////////////////////////////////////
			
			// TODO: check rental list before and after rent success ////////////////////////////
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			System.out.println("jbidet rent 2: " + park.rentVehicle(token, "2"));
			System.out.println("jbidet rent ac-338-xm: " + park.rentVehicle(token, "AC-338-XM"));
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			/////////////////////////////////////////////////////////////////////////////////////
			
			
			
			// TODO: check rental list before and after return //////////////////////////////////////
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			System.out.println("jbidet return ac-338-xm: " + park.returnVehicle(token, "AC-338-XM"));
			vehicles = park.getVehicles(token);
			vehicles.stream().forEach(c1);
			/////////////////////////////////////////////////////////////////////////////////////////
			
			
			vehicles = park.searchByModel("Vegan");
			System.out.println("Vegan list (1 res): " + vehicles.stream().map(v -> {
				try {
					return "["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + "#"+v.getNbRented() + " @"+v.getPrice()+"$";
				} catch (RemoteException e) {
					e.printStackTrace();
					return "";
				}
			}).reduce((v1,v2) -> v1 + "\n" + v2).get());
			
			vehicles = park.searchByModel("logan");
			System.out.println("logan list (0 res): " + vehicles.stream().map(v -> {
				try {
					return "["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + "#"+v.getNbRented() + " @"+v.getPrice()+"$";
				} catch (RemoteException e) {
					e.printStackTrace();
					return "";
				}
			}).reduce((v1,v2) -> v1 + "\n" + v2).get());			
			
			vehicles = park.searchByYear(2009);
			System.out.println("2009 list (4 res): " + vehicles.stream().map(v -> {
				try {
					return "["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + "#"+v.getNbRented() + " @"+v.getPrice()+"$";
				} catch (RemoteException e) {
					e.printStackTrace();
					return "";
				}
			}).reduce((v1,v2) -> v1 + "\n" + v2).get());
			
			vehicles = park.searchByYear(98);
			System.out.println("98 list (0 res): " + vehicles.stream().map(v -> {
				try {
					return "["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + "#"+v.getNbRented() + " @"+v.getPrice()+"$";
				} catch (RemoteException e) {
					e.printStackTrace();
					return "";
				}
			}).reduce((v1,v2) -> v1 + "\n" + v2).get());			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
