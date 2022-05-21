package Wargames.controller;

import Wargames.WargamesApplication;
import Wargames.dialogs.Dialogs;
import Wargames.model.Army;
import Wargames.model.Units.Unit;
import Wargames.model.Units.UnitFactory;
import Wargames.model.FileWriting.FileWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class UpdateArmyController {
    @FXML
    private Text updateTitle;

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
    @FXML
    private StackPane background;

    private Army army1;
    private Army army2;
    private int armyIndex;

    private ObservableList<Unit> unitObservableList;
    ArrayList<Unit> unitsInArmy = new ArrayList<>();


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

    @FXML
    void saveArmyToFileBtnClicked() {
        FileWriter fileWriter = new FileWriter();
        String name = nameInput.getText();
            try {
                Army army = new Army(name, unitsInArmy);
                File savedFile = new File("src/main/resources/Armies/"+name+".csv");
                if(savedFile.isFile()){
                    if(Dialogs.showConfirmationDialog("An army with that name already exists. Do you want to overwrite it?")){
                        try {
                            fileWriter.saveArmyToFile(army);
                        }catch (IOException e) {
                            Dialogs.showAlertDialog("Army could not be saved",e);
                        }
                        fileUploadedFromText.setText("File Saved to " + savedFile.getPath());
                    }
                } else{
                    try {
                        fileWriter.saveArmyToFile(army);
                    }catch (IOException e) {
                        Dialogs.showAlertDialog("Army could not be saved",e);
                    }
                    fileUploadedFromText.setText("File Saved to " + savedFile.getPath());
                }
            }catch (IllegalArgumentException e){
                Dialogs.showAlertDialog("Army could not be saved", e);
            }

    }
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
            Dialogs.showAlertDialog("The health and amount of units must be an integer",e);
        }
    }
    @FXML
    void uploadArmyFromFileBtnClicked() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an army File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV-Files","*.csv")
        );
        fileChooser.setInitialDirectory(new File("src/main/resources/Armies/"));
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
    }

    @FXML
    void confirmArmyBtnClicked() throws IOException {
        try {
            if (armyIndex == 1) {
                army1 = new Army(nameInput.getText(), unitsInArmy);
            } else {
                army2 = new Army(nameInput.getText(), unitsInArmy);
            }
            loadFrontpageScene(army1,army2);
        }catch (IllegalArgumentException e){
            Dialogs.showAlertDialog("Army could not be made",e);
        }
    }

    public void updateTable(){
        typeCol.setCellValueFactory( new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory( new PropertyValueFactory<>("name"));
        healthCol.setCellValueFactory( new PropertyValueFactory<>("health"));
        unitObservableList = FXCollections.observableArrayList(unitsInArmy);
        armyTableView.setItems(unitObservableList);
    }

    private void addTypesToComboBox() {
        ObservableList<String> typeList = FXCollections.observableArrayList("InfantryUnit", "RangedUnit", "CavalryUnit", "CommanderUnit");
        typeComboBox.setItems(typeList);
    }
    @FXML
    void backBtnClicked() throws IOException {
        if(Dialogs.showConfirmationDialog("Go back to main menu? Changes may not have been saved ")){
            loadFrontpageScene(army1,army2);
        }
    }

    public void loadFrontpageScene(Army selectedArmy, Army otherArmy) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Frontpage.fxml"));
        Parent root = loader.load();
        FrontpageController controller = loader.getController();
        controller.receiveArmyInformation(selectedArmy, otherArmy );

        Stage stage = WargamesApplication.stage;
        stage.getScene().setRoot(root);
    }

    public void loadBackground(){
        ImageView imageView =  new ImageView(new Image(FrontpageController.class.getResourceAsStream("/images/backgroundplanks.png")));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(WargamesApplication.stage.getWidth());
        background.getChildren().add(imageView);
        imageView.toBack();
    }
}

