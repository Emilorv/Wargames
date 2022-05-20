package Wargames.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Wargames.model.Units.*;


/**
 * model.Army class. Assembles units into teams that will be used in battles against each other.
 */
public class Army {
    private int armyHealth;
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
                    this.units = units;
        } else {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
    }

    /**
     * Instantiates a new model.Army.
     *
     * @param name the name
     */
    public Army(String name) {
        if (name != null && !name.equals("")) {
                    this.name = name;
                    this.units = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
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

    public int getArmyHealth() {
        armyHealth = 0;
        for (Unit unit: getAllUnits()) {
            armyHealth += unit.getHealth();
        }
        return armyHealth;
    }
    public Army copy(){
        String armyNameCopy = name;
        ArrayList<Unit> copyUnits = new ArrayList();
        for (Unit unit:getInfantryUnits()){
            copyUnits.add(new InfantryUnit(unit.getName(),unit.getHealth()));
        }
        for (Unit unit:getRangedUnits()) {
            copyUnits.add(new RangedUnit(unit.getName(),unit.getHealth()));
        }
        for (Unit unit:getCavalryUnits()) {
            copyUnits.add(new CavalryUnit(unit.getName(),unit.getHealth()));
        }
        for (Unit unit:getCommanderUnits()) {
            copyUnits.add(new CommanderUnit(unit.getName(),unit.getHealth()));
        }
        Army armyCopy = new Army(armyNameCopy, copyUnits);
        return armyCopy;
    }

    @Override
    public String toString() {
        return "model.Army{" +
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
