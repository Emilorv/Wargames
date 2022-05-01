package Wargames.controller;

import Wargames.WargamesApplication;
import Wargames.model.Army;
import Wargames.model.Units.InfantryUnit;
import Wargames.model.Units.Unit;
import Wargames.model.Units.UnitFactory;
import Wargames.model.FileWriting.FileWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

public class UpdateArmyController implements Initializable {
    @FXML
    private Button addBtn;
    @FXML
    private TableView<Unit> armyTableView;

    @FXML
    private Label fileUploadedFromText;

    @FXML
    private TableColumn<?, ?> healthCol;

    @FXML
    private TextField inputAmount;

    @FXML
    private TextField inputHealth;

    @FXML
    private TextField inputUnitName;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TextField nameInput;

    @FXML
    private Button saveArmyToFileBtn;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Button uploadArmyFromFileBtn;

    @FXML
    private Button confirmArmyBtn;

    @FXML
    private Button backBtn;

    private ObservableList<Unit> unitObservableList;
    ArrayList<Unit> unitsInArmy = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeCol.setCellValueFactory( new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory( new PropertyValueFactory<>("name"));
        healthCol.setCellValueFactory( new PropertyValueFactory<>("health"));
        unitObservableList = FXCollections.observableArrayList(unitsInArmy);
        armyTableView.setItems(unitObservableList);
        addTypesToComboBox();

    }
    @FXML
    void saveArmyToFileBtnClicked() {
        FileWriter fileWriter = new FileWriter();
        String name = nameInput.getText();
        try{
            Army army = new Army(name, unitsInArmy);
            fileWriter.saveArmyToFile(army);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void addBtnClicked() {
        String type = typeComboBox.getValue();
        String name = inputUnitName.getText();
        int health = parseInt(inputHealth.getText());
        int amount = parseInt(inputAmount.getText());
        if( type!= null){
            if( !name.equals("") || !name.equals(null)){
                if(health>0){
                    if(amount>0){
                        try {
                            unitsInArmy.addAll(UnitFactory.createUnits(type, name, health, amount));
                        }catch (IllegalArgumentException e){
                            e.getMessage();
                        }
                        updateTable();
                    }
                }

            }
        }
    }
    @FXML
    void uploadArmyFromFileBtnClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an army File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV-Files","*.csv")
        );

        File selectedFile = fileChooser.showOpenDialog(WargamesApplication.stage);
        fileUploadedFromText.setText("File uploaded from: " + selectedFile.getPath());
    }

    @FXML
    void confirmArmyBtnClicked(){

    }

    public void updateTable(){
        unitObservableList = FXCollections.observableArrayList(unitsInArmy);
        armyTableView.setItems(unitObservableList);
    }

    private void addTypesToComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList("InfantryUnit", "RangedUnit", "CavalryUnit", "CommanderUnit");
        typeComboBox.setItems(typeList);
    }
    @FXML
    void backBtnClicked() throws IOException {
        WargamesApplication.changeScene("/view/Frontpage.fxml");
    }
}

