package Wargames.dialogs;

import javafx.scene.control.Alert;

public class Dialogs {
    /**
     * Displays an information dialog
     * @param text the text displayed
     */
    public static void showInformationDialog(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wargames");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
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
    public static void showAlertDialog(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Wargames");
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
