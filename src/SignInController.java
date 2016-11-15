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
			// TODO: show pop-up "Connection issue...\nPlease restart application."
		} catch (IOException e) {
			// TODO: show pop-up "Application issue...\nPlease restart application."
		} catch (AuthenticationException e) {
			// TODO: e can contains either "Invalid login/password" or "Already logged in" exceptions
			// TODO: show pop-up with e.getMessage() content
		}
	}
	
	@FXML
	private void registerButtonHandler() {
		try {
			SignUpStage sustage = new SignUpStage();
			sustage.show();
			this.signIn.getScene().getWindow().hide();
		} catch (RemoteException e) {
			// TODO: show pop-up "Connection issue...\nPlease restart application."
		} catch (IOException e) {
			// TODO: show pop-up "Application issue...\nPlease restart application."
		}
	}

}
