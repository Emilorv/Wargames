import Units.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * File writer class.
 * Saves, reads and deletes army from files.
 */
public class FileWriter {
    /**
     * Save army to file.
     *First line is army name, then 1 unit for each line afterwards
     * @param army the army that is saved to file
     */
    public void saveArmyToFile(Army army) throws IOException {
            java.io.FileWriter fileWriter = null;
            String path = "src/main/resources/Armies/" + army.getName() + ".csv";
            File file = new File(path);
            fileWriter = new java.io.FileWriter(path);
            fileWriter.write(army.getName() + "\n");
            for (Unit unit : army.getAllUnits()) {
                fileWriter.write(unit.toString() + "\n");
            }
            fileWriter.close();
        }

    /**
     * Read army from file army.
     * @param armyName name of army that you want to receive
     * @return the army
     */
    public Army readArmyFromFile(String armyName) throws FileNotFoundException {
        if (armyName != null && !armyName.equals("")) {
            if (new File("src/main/resources/Armies/" + armyName + ".csv").isFile()) {
                    Scanner fileReader = new Scanner(new File("src/main/resources/Armies/" + armyName + ".csv"));
                    ArrayList<Unit> unitsFromFile = new ArrayList<>();
                    armyName = fileReader.nextLine();
                    while (fileReader.hasNextLine()) {
                        String data[] = fileReader.nextLine().split(",");
                        unitsFromFile.add(UnitFactory.createUnit(data[0],data[1],Integer.parseInt(data[2])));
                    }
                    fileReader.close();
                    Army army = new Army(armyName, unitsFromFile);
                    return army;
            } else{
                throw new FileNotFoundException("File could not be found");
            }
        } else {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
    }

    /**
     * Delete army file.
     * @param armyName name of army that is being deleted
     */
    public void deleteArmyFile(String armyName) throws FileNotFoundException {
        if (new File("src/main/resources/Armies/" + armyName + ".csv").isFile()) {
                File file = new File("src/main/resources/Armies/" + armyName + ".csv");
                file.delete();
        }
        else{
            throw new FileNotFoundException();
        }
    }
}
