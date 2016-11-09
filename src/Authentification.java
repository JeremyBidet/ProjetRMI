import java.rmi.Remote;

public interface Authentification extends Remote {

	String login(String login, String password);
	boolean register(String login, String password);
	void forgotPassword(String login);
	boolean logoff();
	
}
