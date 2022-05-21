package Wargames.model.FileWriting;

import Wargames.model.Army;
import Wargames.model.Units.Unit;
import Wargames.model.Units.UnitFactory;

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
     * First line is army name, then 1 unit for each line afterwards
     *
     * @param army the army that is saved to file
     * @throws IOException the io exception
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
     *
     * @param armyName name of army that you want to receive
     * @return the army
     * @throws FileNotFoundException the file not found exception
     */
    public Army readArmyFromFileByName(String armyName) throws FileNotFoundException {
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
     * Read army from file army.
     *
     * @param file the file
     * @return the army
     * @throws FileNotFoundException the file not found exception
     */
    public Army readArmyFromFile(File file) throws FileNotFoundException {
                Scanner fileReader = new Scanner(file);
                ArrayList<Unit> unitsFromFile = new ArrayList<>();
                String armyName = fileReader.nextLine();
                while (fileReader.hasNextLine()) {
                    String data[] = fileReader.nextLine().split(",");
                    unitsFromFile.add(UnitFactory.createUnit(data[0],data[1],Integer.parseInt(data[2])));
                }
                fileReader.close();
                Army army = new Army(armyName, unitsFromFile);
                return army;
            }


    /**
     * Delete army file.
     *
     * @param armyName name of army that is being deleted
     * @throws FileNotFoundException the file not found exception
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
