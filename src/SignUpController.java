import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

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
	public void initialize() {
		this.registerEmail.setAccessibleHelp("The login need to be an e-mail!");
		this.registerEmail.setTooltip(new Tooltip());
		this.registerEmail.setOnInputMethodTextChanged(value -> {
			String text = this.registerEmail.getText();
			if(!text.matches(Utils.mailRegex)) {
				// TODO: show something like red border to tell the user that input does not match pattern
			} else {
				// TODO: turn back to orignal view (when data match pattern) such as black border
			}
		});
		
		this.registerFirstname.setAccessibleHelp("Enter your firstname!");
		this.registerFirstname.setTooltip(new Tooltip());
		
		this.registerLastname.setAccessibleHelp("Enter your lastname!");
		this.registerLastname.setTooltip(new Tooltip());
		
		this.registerPassword.setAccessibleHelp("Please enter a password!");
		this.registerPassword.setTooltip(new Tooltip());
		
		this.registerPassword2.setAccessibleHelp("Passwords need to match!");
		this.registerPassword2.setTooltip(new Tooltip());
		this.registerPassword2.setOnInputMethodTextChanged(value -> {
			String text = this.registerPassword2.getText();
			if(!text.equals(this.registerPassword.getText())) {
				// TODO: show something like red border to tell the user that input does not match other password
			} else {
				// TODO: turn back to orignal view (when data match pattern) such as black border
			}
		});
		
		this.registerProfile.setAccessibleHelp("Please chose a profile!");
		this.registerProfile.setTooltip(new Tooltip());
		
		this.buttonSignUp.setOnAction(value -> {
			try {
				// TODO: add controls for field: they need to be filled
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
		});
	}

}
