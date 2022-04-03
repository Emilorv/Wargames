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
    public void saveArmyToFile(Army army) {
        java.io.FileWriter fileWriter = null;
        try {
            String path = "src/main/resources/Armies/" + army.getName() + ".csv";
            File file = new File(path);
            fileWriter = new java.io.FileWriter(path);
            fileWriter.write(army.getName() + "\n");
            for (Unit unit : army.getAllUnits()) {
                fileWriter.write(unit.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read army from file army.
     * @param armyName name of army that you want to receive
     * @return the army
     */
    public Army readArmyFromFile(String armyName) {
        if (armyName != null && !armyName.equals("")) {
            if (new File("src/main/resources/Armies/" + armyName + ".csv").isFile()) {
                try {
                    Scanner fileReader = new Scanner(new File("src/main/resources/Armies/" + armyName + ".csv"));
                    ArrayList<Unit> unitsFromFile = new ArrayList<>();
                    armyName = fileReader.nextLine();
                    while (fileReader.hasNextLine()) {
                        String data[] = fileReader.nextLine().split(",");
                        if (data[0].equals("InfantryUnit")) {
                            unitsFromFile.add(new InfantryUnit(data[1], Integer.parseInt(data[2])));
                        } else if (data[0].equals("RangedUnit")) {
                            unitsFromFile.add(new RangedUnit(data[1], Integer.parseInt(data[2])));
                        } else if (data[0].equals("CavalryUnit")) {
                            unitsFromFile.add(new CavalryUnit(data[1], Integer.parseInt(data[2])));
                        } else if (data[0].equals("CommanderUnit")) {
                            unitsFromFile.add(new CommanderUnit(data[1], Integer.parseInt(data[2])));
                        }
                    }
                    fileReader.close();
                    Army army = new Army(armyName, unitsFromFile);
                    return army;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
        return null;
    }

    /**
     * Delete army file.
     * @param armyName name of army that is being deleted
     */
    public void deleteArmyFile(String armyName){
        if (new File("src/main/resources/Armies/" + armyName + ".csv").isFile()) {
            try {
                File file = new File("src/main/resources/Armies/" + armyName + ".csv");
                file.delete();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}