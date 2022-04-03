import Units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
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
        fileWriter.saveArmyToFile(army);

        String path = "src/main/resources/Armies/" + army.getName() + ".csv";
        File file = new File(path);
        Assertions.assertTrue(file.isFile());
        file.delete();
    }

    @Test
    public void saveArmyToFileThatAlreadyExists(){
        Army army = new Army("saveToFileTestArmy", units);
        FileWriter fileWriter = new FileWriter();
        fileWriter.saveArmyToFile(army);
        fileWriter.saveArmyToFile(army);


    }
    @Test
    public void readArmyFromFile() {
        String armyName = "readFromFileTestArmy";
       FileWriter fileWriter = new FileWriter();
       Army army = fileWriter.readArmyFromFile(armyName);
        Assertions.assertEquals("readFromFileTestArmy",army.getName());
    }

    @Test
    public void deleteArmyFile() {
        Army army = new Army("deleteArmyTest");
        army.addUnit(new InfantryUnit("Swordman",50));
        army.addUnit(new CommanderUnit("Knight",50));
        FileWriter fileWriter = new FileWriter();
        fileWriter.saveArmyToFile(army);

        String path = "src/main/resources/Armies/" + army.getName() + ".csv";
        File file = new File(path);
        fileWriter.deleteArmyFile("deleteArmyTest");
        Assertions.assertFalse(file.isFile());
    }
}