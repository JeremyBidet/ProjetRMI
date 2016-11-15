import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController {
	
	@FXML
	private TextField registerEmail;
	
	@FXML
	private TextField registerLastname;
	
	@FXML
	private TextField registerFirstname;
	
	@FXML
	private PasswordField registerPassword;
	
	@FXML
	private PasswordField registerPassword2;
	
	@FXML
	private ChoiceBox<String> registerProfile;
	
	@FXML
	private Button buttonSignUp;
	
	@FXML
	private void signUpHandler() {
		try {
			String token = _MainClient.auth.register(registerEmail.getText(), registerLastname.getText(), registerFirstname.getText(), Role.valueOf(registerProfile.getValue()).ordinal(), Utils.sha1(registerPassword.getText()));
			MainAppStage mastage = new MainAppStage();
			mastage.setUserData(token);
			mastage.show();
			this.buttonSignUp.getScene().getWindow().hide();
		} catch (RemoteException e) {
			// XXX: show pop-up "Connection issue...\nPlease restart application."
			javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
		} catch (IOException e) {
			// XXX: show pop-up "Application issue...\nPlease restart application."
			javax.swing.JOptionPane.showMessageDialog(null,"Application issue...\nPlease restart application."); 
		} catch (AuthenticationException e) {
			// XXX: e contains "This login already exists!" exceptions
			// XXX: show pop-up with e.getMessage() content
			javax.swing.JOptionPane.showMessageDialog(null,e.getMessage()); 
		}
	}

}
