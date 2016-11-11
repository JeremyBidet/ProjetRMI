import java.rmi.Naming;

public class Server {

	public static void main(String[] args) {
		try {
			IAuthentication auth = new Authentication();
			IPark park = new Park();
			String token = auth.login("admin@rmi.net", "pass@word1");
			String token2 = auth.login("jbidet@etud.u-pem.fr", "pass@word1");
			String token3 = auth.login("rforax@igm.u-pem.fr", "pass@word3");
			
			park.addVehicle(token, "1", 2016, "Clio", 5000);
			park.addVehicle(token, "2", 2015, "megane", 2000);
			park.addVehicle(token, "3", 2014, "Twingo", 2);
			park.addVehicle(token, "4", 2012, "vegan", 10000);
			park.addVehicle(token, "5", 2000, "208", 13000);
			
			park.addComment(token, "1", "wahou", 5);
			park.addComment(token2, "1", "bof bof bof je prefere me la péter avec mes regex", 1);
			park.addComment(token, "1", "Mouai il a tout sali le mec d'avant", 2);
			park.addComment(token2, "1", "jen ai marre d'ecrire des comments", 1);
			
			park.rentVehicle(token, "1");
			park.rentVehicle(token3, "1");
			
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
