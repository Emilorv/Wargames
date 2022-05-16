package Wargames.controller;

import Wargames.WargamesApplication;
import Wargames.model.Army;
import Wargames.model.Battle;
import Wargames.model.Terrain;
import Wargames.model.Units.Unit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;


public class battleController {

    @FXML
    private Button startBattleBtn;
    @FXML
    private Label armyName1;

    @FXML
    private Label armyName2;

    @FXML
    private Label attackInfo;

    @FXML
    private StackPane background;

    @FXML
    private Label battleStatus;

    @FXML
    private Label damageDealtText;

    @FXML
    private Button battleSpeedBtn;

    @FXML
    private TableColumn<?, ?> healthCol1;

    @FXML
    private TableColumn<?, ?> healthCol2;

    @FXML
    private TableColumn<?, ?> nameCol1;

    @FXML
    private TableColumn<?, ?> nameCol2;

    @FXML
    private TableView<Unit> tableView1;

    @FXML
    private TableView<Unit> tableView2;

    @FXML
    private Label title;

    @FXML
    private TableColumn<?, ?> typeCol1;

    @FXML
    private TableColumn<?, ?> typeCol2;

    @FXML
    private StackPane units1;

    @FXML
    private StackPane units2;

    @FXML
    private ObservableList<Unit> unitObservableList;
    Army army1;
    Army army2;
    Terrain terrain;

    int battleSpeed = 2000;
    public void recieveData(Army army1, Army army2, Terrain terrain){
        ImageView imageView = new ImageView();
        if(terrain.equals(Terrain.FOREST)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/Forest.png")));
        }else if(terrain.equals(Terrain.PLAINS)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/Plains.png")));
        } else if(terrain.equals(Terrain.HILL)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/Hill.png")));
        }
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(WargamesApplication.stage.getWidth());
        background.getChildren().add(imageView);
        imageView.toBack();


        this.army1=army1;
        this.army2=army2;
        this.terrain=terrain;
        updateTables();
    }

    @FXML
    void battleSpeedBtnHold(MouseEvent event) {
        battleSpeedBtn.addEventHandler(MouseEvent.MOUSE_PRESSED, e->{
            battleSpeedBtn.setText("2X Speed!!");
            battleSpeed = 100;
        });
        battleSpeedBtn.addEventHandler(MouseEvent.MOUSE_RELEASED, e->{
            battleSpeedBtn.setText("Normal Speed");
            battleSpeed = 2000;
        });
    }

    public void startBattleBtnClicked() {
        startBattleBtn.setDisable(true);
        Battle battle = new Battle(army1,army2,terrain);
        new Thread(()->{
            while (army1.hasUnits() && army2.hasUnits()) {
                    battleFight(battle,army1,army2,terrain);
                try {
                    Thread.sleep(battleSpeed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (army2.hasUnits()) {
                   battleFight(battle,army2,army1,terrain);
                    try {
                        Thread.sleep(battleSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            if(army1.hasUnits()) {
                Platform.runLater(()-> battleStatus.setText(army1.getName() + " is winner"));
            } else{
                Platform.runLater(()-> battleStatus.setText(army2.getName() + " is winner" ));
            }
            battleSpeedBtn.setDisable(true);
        }).start();
    }
    public void battleFight(Battle battle, Army attackerArmy, Army defenderArmy, Terrain terrain){
        ArrayList fightInformation = battle.Fight(attackerArmy, defenderArmy, terrain);
        String attackerUnit = (String) fightInformation.get(0);
        String defenderUnit = (String) fightInformation.get(1);
        String damageDone = (String)fightInformation.get(2);
        Platform.runLater(()-> battleStatus.setText(attackerArmy.getName() + " attacks " + defenderArmy.getName()));
        Platform.runLater(()-> attackInfo.setText(attackerUnit +" attacked " + defenderUnit));
        Platform.runLater(()-> damageDealtText.setText("For "+ damageDone + " damage!"));
        Platform.runLater(()-> updateTables());
    }
    public void updateTables(){
        if(army1 != null) {
            armyName1.setText(army1.getName());
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
            nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
            healthCol1.setCellValueFactory(new PropertyValueFactory<>("health"));
            unitObservableList = FXCollections.observableArrayList(army1.getAllUnits());
            tableView1.setItems(unitObservableList);
        }
        if(army2 !=null) {
            armyName2.setText(army2.getName());
            typeCol2.setCellValueFactory(new PropertyValueFactory<>("type"));
            nameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
            healthCol2.setCellValueFactory(new PropertyValueFactory<>("health"));
            unitObservableList = FXCollections.observableArrayList(army2.getAllUnits());
            tableView2.setItems(unitObservableList);
        }
        tableView1.refresh();
        tableView2.refresh();
    }
}
