package Wargames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * Wargames application.
 */
public class WargamesApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {

        WargamesApplication.stage = stage;
        Screen screen = Screen.getPrimary();

        //gets screen bounds
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.getIcons().add(new Image(Objects.requireNonNull(WargamesApplication.class.getResourceAsStream("/images/logo.png"))));
        FXMLLoader fxmlLoader = new FXMLLoader(WargamesApplication.class.getResource("/view/Frontpage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),stage.getWidth(),stage.getHeight());
        stage.setTitle("JavaFX Scene");
        stage.setScene(scene);
        stage.show();

    }
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch();
    }
}
