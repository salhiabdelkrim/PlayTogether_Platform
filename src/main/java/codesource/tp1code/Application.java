package codesource.tp1code;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("vues/Scene1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Application.class.getResource("style.css").toExternalForm());
        stage.setTitle("PlayTogether - Créer une rencontre");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}