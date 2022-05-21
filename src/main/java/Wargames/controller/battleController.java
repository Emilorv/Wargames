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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * The type Battle controller.
 */
public class battleController {

    /**
     * The Army on the left side.
     */
    Army army1;
    /**
     * The Army on the right side.
     */
    Army army2;
    /**
     * Copy of the army on the left side. Used for restarting the battle.
     */
    Army army1Saved;
    /**
     * Copy of the army on the right side. Used for restarting the battle.
     */
    Army army2Saved;
    /**
     * The Terrain. Used for calculating bonuses and background-image
     */
    Terrain terrain;
    /**
     * The Battle speed. Adjusts the rates at which the fights happen.
     */
    int battleSpeed = 2000;
    /**
     * The Image map. Used for connecting A unit to their image, so it can be removed when the unit dies.
     */
    Map<Unit, ImageView> imageMap = new HashMap<>();
    /**
     * The Army 1 max health. Used for calculating the width of the left-army healthBar
     */
    int army1MaxHealth;
    /**
     * The Army 2 max health. Used for calculating the width of the right-army healthBar
     */
    int army2MaxHealth;
    /**
     * The Number of times clicked the speed button. Used for alternating between normal and 20x speed
     */
    int numberOfTimesClickedTheSpeedButton = 0;

    @FXML
    private Button startBattleBtn;

    @FXML
    private Button battleSpeedBtn;

    @FXML
    private Button restartBtn;

    @FXML
    private Label armyName1;

    @FXML
    private Label armyName2;

    @FXML
    private Label attackInfo;

    @FXML
    private Label battleStatus;

    @FXML
    private Label damageDealtText;

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
    private TableColumn<?, ?> typeCol1;

    @FXML
    private TableColumn<?, ?> typeCol2;

    @FXML
    private HBox healthBar1;

    @FXML
    private HBox healthBar2;

    @FXML
    private StackPane background;

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

    /**
     * The healthBar-bar for the army on the left side.
     */
    Rectangle bar1 = new Rectangle();
    /**
     * The healthBar-bar for the army on the right side.
     */
    Rectangle bar2 = new Rectangle();

    /**
     * Receive data from other scenes, and initializes data.
     *
     * @param army1   the army 1
     * @param army2   the army 2
     * @param terrain the terrain
     */
    public void receiveData(Army army1, Army army2, Terrain terrain){
        this.army1=army1;
        this.army2=army2;
        this.terrain=terrain;
        this.army1MaxHealth = army1.getArmyHealth();
        this.army2MaxHealth = army2.getArmyHealth();
        startBattleBtn.setDisable(false);
        restartBtn.setDisable(true);
        army1Saved = army1.copy();
        army2Saved = army2.copy();
        setsBackgroundImage();
        updateTables();
        produceUnitImages(army1,infantry1,ranged1,cavalry1,commander1);
        produceUnitImages(army2,infantry2,ranged2,cavalry2,commander2);
        produceArmyHealthBars(healthBar1, bar1);
        produceArmyHealthBars(healthBar2, bar2);
    }

    /**
     * Start battle button clicked. Disable buttons, and starts a new Thread containing the while-loop which initiates battleFight()s until one of the armies has no units left.
     */
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

    /**
     * Battle fight. Uses Platform.runLater() to simulate the fight running in real-time.
     * Updates GUI-elements, so it displays which army attacked, damage done, and checks if the unit is killed
     * The tables and healthBars of each army is also updated.
     * If the unit is killed during the fight, their imageView is set to "null"
     *
     * @param battle       the battle
     * @param attackerArmy the attacker army
     * @param defenderArmy the defender army
     * @param terrain      the terrain
     */
    public void battleFight(Battle battle, Army attackerArmy, Army defenderArmy, Terrain terrain){
        Platform.runLater(()-> {
        battle.Fight(attackerArmy,defenderArmy,terrain);
        Unit attackerUnit = battle.getFight().getAttacker();
        Unit defenderUnit = battle.getFight().getDefender();
        int damageDone = battle.getFight().getDamageDone();


            battleStatus.setText(attackerArmy.getName() + " attacks " + defenderArmy.getName());
            attackInfo.setTextFill(Color.WHITE);
            attackInfo.setText(attackerUnit.getName() +" attacked " + defenderUnit.getName());
            damageDealtText.setText("For "+ damageDone + " damage!");
            updateArmyHealthBars(bar1, army1,healthBar1);
            updateArmyHealthBars(bar2, army2,healthBar2);
            updateTables();
            if (battle.getFight().isKilled()) {
                ImageView killedUnitPicture = imageMap.get(defenderUnit);
                killedUnitPicture.setImage(null);
                attackInfo.setText(attackerUnit.getName() + " killed " + defenderUnit.getName());
                attackInfo.setTextFill(Color.RED);
            }

        });
    }

