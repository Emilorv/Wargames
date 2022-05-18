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
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    private Pane infantry1;
    @FXML
    private  Pane ranged1;
    @FXML
    private  Pane cavalry1;
    @FXML
    private  Pane commander1;
    @FXML
    private Pane infantry2;
    @FXML
    private  Pane ranged2;
    @FXML
    private  Pane cavalry2;
    @FXML
    private  Pane commander2;

    @FXML
    private ObservableList<Unit> unitObservableList;
    Army army1;
    Army army2;
    Terrain terrain;
    int battleSpeed = 2000;
    Map<Unit, ImageView> imageMap = new HashMap<>();

    public void recieveData(Army army1, Army army2, Terrain terrain){
        ImageView imageView = new ImageView();
        if(terrain.equals(Terrain.FOREST)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/Forest.png")));
        }else if(terrain.equals(Terrain.PLAINS)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/Plains.png")));
        } else if(terrain.equals(Terrain.HILL)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/Hill.png")));
        }
        imageView.setFitWidth(WargamesApplication.stage.getWidth());
        imageView.setFitHeight(WargamesApplication.stage.getHeight());
        background.getChildren().add(imageView);
        imageView.toBack();


        this.army1=army1;
        this.army2=army2;
        this.terrain=terrain;
        updateTables();
        produceUnitImages(army1,infantry1,ranged1,cavalry1,commander1);
        produceUnitImages(army2,infantry2,ranged2,cavalry2,commander2);
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
        battle.Fight(attackerArmy,defenderArmy,terrain);
        Unit attackerUnit = battle.getFight().getAttacker();
        Unit defenderUnit = battle.getFight().getDefender();
        int damageDone = battle.getFight().getDamageDone();

        Platform.runLater(()-> {
            battleStatus.setText(attackerArmy.getName() + " attacks " + defenderArmy.getName());
            attackInfo.setText(attackerUnit.getName() +" attacked " + defenderUnit.getName());
            damageDealtText.setText("For "+ damageDone + " damage!");
            if (battle.getFight().isKilled()) {

                ImageView killedUnitPicture = imageMap.get(defenderUnit);
                killedUnitPicture.setImage(null);
            }
            updateTables();
        });
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

    public void produceUnitImages(Army army,Pane infantry, Pane ranged, Pane cavalry, Pane commander) {
        for (Unit unit : army.getAllUnits()) {
            ImageView imageView = new ImageView(unit.getUnitImage());
            if (unit.getClass().getSimpleName().equals("InfantryUnit")) {
                infantry.getChildren().add(imageView);
            }
            else if (unit.getClass().getSimpleName().equals("RangedUnit")) {
                ranged.getChildren().add(imageView);
            }
            else if (unit.getClass().getSimpleName().equals("CavalryUnit")) {
                cavalry.getChildren().add(imageView);
            }
            else if (unit.getClass().getSimpleName().equals("CommanderUnit")) {
                imageView.setEffect(new Glow(100));
                imageView.setScaleX(1.2);
                imageView.setScaleY(1.2);
                commander.getChildren().add(imageView);
            }
           Platform.runLater(() -> {
                imageView.setY(Math.random()*infantry.getHeight());
                if(army.equals(army1)) {
                    imageView.setX(Math.random() * (infantry.getWidth()-32));
                    imageView.setY(Math.random() * infantry.getHeight());
                }
                if (army.equals(army2)) {
                imageView.setX(Math.random()*(infantry2.getWidth()-32));
                imageView.setScaleX(-1);
                }
                });
            imageMap.put(unit, imageView);
        }
        }
    }
