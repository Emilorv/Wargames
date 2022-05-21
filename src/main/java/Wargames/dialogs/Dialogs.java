package Wargames.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Dialogs {
    public static boolean showConfirmationDialog(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wargames");
        alert.setHeaderText(null);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        if(!result.isPresent() || result.get() != ButtonType.OK) {
            return false;
        } else{
            return true;
        }
    }

    /**
     * Displays an alert dialog
     * @param text the text displayed
     */
    public static void showAlertDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Wargames");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Displays an error message
     * @param e the exception that is thrown
     */
    public static void showAlertDialog(String s, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wargames");
        alert.setHeaderText(null);
        alert.setContentText(s + ": "+e.getMessage());
        alert.showAndWait();
    }
}
