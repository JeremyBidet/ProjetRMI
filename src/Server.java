import java.rmi.Naming;

public class Server {

	public static void main(String[] args) {
		try {
			IAuthentication auth = new Authentication();
			IPark park = new Park();

			//admin@rmi.net pass@word1 531cd7bcd239add214ba4cf98f079699ac768248
			//jbidet@etud.u-pem.fr pass@word1 531cd7bcd239add214ba4cf98f079699ac768248
			//eramos@etud.u-pem.fr pass@word2 c1e32f2202e722ed8148727456754b373a231f2c
			//rforax@igm.u-pem.fr pass@word3 2bfc3c09b05aa9912c2ed1eae9114d256000b03e
			//jramsa@etud.u-pem.fr pass@word4 21dbd6c1ea7e975c8c24f2649daae9c0c533ecee
			//mpbeal@igm.u-pem.fr pass@word5 2a20be1bf4c26018261693f245dac7a7231aeb24
			
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
			
			Naming.rebind("rmi://localhost/AuthenticationService", auth);
			Naming.rebind("rmi://localhost/ParkService", park);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
