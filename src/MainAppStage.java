import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainAppStage extends Stage {
	
	public MainAppStage(String token) throws IOException {
		super();
		Parent root = FXMLLoader.load(getClass().getResource("/MainApp.fxml"));
		super.setTitle("UPEM Rent-a-car");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
		super.setOnCloseRequest(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (AuthenticationException e) {
				// XXX: show pop-up with AuthenticationException message "You are not logged in!"
				javax.swing.JOptionPane.showMessageDialog(null,"You are not logged in!"); 
			} catch (RemoteException e) {
				// XXX: show pop-up "Connection issue...\nPlease restart application."
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onCloseRequestProperty();
		});
		super.setOnHiding(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (AuthenticationException e) {
				// XXX: show pop-up with AuthenticationException message "You are not logged in!"
				javax.swing.JOptionPane.showMessageDialog(null,"You are not logged in!");
			} catch (RemoteException e) {
				// XXX: show pop-up "Connection issue...\nPlease restart application."7
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onHidingProperty();
		});
	}

	public MainAppStage(String token, StageStyle style) throws IOException {
		super(style);
		Parent root = FXMLLoader.load(getClass().getResource("/MainApp.fxml"));
		super.setTitle("UPEM Rent-a-car");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
		super.setOnCloseRequest(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (AuthenticationException e) {
				// XXX: show pop-up with AuthenticationException message "You are not logged in!"
				javax.swing.JOptionPane.showMessageDialog(null,"You are not logged in!"); 
			} catch (RemoteException e) {
				// XXX: show pop-up "Connection issue...\nPlease restart application."
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onCloseRequestProperty();
		});
		super.setOnHiding(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (AuthenticationException e) {
				// XXX: show pop-up with AuthenticationException message "You are not logged in!"
				javax.swing.JOptionPane.showMessageDialog(null,"You are not logged in!"); 
			} catch (RemoteException e) {
				// XXX: show pop-up "Connection issue...\nPlease restart application."
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onHidingProperty();
		});
	}

}
