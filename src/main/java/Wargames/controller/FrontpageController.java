package Wargames.controller;
import Wargames.WargamesApplication;
import Wargames.controller.dialogs.Dialogs;
import Wargames.model.Army;
import Wargames.model.Terrain;
import Wargames.model.Units.Unit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * The Frontpage controller.
 */
public class FrontpageController implements Initializable {
    @FXML
    private Label army1Name;
    @FXML
    private Label army2Name;

    @FXML
    private AnchorPane background;

    @FXML
    private ComboBox<Terrain> terrainComboBox;
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
    private TableView<Unit> tableView1;
    @FXML
    private TableView<Unit> tableView2;
    @FXML
    private TableColumn<?, ?> typeCol1;
    @FXML
    private TableColumn<?, ?> typeCol2;
    @FXML
    private TableColumn<?, ?> nameCol1;
    @FXML
    private TableColumn<?, ?> nameCol2;
    @FXML
    private TableColumn<?, ?> healthCol1;
    @FXML
    private TableColumn<?, ?> healthCol2;

    @FXML
    private ObservableList<Unit> unitObservableList;

    /**
     * The Army on the left side, imported from frontpage.
     */
    Army army1;
    /**
     * The Army on the right side, imported from frontpage.
     */
    Army army2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTables();
        loadBackground();
        fillTerrainComboBox();
        fillArmyFields();
    }

    /**
     * Receive army information from other scenes.
     *
     * @param army1 the army on the left side
     * @param army2 the army on the right side
     */
    public void receiveArmyInformation(Army army1, Army army2 ) {
        this.army1 = army1;
        this.army2 = army2;
        fillArmyFields();
        updateTables();
        fillTerrainComboBox();
    }

    /**
     * Battle button clicked. If both armies has units and terrain is given, change the scene to battleScene
     *
     */
    @FXML
    void battleBtnClicked() {
        if(army1 != null && army2 != null) {
            if (army1.hasUnits() && army2.hasUnits()) {
                if (terrainComboBox.getValue() != null) {
                    if(Dialogs.showConfirmationDialog("Do you want to start the game? ")){
                        loadBattleScene(army1, army2, terrainComboBox.getValue());
                    }
                } else {
                    Dialogs.showAlertDialog("Please select a type of terrain");
                }
            } else{
                Dialogs.showAlertDialog("Please make sure that both armies have units");
            }
        }
        else{
            Dialogs.showAlertDialog("Please make / import both armies");
        }
    }

    /**
     * Load battle scene.
     *
     * @param army1   the army 1
     * @param army2   the army 2
     * @param terrain the terrain
     */
    public void loadBattleScene(Army army1, Army army2, Terrain terrain){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/battleView.fxml"));
        try {
            Parent root = loader.load();
            battleController controller = loader.getController();
            controller.receiveData(army1, army2, terrain);
            Stage stage = WargamesApplication.stage;
            stage.getScene().setRoot(root);
        }catch (IOException e){
            Dialogs.showAlertDialog("Could not load scene", e);
        }
    }

    /**
     * Update army1 button clicked. Changes the scene to update the army on the left.
     *
     */
    @FXML
    void updateArmy1BtnClicked() {
        loadUpdateArmyScene(1,army1, army2);
    }

    /**
     * Update army2 button clicked. Changes the scene to update the army on the right
     *
     */
    @FXML
    void updateArmy2BtnClicked() {
        loadUpdateArmyScene(2,army2, army1);
    }

    /**
     * Load update army scene.
     *
     * @param armyIndex    the army index
     * @param selectedArmy the selected army
     * @param otherArmy    the other army
     */
    public void loadUpdateArmyScene(int armyIndex, Army selectedArmy, Army otherArmy){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UpdateArmyPage.fxml"));
        try {
            Parent root = loader.load();
            UpdateArmyController controller = loader.getController();
            controller.receiveArmyInformation(armyIndex, selectedArmy, otherArmy);

            Stage stage = WargamesApplication.stage;
            stage.getScene().setRoot(root);
        }catch (IOException e){
            Dialogs.showAlertDialog("Could not load scene", e);
        }
    }


    /**
     * Fill army fields with data from army1 and army2.
     *
     */
    public void fillArmyFields() {
        fillArmyFields(army1, army1Name, numberOfUnits1, infantryU1, rangedU1, cavalryU1, commanderU1);
        fillArmyFields(army2, army2Name, numberOfUnits2, infantryU2, rangedU2, cavalryU2, commanderU2);
    }

    private void fillArmyFields(Army army1, Label army1Name, Label numberOfUnits1, Label infantryU1, Label rangedU1, Label cavalryU1, Label commanderU1) {
        if(army1 != null){
            army1Name.setText(army1.getName());
            numberOfUnits1.setText(Integer.toString(army1.getAllUnits().size()));
            infantryU1.setText(Integer.toString(army1.getInfantryUnits().size()));
            rangedU1.setText(Integer.toString(army1.getRangedUnits().size()));
            cavalryU1.setText(Integer.toString(army1.getCavalryUnits().size()));
            commanderU1.setText(Integer.toString(army1.getCommanderUnits().size()));
        }
    }

    /**
     * Update tables with units from army1 and army2.
     */
    public void updateTables(){
        updateTables(army1, typeCol1, nameCol1, healthCol1, tableView1);
        updateTables(army2, typeCol2, nameCol2, healthCol2, tableView2);
    }

    private void updateTables(Army army1, TableColumn<?, ?> typeCol1, TableColumn<?, ?> nameCol1, TableColumn<?, ?> healthCol1, TableView<Unit> tableView1) {
        if(army1 != null) {
            typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
            nameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
            healthCol1.setCellValueFactory(new PropertyValueFactory<>("health"));
            unitObservableList = FXCollections.observableArrayList(army1.getAllUnits());
            tableView1.setItems(unitObservableList);
        }
    }

    /**
     * Fill terrain combo box with terrains.
     */
    public void fillTerrainComboBox(){
        ObservableList<Terrain> terrainList = FXCollections.observableArrayList(Terrain.values());
        terrainList.remove(Terrain.DEFAULT);
        terrainComboBox.setItems(terrainList);
    }

    /**
     * Load background image of frontpage.
     */
    public void loadBackground(){
        ImageView imageView =  new ImageView(new Image(Objects.requireNonNull(FrontpageController.class.getResourceAsStream("/images/frontPage.png"))));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(WargamesApplication.stage.getWidth());
        background.getChildren().add(imageView);
        imageView.toBack();
    }

    /**
     * Load frontpage scene from other scenes.
     *
     * @param selectedArmy the selected army
     * @param otherArmy    the other army
     */
    public static void loadFrontpageScene(Army selectedArmy, Army otherArmy) {
        FXMLLoader loader = new FXMLLoader(FrontpageController.class.getResource("/view/Frontpage.fxml"));
        try {
            Parent root = loader.load();
        FrontpageController controller = loader.getController();
        controller.receiveArmyInformation(selectedArmy, otherArmy );

        Stage stage = WargamesApplication.stage;
        stage.getScene().setRoot(root);
        }catch (IOException e){
            Dialogs.showAlertDialog("Could not load scene", e);
        }
    }
}

