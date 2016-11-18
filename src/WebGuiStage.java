import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WebGuiStage extends Stage {
	
	private boolean loggedOff = false;
	
	public WebGuiStage(String token, String login) throws IOException {
		super();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("webGUI.fxml"));
		Parent root = loader.load();
		WebGuiController controller = loader.getController();
		controller.setToken(token);
		controller.setLogin(login);
		super.setTitle("UPEM Buy-a-car");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
		super.setOnCloseRequest(v -> {
			try {
				if(!this.loggedOff) {
					this.loggedOff = _MainWSClient.auth.logoff(token);
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
					this.loggedOff = _MainWSClient.auth.logoff(token);
				}
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onHidingProperty();
		});
	}

	public WebGuiStage(String token, StageStyle style) throws IOException {
		super(style);
		Parent root = FXMLLoader.load(getClass().getResource("webGUI.fxml"));
		super.setTitle("UPEM Buy-a-car");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
		super.setOnCloseRequest(v -> {
			try {
				_MainWSClient.auth.logoff(token);
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onCloseRequestProperty();
		});
		super.setOnHiding(v -> {
			try {
				_MainWSClient.auth.logoff(token);
			} catch (AuthenticationException e) {
				javax.swing.JOptionPane.showMessageDialog(null, e.getMessage()); 
			} catch (RemoteException e) {
				javax.swing.JOptionPane.showMessageDialog(null,"Connection issue...\nPlease restart application.");
			}
			super.onHidingProperty();
		});
	}

}
