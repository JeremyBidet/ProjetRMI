import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class _OldMainClient extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
		primaryStage.setTitle("Rent-A-Car");
		primaryStage.setScene(new Scene(root, 1050, 600));
		primaryStage.setResizable(false);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
