package Wargames.controller;
import Wargames.WargamesApplication;
import Wargames.model.Army;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FrontpageController implements Initializable {
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

    Army army1;
    Army army2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void battleBtnClicked() {
    }
    @FXML
    void updateArmy1BtnClicked() throws IOException {
        initData(1, army1);
        WargamesApplication.changeScene("/view/UpdateArmyPage.fxml");
    }
    @FXML
    void updateArmy2BtnClicked() throws IOException {
        initData(2,army2);
        WargamesApplication.changeScene("/view/UpdateArmyPage.fxml");


    }

    public void initData(int selectedArmyIndex, Army army){

    }

}

