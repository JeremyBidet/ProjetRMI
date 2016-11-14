import java.rmi.Naming;
import java.rmi.RMISecurityManager;

@SuppressWarnings("deprecation")
public class _OldClientFx {

	try {
		String codebase = "file:///home/whyt/workspace/ProjetRMI/src/";
		System.setProperty("java.rmi.server.codebase", codebase);
		System.setProperty("java.security.policy", "/home/whyt/workspace/ProjetRMI/resources/all.policy");
		System.setSecurityManager(new RMISecurityManager());
		
		IAuthentication auth = (IAuthentication) Naming.lookup("rmi://localhost/AuthenticationService");
	
}
