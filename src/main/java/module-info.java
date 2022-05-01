module Wargames{
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;

    opens Wargames to javafx.fxml;
    exports Wargames;
    opens Wargames.controller to javafx.fxml;
    opens  Wargames.model to javafx.fxml;
    exports Wargames.model;
    opens Wargames.model.Units to javafx.fxml;
    exports Wargames.model.Units;
    exports Wargames.model.FileWriting;
    opens Wargames.model.FileWriting to javafx.fxml;
}