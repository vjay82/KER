
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rechner.RechnerController;

public class FIFO_LIFO_HIFO_LOFO_Rechner extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(RechnerController.class.getResource("Rechner.fxml"));

		Scene scene = new Scene(root, 500, 400);

		stage.setTitle("FIFO LIFO HIFO LOFO Rechner");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
