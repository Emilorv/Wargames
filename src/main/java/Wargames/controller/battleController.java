package Wargames.controller;

import Wargames.WargamesApplication;
import Wargames.model.Army;
import Wargames.model.Battle;
import Wargames.model.Terrain;
import Wargames.model.Units.Unit;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;


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

        this.army1=army1;
        this.army2=army2;
        this.terrain=terrain;
        updateTables();
    }

    public void startBattleBtnClicked() {
        startBattleBtn.setDisable(true);
        Battle battle = new Battle(army1,army2,terrain);
        new Thread(()->{
            while (army1.hasUnits() && army2.hasUnits()) {
                battle.Fight(army1, army2, terrain);
                Platform.runLater(()-> battleStatus.setText(army1.getName() + " attacks " + army2.getName()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2");
                if (army2.hasUnits()) {
                    battle.Fight(army2, army1, terrain);
                    Platform.runLater(()-> battleStatus.setText(army2.getName() + " attacks " + army1.getName()));
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("1");
                }

            }
            if(army1.hasUnits()) {
                Platform.runLater(()-> battleStatus.setText(army1.getName() + " is winner"));
            } else{
                Platform.runLater(()-> battleStatus.setText(army2.getName() + " is winner" ));
            }
        }).start();
    }
    public void simulateBattle() {
        startBattleBtn.setDisable(true);
        Battle battle = new Battle(army1,army2,terrain);
        while (army1.hasUnits() && army2.hasUnits()) {
            System.out.println(1);
            battle.Fight(army1, army2, terrain);
            battleStatus.setText(army1.getName() + " attacks " + army2.getName());
            if (army2.hasUnits()) {
                System.out.println(2);
                battle.Fight(army2, army1, terrain);
                battleStatus.setText(army2.getName() + " attacks " + army1.getName());
            }
        }
        if(army1.hasUnits()) {
            battleStatus.setText(army1.getName() + "is winner");
        } else{
            battleStatus.setText(army2.getName() + "is winner");
        }
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
    }
}
