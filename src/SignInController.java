import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignInController {
	
	@FXML
	private TextField login;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Button signIn;
	
	@FXML
	private Button signUp;
	
	@FXML
	private void loginButtonHandler() {
		try {
			String token = _MainClient.auth.login(login.getText(), Utils.sha1(password.getText()));
			MainAppStage mastage = new MainAppStage();
			mastage.setUserData(token);
			mastage.show();
			this.signIn.getScene().getWindow().hide();
		} catch (RemoteException e) {
			// XXX: show pop-up "Connection issue...\nPlease restart application."
			javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application."); 

		} catch (IOException e) {
			// XXX: show pop-up "Application issue...\nPlease restart application."
			javax.swing.JOptionPane.showMessageDialog(null,"Application issue...\nPlease restart application."); 

		} catch (AuthenticationException e) {
			// XXX: e can contains either "Invalid login/password" or "Already logged in" exceptions
			// XXX: show pop-up with e.getMessage() content
			javax.swing.JOptionPane.showMessageDialog(null,e.getMessage()); 
		}
	}
	
	@FXML
	private void registerButtonHandler() {
		try {
			SignUpStage sustage = new SignUpStage();
			sustage.show();
			this.signIn.getScene().getWindow().hide();
		} catch (RemoteException e) {
			// XXX: show pop-up "Connection issue...\nPlease restart application."
			javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application."); 

		} catch (IOException e) {
			// XXX: show pop-up "Application issue...\nPlease restart application."
			javax.swing.JOptionPane.showMessageDialog(null,"Application issue...\nPlease restart application."); 

		}
	}

}
