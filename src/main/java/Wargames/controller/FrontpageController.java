package Wargames.controller;
import Wargames.WargamesApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class FrontpageController {
    @FXML
    private Label title;
    @FXML
    private Label army1Name;
    @FXML
    private Label army2Name;
    @FXML
    private Button battleBtn;
    @FXML
    private Label cavalryU1;
    @FXML
    private Label cavalryU2;
    @FXML
    private Label commanderU1;
    @FXML
    private Label commanderU2;
    @FXML
    private Label infantryU2;
    @FXML
    private Label numberOfUnit2;
    @FXML
    private Label numberOfUnits1;
    @FXML
    private Label rangedU1;
    @FXML
    private Label rangedU2;
    @FXML
    private Button updateArmy1Btn;
    @FXML
    private Button updateArmy2Btn;

    @FXML
    void battleBtnClicked() {
    }
    @FXML
    void updateArmy1BtnClicked() throws IOException {
        WargamesApplication.changeScene("/view/UpdateArmyPage.fxml");
    }
    @FXML
    void updateArmy2BtnClicked() throws IOException {
        WargamesApplication.changeScene("/view/UpdateArmyPage.fxml");
    }
}

