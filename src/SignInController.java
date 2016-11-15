import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
	public void initialize() {
		this.login.setAccessibleHelp("The login need to be your registered e-mail!");
		this.login.setTooltip(new Tooltip());
		this.login.setOnInputMethodTextChanged(value -> {
			String text = this.login.getText();
			if(!text.matches(Utils.mailRegex)) {
				// TODO: show something like red border to tell the user that input does not match pattern
			} else {
				// TODO: turn back to orignal view (when data match pattern) such as black border
			}
		});
		
		this.password.setAccessibleHelp("Please enter a password!");
		this.password.setTooltip(new Tooltip());
		
		this.signIn.setOnAction(value -> {
			try {
				// TODO: add controls for field: they need to be filled
				String token = _MainClient.auth.login(login.getText(), Utils.sha1(password.getText()));
				MainAppStage mastage = new MainAppStage(token);
				mastage.setUserData(token);
				mastage.show();
				this.signIn.getScene().getWindow().hide();
			} catch (AuthenticationException e) {
				// XXX: e can contains either "Invalid login/password" or "Already logged in" exceptions
				// XXX: show pop-up with e.getMessage() content
				javax.swing.JOptionPane.showMessageDialog(null,e.getMessage()); 
			} catch (RemoteException e) {
				// XXX: show pop-up "Connection issue...\nPlease restart application."
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application."); 
			} catch (IOException e) {
				// XXX: show pop-up "Application issue...\nPlease restart application."
				javax.swing.JOptionPane.showMessageDialog(null,"Application issue...\nPlease restart application."); 
			}
		});
		
		this.signUp.setOnAction(value -> {
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
		});
	}

}
