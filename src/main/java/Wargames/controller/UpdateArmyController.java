package Wargames.controller;

import Wargames.WargamesApplication;
import Wargames.controller.dialogs.Dialogs;
import Wargames.model.Army;
import Wargames.model.Units.Unit;
import Wargames.model.Units.UnitFactory;
import Wargames.model.FileWriting.FileWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Integer.parseInt;

/**
 * Update army controller.
 */
public class UpdateArmyController {
    @FXML
    private Text updateTitle;

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
    private TableColumn<?, ?> typeCol;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private StackPane background;

    private Army army1;
    private Army army2;
    private int armyIndex;

    /**
     * The Units in the army.
     */
    ArrayList<Unit> unitsInArmy = new ArrayList<>();


    /**
     * Receive army information from the frontpage scene.
     *
     * @param armyIndex    the army index
     * @param selectedArmy the selected army
     * @param otherArmy    the other army
     */
    public void receiveArmyInformation(int armyIndex, Army selectedArmy, Army otherArmy ){
        unitsInArmy = new ArrayList<>();
        if(armyIndex==1){
            this.army1 = selectedArmy;
            this.army2 = otherArmy;
        } else{
            this.army2 = selectedArmy;
            this.army1 = otherArmy;
        }
        this.armyIndex=armyIndex;
        updateTitle.setText("Updating Army" + armyIndex);
        if(selectedArmy!=null){
            unitsInArmy.addAll(selectedArmy.getAllUnits());
            nameInput.setText(selectedArmy.getName());
        }
        updateTable();
        addTypesToComboBox();
        loadBackground();
    }

    /**
     * Save army to file button clicked. Uses the fileWriter to save army to file. If the army name already exists as a file, the user will be asked to overwrite it.
     *
     */
    @FXML
    void saveArmyToFileBtnClicked(){
        FileWriter fileWriter = new FileWriter();
        String name = nameInput.getText();
            try {
                Army army = new Army(name, unitsInArmy);
                File savedFile = new File("src/main/resources/Armies/"+name+".csv");
                if(savedFile.isFile()){
                    if(Dialogs.showConfirmationDialog("An army with that name already exists. Do you want to overwrite it?")){
                        try {
                            fileWriter.saveArmyToFile(army);
                            fileUploadedFromText.setText("File Saved to " + savedFile.getPath());
                        }catch (IOException e) {
                            Dialogs.showAlertDialog("Army could not be saved",e);
                        }
                    }
                } else{
                    try {
                        fileWriter.saveArmyToFile(army);
                        fileUploadedFromText.setText("File Saved to " + savedFile.getPath());
                        Dialogs.showConfirmationDialog("The army was saved");
                    }catch (IOException e) {
                        Dialogs.showAlertDialog("Army could not be saved",e);
                    }
                }
            }catch (IllegalArgumentException e){
                Dialogs.showAlertDialog("Army could not be saved", e);
            }

    }

    /**
     * Add button clicked. Uses the inputFields to create units and add them to the table.
     *
     */
    @FXML
    void addBtnClicked() {
        String type = typeComboBox.getValue();
        String name = inputUnitName.getText();
        try {
            int health = parseInt(inputHealth.getText());
            int amount = parseInt(inputAmount.getText());
            try {
                unitsInArmy.addAll(UnitFactory.createUnits(type, name, health, amount));
            }catch (IllegalArgumentException e){
                Dialogs.showAlertDialog("Units could not be added to army", e);
            } catch (NullPointerException e){
                Dialogs.showAlertDialog("Units could not be added", e);
            }
            updateTable();
        }catch (NumberFormatException e){
            Dialogs.showAlertDialog("health and amount must be integers", e);
        }
    }

    /**
     * Upload army from file button clicked. Creates an army based on the information from a CSV-file
     *
     */
    @FXML
    void uploadArmyFromFileBtnClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an army File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV-Files","*.csv")
        );
        fileChooser.setInitialDirectory(new File("src/main/resources/Armies/"));
        try{
            File selectedFile = fileChooser.showOpenDialog(WargamesApplication.stage);
        FileWriter fileWriter = new FileWriter();
        if(armyIndex==1){
            army1= fileWriter.readArmyFromFile(selectedFile);
          receiveArmyInformation(armyIndex,army1,army2);
        }
        else{
            army2= fileWriter.readArmyFromFile(selectedFile);
            receiveArmyInformation(armyIndex,army2,army1);
        }
        fileUploadedFromText.setText("File uploaded from: " + selectedFile.getPath());
        }catch (NullPointerException e){
            Dialogs.showAlertDialog("You did not choose a file");
        } catch (NumberFormatException e){
            Dialogs.showAlertDialog("The file has the wrong format", e);
        }catch (IllegalArgumentException e){
            Dialogs.showAlertDialog(e.getMessage());
        }catch (FileNotFoundException e){
            Dialogs.showAlertDialog("File could not be found", e);
        }
    }

    /**
     * Confirm army button clicked. Converts the added units and the inputted name into an army and changes scene to frontpage.
     *
     */
    @FXML
    void confirmArmyBtnClicked() {
        try {
            if (armyIndex == 1) {
                army1 = new Army(nameInput.getText(), unitsInArmy);
            } else {
                army2 = new Army(nameInput.getText(), unitsInArmy);
            }
            FrontpageController.loadFrontpageScene(army1,army2);
        }catch (IllegalArgumentException e){
            Dialogs.showAlertDialog("Army could not be made",e);
        }
    }

    /**
     * Update table with units.
     */
    void updateTable(){
        typeCol.setCellValueFactory( new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory( new PropertyValueFactory<>("name"));
        healthCol.setCellValueFactory( new PropertyValueFactory<>("health"));
        ObservableList<Unit> unitObservableList = FXCollections.observableArrayList(unitsInArmy);
        armyTableView.setItems(unitObservableList);
    }

    private void addTypesToComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList("InfantryUnit", "RangedUnit", "CavalryUnit", "CommanderUnit");
        typeComboBox.setItems(typeList);
    }

    /**
     * Back button clicked. Changes scene to the frontpage without converting added units and army name.
     * If the army is imported, it will still be transferred to the frontPageScene
     *
     */
    @FXML
    public void backBtnClicked(){
        if(Dialogs.showConfirmationDialog("Go back to main menu? Changes may not have been saved ")){
            FrontpageController.loadFrontpageScene(army1,army2);
        }
    }
    @FXML
    public void clearTableBtnClicked(){
        unitsInArmy = new ArrayList<>();
        fileUploadedFromText.setText("");
        updateTable();
    }


    /**
     * Load background image.
     */
    public void loadBackground(){
        ImageView imageView =  new ImageView(new Image(Objects.requireNonNull(FrontpageController.class.getResourceAsStream("/images/backgroundplanks.png"))));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(WargamesApplication.stage.getWidth());
        background.getChildren().add(imageView);
        imageView.toBack();
    }
}

