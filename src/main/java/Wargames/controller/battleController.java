package Wargames.controller;

import Wargames.WargamesApplication;
import Wargames.dialogs.Dialogs;
import Wargames.model.Army;
import Wargames.model.Battle;
import Wargames.model.Terrain;
import Wargames.model.Units.Unit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;


public class battleController {

    Army army1;
    Army army2;
    Army army1Saved;
    Army army2Saved;
    Terrain terrain;
    int battleSpeed = 2000;
    Map<Unit, ImageView> imageMap = new HashMap<>();
    int army1MaxHealth;
    int army2MaxHealth;
    int numberOfTimesClickedTheSpeedButton = 0;

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
    private Button restartBtn;
    @FXML
    private Button backBtn;
    @FXML
    private ObservableList<Unit> unitObservableList;

    Rectangle bar1 = new Rectangle();
    Rectangle bar2 = new Rectangle();

    public void recieveData(Army army1, Army army2, Terrain terrain){
        ImageView imageView = new ImageView();
        if(terrain.equals(Terrain.FOREST)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/terrainBackgrounds/Forest.png")));
        }else if(terrain.equals(Terrain.PLAINS)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/terrainBackgrounds/Plains.png")));
        } else if(terrain.equals(Terrain.HILL)){
            imageView =  new ImageView(new Image(battleController.class.getResourceAsStream("/images/terrainBackgrounds/Hill.png")));
        }
        imageView.setFitWidth(WargamesApplication.stage.getWidth());
        imageView.setFitHeight(WargamesApplication.stage.getHeight());
        background.getChildren().add(imageView);
        imageView.toBack();

        startBattleBtn.setDisable(false);
        restartBtn.setDisable(true);

        this.army1=army1;
        this.army2=army2;
        army1Saved = army1.copy();
        army2Saved = army2.copy();
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
    void battleSpeedBtnClicked() {
        numberOfTimesClickedTheSpeedButton +=1;
        if(numberOfTimesClickedTheSpeedButton%2 ==1){
            battleSpeedBtn.setText("20X Speed!!");
            battleSpeed = 50;
        } else{
            battleSpeedBtn.setText("Normal Speed");
            battleSpeed = 2000;
        }
    }

    public void startBattleBtnClicked() {
        if(Dialogs.showConfirmationDialog("Are you ready to start?")){
            startBattleBtn.setDisable(true);
            battleSpeedBtn.setDisable(false);
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
                restartBtn.setDisable(false);
            }).start();
        }
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
            updateArmyHealthbars(bar1, army1,healthBar1);
            updateArmyHealthbars(bar2, army2,healthBar2);
            updateTables();
            if (battle.getFight().isKilled()) {
                ImageView killedUnitPicture = imageMap.get(defenderUnit);
                killedUnitPicture.setImage(null);
                attackInfo.setText(attackerUnit.getName() + " killed " + defenderUnit.getName());
                attackInfo.setTextFill(Color.RED);
            }

        });
    }
    public void updateTables(){
        updateTables(army1, armyName1, typeCol1, nameCol1, healthCol1, tableView1);
        updateTables(army2, armyName2, typeCol2, nameCol2, healthCol2, tableView2);
        tableView1.refresh();
        tableView2.refresh();
    }

    public void updateTables(Army army, Label armyName, TableColumn<?, ?> typeCol, TableColumn<?, ?> nameCol, TableColumn<?, ?> healthCol, TableView<Unit> tableView) {
        if(army != null) {
            try {
                armyName.setText(army.getName());
                typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
                nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                healthCol.setCellValueFactory(new PropertyValueFactory<>("health"));
                unitObservableList = FXCollections.observableArrayList(army.getAllUnits());
                tableView.setItems(unitObservableList);
            }catch (ConcurrentModificationException e){
                System.out.println(e.getClass().getSimpleName() + " from updating table for army: "+ army.getName());
            }
        }
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
        Platform.runLater(()->{
            bar.setFill(Color.RED);
            healthbar.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.MEDIUM)));
            double healthBarBorderStrokes = healthbar.getBorder().getStrokes().get(0).getWidths().getRight()*2;
            bar.setWidth(healthbar.getWidth()-healthBarBorderStrokes);
            bar.setHeight(healthbar.getHeight()-healthBarBorderStrokes);
            healthbar.getChildren().add(bar);
        });
        }
        public void updateArmyHealthbars(Rectangle bar, Army army, HBox healthBar){
        float percentHealth;
        try {
            if (army.equals(army1)) {
                percentHealth = (float) army.getArmyHealth() / (float) army1MaxHealth;
            } else {
                percentHealth = (float) army.getArmyHealth() / (float) army2MaxHealth;
            }
            Platform.runLater(() -> {
                double healthBarBorderStrokes = healthBar.getBorder().getStrokes().get(0).getWidths().getRight() * 2;
                bar.setWidth((healthBar.getWidth() - healthBarBorderStrokes) * percentHealth);
            });
        }catch (ConcurrentModificationException e){
            System.out.println(e.getClass().getSimpleName() + " from updating healthbar for army: "+ army.getName());
        }

        }
        public void restartBtnClicked(){
        attackInfo.setText("");
        battleStatus.setText("");
        damageDealtText.setText("");
        healthBar1.getChildren().remove(bar1);
        healthBar2.getChildren().remove(bar2);
            for (Unit unit:army1.getAllUnits()) {
                imageMap.get(unit).setImage(null);
            }
            for (Unit unit:army2.getAllUnits()) {
                imageMap.get(unit).setImage(null);
            }
        imageMap.clear();
        recieveData(army1Saved,army2Saved,terrain);
        }
        public void backBtnClicked() throws IOException {
            loadFrontpageScene(army1Saved,army2Saved);
        }
        public void loadFrontpageScene(Army selectedArmy, Army otherArmy) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Frontpage.fxml"));
        Parent root = loader.load();
        FrontpageController controller = loader.getController();
        controller.receiveArmyInformation(selectedArmy, otherArmy );

        Stage stage = WargamesApplication.stage;
        stage.getScene().setRoot(root);
    }
    }
