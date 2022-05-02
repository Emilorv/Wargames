package Wargames.controller;
import Wargames.WargamesApplication;
import Wargames.model.Army;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.stage.Stage;

import java.io.IOException;


public class FrontpageController{
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
    private Label infantryU1;
    @FXML
    private Label infantryU2;
    @FXML
    private Label numberOfUnits2;
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

    private Army army1;
    private Army army2;

    public void receiveArmyInformation(Army army1, Army army2 ){
        this.army1 = army1;
        this.army2 = army2;
        try {
            fillArmyFields();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadNextScene(int armyIndex, Army selectedArmy, Army otherArmy) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateArmyPage.fxml"));
        Parent root = loader.load();
        UpdateArmyController controller = loader.getController();
        controller.receiveArmyInformation(armyIndex,selectedArmy, otherArmy );

        Stage stage = WargamesApplication.stage;
        stage.getScene().setRoot(root);
    }

    @FXML
    void battleBtnClicked() {
    }

    @FXML
    void updateArmy1BtnClicked() throws IOException {
        loadNextScene(1,army1, army2);
    }
    @FXML
    void updateArmy2BtnClicked() throws IOException {
        loadNextScene(2,army2, army1);
    }


    public void fillArmyFields() throws IOException {
        if(army1 != null){
            army1Name.setText(army1.getName());
            numberOfUnits1.setText(Integer.toString(army1.getAllUnits().size()));
            infantryU1.setText(Integer.toString(army1.getInfantryUnits().size()));
            rangedU1.setText(Integer.toString(army1.getRangedUnits().size()));
            cavalryU1.setText(Integer.toString(army1.getCavalryUnits().size()));
            commanderU1.setText(Integer.toString(army1.getCommanderUnits().size()));
        }
        if(army2 != null){
            army2Name.setText(army2.getName());
            numberOfUnits2.setText(Integer.toString(army2.getAllUnits().size()));
            infantryU2.setText(Integer.toString(army2.getInfantryUnits().size()));
            rangedU2.setText(Integer.toString(army2.getRangedUnits().size()));
            cavalryU2.setText(Integer.toString(army2.getCavalryUnits().size()));
            commanderU2.setText(Integer.toString(army2.getCommanderUnits().size()));
        }
    }
}

