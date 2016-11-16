import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignUpStage extends Stage {
	
	public SignUpStage() throws IOException {
		super();
		Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
		super.setTitle("Sign Up");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
	}
	
	public SignUpStage(StageStyle style) throws IOException {
		super(style);
		Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
		super.setTitle("Sign Up");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
	}

}
