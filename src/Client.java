import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

public class Client {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		try {
			String codebase = "file:///home/whyt/workspace/ProjetRMI/src/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "resources/all.policy");
			System.setSecurityManager(new RMISecurityManager());
			
			IAuthentication auth = (IAuthentication) Naming.lookup("rmi://localhost/AuthenticationService");
			IPark park = (IPark) Naming.lookup("rmi://localhost/ParkService");
			
			/**
			 * INIT
			 */
			String token1 = auth.login("admin@rmi.net", "531cd7bcd239add214ba4cf98f079699ac768248");
			String token2 = auth.login("rforax@igm.u-pem.fr", "2bfc3c09b05aa9912c2ed1eae9114d256000b03e");
			String token3 = auth.login("jramsa@etud.u-pem.fr", "21dbd6c1ea7e975c8c24f2649daae9c0c533ecee");
			
			park.addVehicle(token1, "1", 2016, "Clio", 5000);
			park.addVehicle(token1, "2", 2015, "megane", 2000);
			park.addVehicle(token1, "3", 2014, "Twingo", 2);
			park.addVehicle(token1, "4", 2012, "vegan", 10000);
			park.addVehicle(token1, "5", 2000, "208", 13000);
			
			park.addComment(token1, "1", "wahou", 5);
			park.addComment(token2, "1", "bof bof bof je prefere me la péter avec mes regex", 1);
			park.addComment(token1, "1", "Mouai il a tout sali le mec d'avant", 2);
			park.addComment(token2, "1", "jen ai marre d'ecrire des comments", 1);
			
			park.rentVehicle(token1, "1");
			park.rentVehicle(token3, "1");
			
			
			/**
			 * Authentication tests...
			 * 1. log in as jbidet (existing user) => OK
			 * 2. register new user test (not existing user) => OK
			 * 3. log in as jbidet (logged in user) => ERROR
			 * 4. log off as test (logged in user) => OK
			 * 5. log off as test (not logged in user) => ERROR
			 * 6. log in as user1 (not existing user) => ERROR
			 */
			String login = "jbidet@etud.u-pem.fr";
			String password = "531cd7bcd239add214ba4cf98f079699ac768248";
			String token = "";
			String _token = "";
			String __token = "";
			String ___token = "";
			boolean logoff = false;
			boolean _logoff = false;
			try {
				token = auth.login(login, password);
				_token = auth.register("test@test.fr", "Test", "Test", Role.Professor.ordinal(), "531cd7bcd239add214ba4cf98f079699ac768248");
			} catch(RemoteException e) {
				e.printStackTrace();
			}
			try {
				__token = auth.login(login, password);
			} catch(RemoteException e) {
				e.printStackTrace();
			}
			try {
				logoff = auth.logoff(_token);
			} catch(RemoteException e) {
				e.printStackTrace();
			}
			try {
				_logoff = auth.logoff(_token);
			} catch(RemoteException e) {
				e.printStackTrace();
			}
			try {
				___token = auth.login("user1", "password");
			} catch(RemoteException e) {
				e.printStackTrace();
			}
			System.out.println("login jbidet token: " + token);
			System.out.println("register test token: " + _token);
			System.out.println("login jbidet token: " + __token);
			System.out.println("logoff test: " + logoff);
			System.out.println("logoff test: " + _logoff);
			System.out.println("login user1 token: " + ___token);
			
			/**
			 * Park tests...
			 */
			System.out.println("jbidet add ac-338-xm: " + park.addVehicle(token, "AC-338-XM", 2009, "Opel Corsa Rouge", 999999999)); //OK
			System.out.println("jbidet add aa-666-aa: " + park.addVehicle(token, "AA-666-AA", 2009, "Opel Corsa Rouge", 666666666)); // OK
			park.addVehicle("dgfsbf", "AC-338-XM", 2009, "Opel Corsa Rouge", 999999999); // ERROR
			System.out.println("jbidet comment ac-338-xm: " + park.addComment(token, "AC-338-XM", "La plus belle", 5)); // OK
			park.addComment(token, "trololo", "La plus belle", 5); // ERROR
			park.addComment("dsdfsf", "trololo", "La plus belle", 5); // ERROR
			System.out.println(park.getComments(token, "AC-338-XM")); // OK
			park.getComments(token, "sdqdqd"); // ERROR
			park.getComments("", "sdfsd"); // ERROR
			System.out.println(park.getVehicles(token)); // OK
			park.getVehicles("sdqsd"); // ERROR
			park.removeVehicle(token, "AA-666-AA"); // ERROR
			System.out.println("jbidet remove 1: " + park.removeVehicle(token, "1")); // OK
			park.removeVehicle(token, "laval"); // ERROR
			park.removeVehicle("sqd", "laval"); // ERROR
			System.out.println("jbidet rent 1 (pending): " + park.rentVehicle(token, "1")); // OK (PENDING)
			System.out.println("jbidet rent 2: " + park.rentVehicle(token, "2")); // OK
			System.out.println("jbidet rent ac-338-xm: " + park.rentVehicle(token, "AC-338-XM")); // OK
			park.rentVehicle(token, "24141"); // ERROR
			park.rentVehicle("ssqd", "1"); // ERROR
			System.out.println("jbidet return ac-338-xm: " + park.returnVehicle(token, "AC-338-XM")); // OK
			park.returnVehicle(token, "1"); // ERROR
			park.returnVehicle(token, "3"); // ERROR
			park.returnVehicle("sdqsd", "AC-338-XM"); // ERROR
			System.out.println("vegan list: " + park.searchByModel("vegan")); // OK
			System.out.println("logan list: " + park.searchByModel("logan")); // EMPTY
			System.out.println("2009 list: " + park.searchByYear(2009)); // OK
			System.out.println("98 list: " + park.searchByYear(98)); // EMPTY
			
			System.out.println(park);
		} catch(Exception e) {
			System.err.println("Trouble: " + e);
		}
	}

}
