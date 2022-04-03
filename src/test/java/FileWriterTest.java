import Units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class FileWriterTest {

    @Test
    public void testSaveArmyToFile() {
        Army army = new Army("saveToFileTestArmy");
        army.addUnit(new InfantryUnit("Swordman",50));
        army.addUnit(new CommanderUnit("Knight",50));
        FileWriter fileWriter = new FileWriter();
        fileWriter.saveArmyToFile(army);

        String path = "src/main/resources/Armies/" + army.getName() + ".csv";
        File file = new File(path);
        Assertions.assertTrue(file.isFile());
        file.delete();

    }
    @Test
    public void testReadArmyFromFile() {
        String armyName = "readFromFileTestArmy";
       FileWriter fileWriter = new FileWriter();
       Army army = fileWriter.readArmyFromFile(armyName);
        Assertions.assertEquals("readFromFileTestArmy",army.getName());
    }

    @Test
    public void testDeleteArmyFile() {
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