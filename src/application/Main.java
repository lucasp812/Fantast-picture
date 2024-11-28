package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent rootFXML = FXMLLoader.load(getClass().getResource("vue.fxml"));
            Scene sceneFXML = new Scene(rootFXML);

            primaryStage.setTitle("Fantast'Picture");

            primaryStage.setScene(sceneFXML);

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
