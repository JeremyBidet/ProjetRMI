import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainAppStage extends Stage {
	
	private String token;
	
	public MainAppStage() throws IOException {
		super();
		this.token = (String) super.getUserData();
		Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
		super.setTitle("UPEM Rent-a-car");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
		super.setOnCloseRequest(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (RemoteException e) {
				// TODO:
			} catch (AuthenticationException e) {
				// XXX: show pop-up with AuthenticationException message "You are not logged in!"
				javax.swing.JOptionPane.showMessageDialog(null,"You are not logged in!"); 
			}
			super.onCloseRequestProperty();
		});
		super.setOnHiding(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (RemoteException e) {
				// TODO:
			} catch (AuthenticationException e) {
				// XXX: show pop-up with AuthenticationException message "You are not logged in!"
				javax.swing.JOptionPane.showMessageDialog(null,"You are not logged in!"); 

			}
			super.onHidingProperty();
		});
	}
	
	public MainAppStage(StageStyle style) throws IOException {
		super(style);
		this.token = (String) super.getUserData();
		Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
		super.setTitle("UPEM Rent-a-car");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
		super.setOnCloseRequest(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (RemoteException e) {
				// TODO:
			} catch (AuthenticationException e) {
				// XXX: show pop-up with AuthenticationException message "You are not logged in!"
				javax.swing.JOptionPane.showMessageDialog(null,"You are not logged in!"); 
			}
			super.onCloseRequestProperty();
		});
		super.setOnHiding(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (RemoteException e) {
				// TODO:
			} catch (AuthenticationException e) {
				// XXX: show pop-up with AuthenticationException message "You are not logged in!"
				javax.swing.JOptionPane.showMessageDialog(null,"You are not logged in!"); 
			}
			super.onHidingProperty();
		});
	}
	
	public String getToken() {
		return this.token;
	}

}
