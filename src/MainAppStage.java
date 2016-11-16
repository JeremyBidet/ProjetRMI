import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainAppStage extends Stage {
	
	private boolean loggedOff = false;
	
	public MainAppStage(String token) throws IOException {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainApp.fxml"));
		Parent root = loader.load();
		MainAppController controller = loader.getController();
		controller.setToken(token);
		super.setTitle("UPEM Rent-a-car");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
		super.setOnCloseRequest(v -> {
			try {
				if(!this.loggedOff) {
					this.loggedOff = _MainClient.auth.logoff(token);
				}
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onCloseRequestProperty();
		});
		super.setOnHiding(v -> {
			try {
				if(!this.loggedOff) {
					this.loggedOff = _MainClient.auth.logoff(token);
				}
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (RemoteException e) {
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
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onCloseRequestProperty();
		});
		super.setOnHiding(v -> {
			try {
				_MainClient.auth.logoff(token);
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onHidingProperty();
		});
	}

}
