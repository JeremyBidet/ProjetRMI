import java.rmi.Naming;

public class _MainServer {
	
	public static IAuthentication auth;
	public static IPark park;
	
	public static void main(String[] args) {
		try {
			auth = new Authentication();
			park = new Park();
			
			//admin@rmi.net pass@word1 531cd7bcd239add214ba4cf98f079699ac768248
			//jbidet@etud.u-pem.fr pass@word1 531cd7bcd239add214ba4cf98f079699ac768248
			//eramos@etud.u-pem.fr pass@word2 c1e32f2202e722ed8148727456754b373a231f2c
			//rforax@igm.u-pem.fr pass@word3 2bfc3c09b05aa9912c2ed1eae9114d256000b03e
			//jramsa@etud.u-pem.fr pass@word4 21dbd6c1ea7e975c8c24f2649daae9c0c533ecee
			//mpbeal@igm.u-pem.fr pass@word5 2a20be1bf4c26018261693f245dac7a7231aeb24
			
			_MainServer.populate(true);
			
			Naming.rebind("rmi://localhost:1099/AuthenticationService", auth);
			Naming.rebind("rmi://localhost:1099/ParkService", park);

			// TODO: tu parcours la liste de vehicules puis tu parcours la liste des utilisateurs de chaque vehicule 
			//et tu fais appel à la methode onChangeValue() ) et tu le désinscris
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void populate(boolean populate) {
		if(!populate) {
			return;
		}
		try {
			String token1 = auth.login("admin@rmi.net", "531cd7bcd239add214ba4cf98f079699ac768248");
			String token2 = auth.login("rforax@igm.u-pem.fr", "2bfc3c09b05aa9912c2ed1eae9114d256000b03e");
			String token3 = auth.login("jramsa@etud.u-pem.fr", "21dbd6c1ea7e975c8c24f2649daae9c0c533ecee");
			
			park.addVehicle(token1, "1", 2016, "Clio", 5000);
			park.addVehicle(token1, "2", 2009, "Megane", 2000);
			park.addVehicle(token1, "3", 2014, "Twingo", 2);
			park.addVehicle(token1, "4", 2009, "Vegan", 10000);
			park.addVehicle(token1, "5", 2000, "208", 13000);
			park.addVehicle(token1, "6", 2009, "Vegan", 10000);
			park.addVehicle(token1, "7", 2000, "208", 13000);
			park.addVehicle(token1, "8", 2010, "Aston Martin V12 Vantage", 130000);
			park.addVehicle(token1, "9", 2016, "BMW M4 45", 100000);
			park.addVehicle(token1, "10", 2017, "Nissan GTR Nismo", 160000);
			
			park.addComment(token1, "1", "wahou", 5);
			park.addComment(token2, "1", "bof bof bof je prefere me la péter avec mes regex", 1);
			park.addComment(token1, "1", "Mouai il a tout sali le mec d'avant", 2);
			park.addComment(token2, "1", "jen ai marre d'ecrire des comments", 1);
			
			try { park.rentVehicle(token1, "1"); } catch(Exception e) {}
			try { park.rentVehicle(token2, "1"); } catch(Exception e) {}
			try { park.rentVehicle(token3, "1"); } catch(Exception e) {}
			try { park.rentVehicle(token2, "2"); } catch(Exception e) {}
			try { park.rentVehicle(token1, "2"); } catch(Exception e) {}
			try { park.rentVehicle(token3, "3"); } catch(Exception e) {}
			
			auth.logoff(token1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
