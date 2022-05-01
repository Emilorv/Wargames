package Wargames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WargamesApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        WargamesApplication.stage = stage;
        stage.getIcons().add(new Image(WargamesApplication.class.getResourceAsStream("/images/logo.png")));
        FXMLLoader fxmlLoader = new FXMLLoader(WargamesApplication.class.getResource("/view/Frontpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1200,600);
        stage.setTitle("JavaFX Scene");
        stage.setScene(scene);
        stage.show();

    }
    /**
     * Change scene.
     *
     * Change to given fxml view
     * @param fxml the fxml
     * @throws IOException the io exception
     */
    public static void changeScene(String fxml) throws IOException {
        Parent parent = FXMLLoader.load(WargamesApplication.class.getResource(fxml));
        Stage stage = WargamesApplication.stage;
        stage.getScene().setRoot(parent);
    }

    public static void main(String[] args) {
        launch();
    }
}
