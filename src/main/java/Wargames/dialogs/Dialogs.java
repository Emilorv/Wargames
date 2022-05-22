package Wargames.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The Dialogs class. Used
 */
public class Dialogs {
    /**
     * Show confirmation dialog boolean.
     *
     * @param text the text
     * @return the boolean
     */
    public static boolean showConfirmationDialog(String text){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Wargames");
        alert.setHeaderText(null);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Displays an alert dialog
     *
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
     *
     * @param text the String that explains the errorMessage
     * @param e the exception that is thrown
     */
    public static void showAlertDialog(String text, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wargames");
        alert.setHeaderText(null);
        alert.setContentText(text + ": "+e.getMessage());
        alert.showAndWait();
    }
}
