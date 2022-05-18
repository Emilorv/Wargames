package Wargames.controller;

import Wargames.WargamesApplication;
import Wargames.model.Army;
import Wargames.model.Battle;
import Wargames.model.Terrain;
import Wargames.model.Units.Unit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;


public class battleController {

    Army army1;
    Army army2;
    Terrain terrain;
    int battleSpeed = 2000;
    Map<Unit, ImageView> imageMap = new HashMap<>();
    int army1MaxHealth;
    int army2MaxHealth;

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
    private HBox healthBar1;

    @FXML
    private HBox healthBar2;
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

    Rectangle bar1 = new Rectangle();
    Rectangle bar2 = new Rectangle();

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

        this.army1MaxHealth = army1.getArmyHealth();
        this.army2MaxHealth = army2.getArmyHealth();
        updateTables();
        produceUnitImages(army1,infantry1,ranged1,cavalry1,commander1);
        produceUnitImages(army2,infantry2,ranged2,cavalry2,commander2);
        produceArmyHealthbars(healthBar1, bar1);
        produceArmyHealthbars(healthBar2, bar2);
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
            attackInfo.setTextFill(Color.WHITE);
            attackInfo.setText(attackerUnit.getName() +" attacked " + defenderUnit.getName());
            damageDealtText.setText("For "+ damageDone + " damage!");
            if(attackerArmy.equals(army1)){

            }
            if (battle.getFight().isKilled()) {
                ImageView killedUnitPicture = imageMap.get(defenderUnit);
                killedUnitPicture.setImage(null);
                attackInfo.setText(attackerUnit.getName() + " killed " + defenderUnit.getName());
                attackInfo.setTextFill(Color.RED);
                updateArmyHealthbars(bar1, army1,healthBar1);
                updateArmyHealthbars(bar2, army2,healthBar2);
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
        public void produceArmyHealthbars(HBox healthbar, Rectangle bar){
        bar.setFill(Color.RED);
        Platform.runLater(()->{
            bar.setWidth(healthbar.getWidth());
            bar.setHeight(healthbar.getHeight());
        });
        healthbar.getChildren().add(bar);
        }
        public void updateArmyHealthbars(Rectangle bar, Army army, HBox healthBar){
        float percentHealth;
        if(army.equals(army1)){
            percentHealth = (float)army.getArmyHealth() / (float)army1MaxHealth;
            System.out.println("Army Health: " + army.getArmyHealth());
            System.out.println("Max Health: " + army1MaxHealth);
        }
        else {
            percentHealth = (float)army.getArmyHealth()/(float)army2MaxHealth;
        }
            System.out.println(percentHealth);
        Platform.runLater(()->{
            bar.setWidth(healthBar.getWidth()*percentHealth);
        });

        }
    }
