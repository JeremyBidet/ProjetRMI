import java.io.IOException;
import java.rmi.RemoteException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class WSSignUpController {
	
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
	private ChoiceBox<String> registerCurrency;
	
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
		
		this.registerCurrency.setAccessibleHelp("Please chose a profile!");
		this.registerCurrency.setTooltip(new Tooltip());
		try {
			ObservableList<String> currencies = FXCollections.observableArrayList(_MainWSClient.bank.getCurrencies());
			this.registerCurrency.setItems(currencies);
		} catch (RemoteException e1) {
			javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
		}
		
		this.buttonSignUp.setOnAction(value -> {
			try {
				// TODO: add controls for field: they need to be filled
				String token = _MainWSClient.auth.register(registerEmail.getText(), registerFirstname.getText(), registerLastname.getText(), Role.Professor.ordinal(), Utils.sha1(registerPassword.getText()));
				_MainWSClient.bank.addAccount(registerEmail.getText(), 0, registerFirstname.getText(), registerLastname.getText(), registerCurrency.getSelectionModel().getSelectedItem());
				WebGuiStage wgstage = new WebGuiStage(token, registerEmail.getText());
				wgstage.setUserData(token);
				wgstage.show();
				this.buttonSignUp.getScene().getWindow().hide();
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null,e.getMessage());
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			} catch (IOException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Application issue...\nPlease restart application."); 
			}
		});
	}

}
