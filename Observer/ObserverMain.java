
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class ObserverMain {

	public static void main(String[] args) {

		try{
			String codebase = "file:///G:/UPEM_Cours/WebServices/ProjetWebService/Observed/";
			System.setProperty("java.rmi.server.codebase",codebase); 
			System.setProperty(" java.security.policy", "file:///G:/UPEM_Cours/WebServices/ProjetWebService/Observer/security.policy");  
			System.setSecurityManager(new RMISecurityManager()); 
			IObserver observer1 = new Observer();
			System.out.println("ok1");
			IObserver observer2 = new Observer();
			System.out.println("ok2");
			IObserved sub =(IObserved) Naming.lookup("rmi://localhost:1099/observed");
			System.out.println("ok3");
			sub.subscribe(observer1);
			sub.subscribe(observer2);
			
			
			
	}catch(Exception e){e.printStackTrace();}
	}
}
