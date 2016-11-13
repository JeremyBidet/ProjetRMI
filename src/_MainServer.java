import java.rmi.Naming;

public class _MainServer {

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
			
			Naming.rebind("rmi://localhost/AuthenticationService", auth);
			Naming.rebind("rmi://localhost/ParkService", park);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