    /**
     * Battle speed button clicked. Alternates the speed in which each attack happens. Alternates between 1 second and 0.05 seconds
     */
    @FXML
    void battleSpeedBtnClicked() {
        numberOfTimesClickedTheSpeedButton +=1;
        if(numberOfTimesClickedTheSpeedButton%2 ==1){
            battleSpeedBtn.setText("20X Speed!!");
            battleSpeed = 50;
        } else{
            battleSpeedBtn.setText("Normal Speed");
            battleSpeed = 1000;
        }
    }

    /**
     * Restart button clicked. Removes GUI-Texts, healthBars and clears the imageMap, and initiates a new battle
     */
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
        receiveData(army1Saved,army2Saved,terrain);
    }

    /**
     * Back button clicked. Returns to the frontpage.
     * @throws IOException the io exception
     */
    public void backBtnClicked() throws IOException {
        FrontpageController.loadFrontpageScene(army1Saved,army2Saved);
    }

    /**
     * Sets background image based on terrain.
     */
    public void setsBackgroundImage(){
        ImageView imageView = new ImageView();
        if(terrain.equals(Terrain.FOREST)){
            imageView =  new ImageView(new Image(Objects.requireNonNull(battleController.class.getResourceAsStream("/images/terrainBackgrounds/Forest.png"))));
        }else if(terrain.equals(Terrain.PLAINS)){
            imageView =  new ImageView(new Image(Objects.requireNonNull(battleController.class.getResourceAsStream("/images/terrainBackgrounds/Plains.png"))));
        } else if(terrain.equals(Terrain.HILL)){
            imageView =  new ImageView(new Image(Objects.requireNonNull(battleController.class.getResourceAsStream("/images/terrainBackgrounds/Hill.png"))));
        }
        imageView.setFitWidth(WargamesApplication.stage.getWidth());
        imageView.setFitHeight(WargamesApplication.stage.getHeight());
        background.getChildren().add(imageView);
        imageView.toBack();
    }

    /**
     * Initiates updateTables method for both armies.
     */
    public void updateTables(){
        updateTables(army1, armyName1, typeCol1, nameCol1, healthCol1, tableView1);
        updateTables(army2, armyName2, typeCol2, nameCol2, healthCol2, tableView2);
        tableView1.refresh();
        tableView2.refresh();
    }

    /**
     * Update tables to include Units of each army. ConcurrentModificationException happens occasionally while battleSpeed is high, but it is not critical
     *
     * @param army      the army
     * @param armyName  the army name
     * @param typeCol   the type col
     * @param nameCol   the name col
     * @param healthCol the health col
     * @param tableView the table view
     */
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

    /**
     * Produce small unit images displayed at the bottom of the scene.
     *
     * @param army      the army
     * @param infantry  the field where infantryUnits go
     * @param ranged    the field where rangedUnits go
     * @param cavalry   the field where cavalryUnits go
     * @param commander the field where commanderUnits go
     */
    public void produceUnitImages(Army army,Pane infantry, Pane ranged, Pane cavalry, Pane commander) {
        for (Unit unit : army.getAllUnits()) {
            ImageView imageView = new ImageView(unit.getUnitImage());
            switch (unit.getClass().getSimpleName()) {
                case "InfantryUnit" -> infantry.getChildren().add(imageView);
                case "RangedUnit" -> ranged.getChildren().add(imageView);
                case "CavalryUnit" -> cavalry.getChildren().add(imageView);
                case "CommanderUnit" -> {
                    imageView.setEffect(new Glow(100));
                    imageView.setScaleX(1.2);
                    imageView.setScaleY(1.2);
                    commander.getChildren().add(imageView);
                }
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

    /**
     * Produce army healthBars.
     *
     * @param healthBar the healthBar container
     * @param bar       the healthBar bar
     */
    public void produceArmyHealthBars(HBox healthBar, Rectangle bar){
        Platform.runLater(()->{
            bar.setFill(Color.RED);
            healthBar.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderStroke.MEDIUM)));
            double healthBarBorderStrokes = healthBar.getBorder().getStrokes().get(0).getWidths().getRight()*2;
            bar.setWidth(healthBar.getWidth()-healthBarBorderStrokes);
            bar.setHeight(healthBar.getHeight()-healthBarBorderStrokes);
            healthBar.getChildren().add(bar);
        });
        }

    /**
     * Update army healthBars. Bases the bar width on how much health the army has. ConcurrentModificationException happens occasionally while battleSpeed is high, but it is not critical
     *
     * @param bar       the healthBar bar
     * @param army      the army
     * @param healthBar the healthBar container
     */
    public void updateArmyHealthBars(Rectangle bar, Army army, HBox healthBar){
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
            System.out.println(e.getClass().getSimpleName() + " from updating healthBar for army: "+ army.getName());
        }

        }
    }
