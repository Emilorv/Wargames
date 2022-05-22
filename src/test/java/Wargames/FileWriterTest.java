package Wargames;

import Wargames.model.Army;
import Wargames.model.FileWriting.FileWriter;
import Wargames.model.Units.CommanderUnit;
import Wargames.model.Units.InfantryUnit;
import Wargames.model.Units.RangedUnit;
import Wargames.model.Units.Unit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileWriterTest {
    ArrayList<Unit> units;
    Army army;
    File file;
    String path;
    FileWriter fileWriter;

    public void testData(){
        units = new ArrayList<>();
        units.add(new InfantryUnit("Swordsman",50));
        units.add(new CommanderUnit("Knight",50));
        units.add(new RangedUnit("Archer",60));
    }

    public void saveArmyToFileTestData(){
        testData();
        army = new Army("savedTestArmy", units);
        fileWriter = new FileWriter();
        try {
            fileWriter.saveArmyToFile(army);
        }catch (IOException e){
            e.printStackTrace();
        }
        path = "src/main/resources/Armies/" + army.getName() + ".csv";
        file = new File(path);
    }

    @Test
    @DisplayName("Save army to file")
    public void saveArmyToFile() {
        saveArmyToFileTestData();
        File file = new File(path);
        Assertions.assertTrue(file.isFile());
        file.delete();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    @DisplayName("Read army from file by army name")
    public void readArmyFromFileByName() {
    saveArmyToFileTestData();
        String armyName = army.getName();
        Army armyCopy = null;
       try {
            armyCopy = fileWriter.readArmyFromFileByName(armyName);
       }catch (FileNotFoundException e){
           e.printStackTrace();
       }

        assert armyCopy != null;
        Assertions.assertEquals(3, armyCopy.getAllUnits().size());
            file.delete();
    }

    @Test
    @DisplayName("Read from file that is not CSV")
    public void readArmyFromFileThatIsNotCSV(){
        FileWriter fileWriter = new FileWriter();
        path = "src/test/resources/Armies/notACSVFile.docx";
        file = new File(path);
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->fileWriter.readArmyFromFile(file));
    }

    @Test
    @DisplayName("Read army with illegal name")
    public void readArmyWithIllegalName(){
        FileWriter fileWriter= new FileWriter();
        Assertions.assertThrowsExactly(IllegalArgumentException.class,()->fileWriter.readArmyFromFileByName("8/,."));
    }

    @Test
    @DisplayName("Read army from file that does not exist")
    public void readArmyFromFileThatDoesNotExist(){
        String armyName = "readArmyFromFileThatDoesNotExist";
        FileWriter fileWriter = new FileWriter();
        Assertions.assertThrows(FileNotFoundException.class, () ->fileWriter.readArmyFromFileByName(armyName));
    }

    @Test
    @DisplayName("Overwrite file that already exists")
    public void saveArmyToFileThatAlreadyExists(){
        saveArmyToFileTestData();
        units.remove(1);
        try {
            fileWriter.saveArmyToFile(army);
        }catch (IOException e){
            e.printStackTrace();
        }
        Army checkArmy = new Army("E");
        try {
            checkArmy = fileWriter.readArmyFromFileByName(army.getName());
        }catch(FileNotFoundException e){
            e.printStackTrace();
            }
        Assertions.assertEquals(2,checkArmy.getAllUnits().size());
        file.delete();
    }
    @Test
    @DisplayName("Delete file")
    public void deleteArmyFile() {
        saveArmyToFileTestData();
        try {
            fileWriter.deleteArmyFile(army.getName());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        Assertions.assertFalse(file.isFile());
    }
}