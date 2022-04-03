import Units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileWriterTest {
    ArrayList<Unit> units;
    @BeforeEach
    public void testData(){
        units = new ArrayList<>();
        units.add(new InfantryUnit("Swordman",50));
        units.add(new CommanderUnit("Knight",50));
        units.add(new RangedUnit("Archer",60));
    }

    @Test
    public void saveArmyToFile() {
        Army army = new Army("saveToFileTestArmy", units);
        FileWriter fileWriter = new FileWriter();
        try {
            fileWriter.saveArmyToFile(army);
        }catch (IOException e){
            e.printStackTrace();
        }

        String path = "src/main/resources/Armies/" + army.getName() + ".csv";
        File file = new File(path);
        Assertions.assertTrue(file.isFile());
        file.delete();
    }
    @Test
    public void readArmyFromFile() {
        String armyName = "readFromFileTestArmy";
       FileWriter fileWriter = new FileWriter();
       Army army = new Army("E");
       try {
           army = fileWriter.readArmyFromFile(armyName);
       }catch (FileNotFoundException e){
           e.printStackTrace();
       }
        Assertions.assertEquals("readFromFileTestArmy",army.getName());
    }

    @Test
    public void readArmyFromFileThatDoesNotExsist(){
        String armyName = "readArmyFromFileThatDoesNotExsist";
        FileWriter fileWriter = new FileWriter();
        Assertions.assertThrows(FileNotFoundException.class, () ->fileWriter.readArmyFromFile(armyName));
    }

    @Test
    public void saveArmyToFileThatAlreadyExists(){
        Army army = new Army("saveArmyToFileThatAlreadyExists", units);
        FileWriter fileWriter = new FileWriter();
        try {
            fileWriter.saveArmyToFile(army);
        }catch (IOException e){
            e.printStackTrace();
        }
        units.remove(1);
        try {
            fileWriter.saveArmyToFile(army);
        }catch (IOException e){
            e.printStackTrace();
        }
        Army checkarmy = new Army("E");
        try {
            checkarmy = fileWriter.readArmyFromFile("saveArmyToFileThatAlreadyExists");
        }catch(FileNotFoundException e){
            e.printStackTrace();
            }
        Assertions.assertEquals(2,checkarmy.getAllUnits().size());
    }
    @Test
    public void deleteArmyFile() {
        Army army = new Army("deleteArmyTest");
        army.addUnit(new InfantryUnit("Swordman",50));
        army.addUnit(new CommanderUnit("Knight",50));
        FileWriter fileWriter = new FileWriter();
        try{
        fileWriter.saveArmyToFile(army);
        }catch(IOException e){
            e.printStackTrace();
        }
        String path = "src/main/resources/Armies/" + army.getName() + ".csv";
        File file = new File(path);
        try {
            fileWriter.deleteArmyFile("deleteArmyTest");
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        Assertions.assertFalse(file.isFile());
    }
}