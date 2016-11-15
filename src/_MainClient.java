import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SuppressWarnings("deprecation")
public class _MainClient extends Application {

	public static IAuthentication auth;
	public static IPark park;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
		primaryStage.setTitle("Sign In");
		primaryStage.setScene(new Scene(root));
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		try {
			String codebase = "file:///home/whyt/workspace/ProjetRMI/src/";
			System.setProperty("java.rmi.server.codebase", codebase);
			System.setProperty("java.security.policy", "/home/whyt/workspace/ProjetRMI/resources/all.policy");
			System.setSecurityManager(new RMISecurityManager());
			
			_MainClient.auth = (IAuthentication) Naming.lookup("rmi://localhost:1099/AuthenticationService");
			_MainClient.park = (IPark) Naming.lookup("rmi://localhost:1099/ParkService");
			//chargement notifs observer
			
			Application.launch(_MainClient.class, args);
			
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
					System.out.println("["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + " @"+v.getPrice()+"$");
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
			int pending_p = park.getPendingPosition(token, "1");
			System.out.println(pending_p);
			IUser user = park.getRental(token, "1");
			System.out.println("Current rental: " + user.getLogin());
			System.out.println("jbidet rent 1 (pending): " + park.rentVehicle(token, "1"));
			pending_p = park.getPendingPosition(token, "1");
			System.out.println(pending_p);
			user = park.getRental(token, "1");
			System.out.println("Current rental: " + user.getLogin());
			///////////////////////////////////////////////////////////////////////////////
			
			System.out.println("jbidet rent 2: " + park.rentVehicle(token, "2"));
			
			System.out.println("jbidet rent ac-338-xm: " + park.rentVehicle(token, "AC-338-XM"));
			System.out.println("jbidet return ac-338-xm: " + park.rentVehicle(token2, "AC-338-XM"));
			System.out.println("jbidet return ac-338-xm: " + park.returnVehicle(token, "AC-338-XM"));
			
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("model", "Vegan");
			vehicles = park.searchBy(filters);
			System.out.println("Vegan list (1 res): " + vehicles.stream().map(v -> {
				try {
					return "["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + " @"+v.getPrice()+"$";
				} catch (RemoteException e) {
					e.printStackTrace();
					return "";
				}
			}).reduce((v1,v2) -> v1 + "\n" + v2).get());		
			
			Map<String, Object> filters2 = new HashMap<String, Object>();
			filters2.put("year", 2009);
			vehicles = park.searchBy(filters2);
			System.out.println("2009 list (4 res): " + vehicles.stream().map(v -> {
				try {
					return "["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + " @"+v.getPrice()+"$";
				} catch (RemoteException e) {
					e.printStackTrace();
					return "";
				}
			}).reduce((v1,v2) -> v1 + "\n" + v2).get());
			
			filters.putAll(filters2);
			vehicles = park.searchBy(filters);
			System.out.println("Vegan/2009 list (1 res): " + vehicles.stream().map(v -> {
				try {
					return "["+v.getMatricul()+"] " + v.getModel() + "("+v.getYear()+") " + " @"+v.getPrice()+"$";
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
