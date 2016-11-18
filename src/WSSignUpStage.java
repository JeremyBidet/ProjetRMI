import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WSSignUpStage extends Stage {
	
	public WSSignUpStage() throws IOException {
		super();
		Parent root = FXMLLoader.load(getClass().getResource("WSSignUp.fxml"));
		super.setTitle("Sign Up");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
	}
	
	public WSSignUpStage(StageStyle style) throws IOException {
		super(style);
		Parent root = FXMLLoader.load(getClass().getResource("WSSignUp.fxml"));
		super.setTitle("Sign Up");
		super.setScene(new Scene(root));
		super.setResizable(false);
		super.centerOnScreen();
	}

}
