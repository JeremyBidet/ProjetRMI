
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class ObservedMain {

	public static void main(String[] args) {
			
		try {
			
			String codeBase="file:///G:/UPEM_Cours/WebServices/ProjetWebService/Observer/";
			System.setProperty("java.rmi.server.codebase", codeBase); 
			System.setProperty(" java.security.policy", "security.policy");  
			System.setSecurityManager(new RMISecurityManager()); 
			IObserved o = new Observed();
			Naming.rebind("rmi://localhost:1099/observed",o);
			for (int i=1; i<=30; i++) {
				o.changeValue(i*10);
				System.out.println("o.changeValue() = "+ i*10);
				Thread.sleep(1000);
			} 
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
