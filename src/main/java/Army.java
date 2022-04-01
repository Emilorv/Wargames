import Units.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import Units.InfantryUnit;


/**
 * Army class. Assembles units into teams that will be used in battles against each other.
 */
public class Army {
    private String name;
    private ArrayList<Unit> units = new ArrayList<>();

    /**
     * Constructor of ArmyClass
     *
     * @param name  name of army
     * @param units array with units
     */
    public Army(String name, ArrayList<Unit> units) {
        if (name != null && !name.equals("")) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
        if (units.size() != 0) {
            this.units = units;
        } else {
            throw new IllegalArgumentException("List with units cannot be empty");
        }
    }

    /**
     * Instantiates a new Army.
     *
     * @param name the name
     */
    public Army(String name) {
        this.name = name;
    }

    /**
     * Get name of army
     *
     * @return name name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds Unit to army
     *
     * @param unit unit to be added
     * @return true if succesfull
     */
    public boolean addUnit(Unit unit) {
        units.add(unit);
        return true;
    }

    /**
     * Adds all units in an inputarray to army
     *
     * @param inputUnits an array of units that are to be added to army
     * @return true if succesfull
     */
    public boolean addAll(ArrayList<Unit> inputUnits) {
        for (Unit var : inputUnits) {
            addUnit(var);
        }
        return true;
    }

    /**
     * Removes unit from army
     *
     * @param unit unit to be removed from army
     * @return true if succesfull
     */
    public boolean remove(Unit unit) {
        units.remove(unit);
        return true;
    }

    /**
     * Checks if army has units
     *
     * @return false if army is empty, true if it is not
     */
    public boolean hasUnits() {
        return units.size() != 0;
    }

    /**
     * Returns an array of units in the army
     *
     * @return units array list
     */
    public ArrayList<Unit> getAllUnits() {
        return units;
    }

    /**
     * Get infantry units in army.
     *
     * @return the list
     */
    public List<Unit> getInfantryUnits() {
        return getAllUnits().stream().filter(c -> c instanceof InfantryUnit).toList();
    }

    /**
     * Get cavalry units in army.
     *
     * @return the list
     */
    public List<Unit> getCavalryUnits() {
        return getAllUnits().stream().filter(c -> c.getClass().equals(CavalryUnit.class)).toList();
    }

    /**
     * Get ranged units in army.
     *
     * @return the list
     */
    public List<Unit> getRangedUnits() {
        return getAllUnits().stream().filter(c -> c instanceof RangedUnit).toList();
    }

    /**
     * Get commander units in army.
     *
     * @return the list
     */
    public List<Unit> getCommanderUnits() {
        return getAllUnits().stream().filter(c -> c instanceof CommanderUnit).toList();
    }

    /**
     * Returns a random unit in army
     *
     * @return unit unit
     */
    public Unit getRandom() {
        double randomNumber = Math.random() * units.size();
        int unitIndex = (int) Math.floor(randomNumber);
        return units.get(unitIndex);
    }

    public void saveArmyToFile() {
        FileWriter fileWriter = null;
        try {
            String path = "src/main/resources/Armies/" + getName() + ".csv";
            File army = new File(path);
            fileWriter = new FileWriter(path);
            fileWriter.write(getName() + "\n");
            for (Unit unit : getAllUnits()) {
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

    public void readArmyFromFile(String filepath) {
        try {
            File file = new File(filepath);
            Scanner myReader = new Scanner(file);
            ArrayList<Unit> units = new ArrayList<>();
            String armyName = myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data[] = myReader.nextLine().split(",");
                if (data[0].equals("InfantryUnit")) {
                    units.add(new InfantryUnit(data[1], Integer.parseInt(data[2])));
                } else if (data[0].equals("RangedUnit")) {
                    units.add(new RangedUnit(data[1], Integer.parseInt(data[2])));
                } else if (data[0].equals("CavalryUnit")) {
                    units.add(new CavalryUnit(data[1], Integer.parseInt(data[2])));
                } else if (data[0].equals("CommanderUnit")) {
                    units.add(new CommanderUnit(data[1], Integer.parseInt(data[2])));
                }
            }
            myReader.close();
            setName(armyName);
            addAll(units);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Army{" +
                "name='" + name + '\'' +
                ", units=" + units +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Army army = (Army) object;
        return Objects.equals(name, army.name) && Objects.equals(units, army.units);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, units);
    }
}
