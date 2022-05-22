package Wargames.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Wargames.model.Units.*;


/**
 * Army class. Assembles units into teams that will be used in battles against each other.
 */
public class Army {
    private String name;
    private ArrayList<Unit> units;

    /**
     * Constructor of ArmyClass
     *
     * @param name  name of army
     * @param units array with units
     */
    public Army(String name, ArrayList<Unit> units) throws IllegalArgumentException {
        if (name != null && !name.equals("")) {
            if(name.split("[,.:;^=~#@*+%{}<>/\\[\\]|\"]", 2).length<2){
                this.name = name;
                this.units = units;
            } else{
                throw  new IllegalArgumentException("Army name cannot contain iIllegal Characters ");
            }
        } else {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
    }

    /**
     * Instantiates a new Army.
     *
     * @param name the name
     */
    public Army(String name) throws IllegalArgumentException {
        if (name != null && !name.equals("")) {
            if(name.split("[,.:;^=~#@*+%{}<>/\\[\\]|\"]", 2).length<2) {
                this.name = name;
                this.units = new ArrayList<>();
            }else{
                throw new IllegalArgumentException();
            }
        } else {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
    }

    /**
     * Get name of army
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds Unit to army
     *
     * @param unit unit to be added
     */
    public void addUnit(Unit unit) {
        units.add(unit);
    }

    /**
     * Adds all units in an input array to army
     *
     * @param inputUnits an array of units that are to be added to army
     */
    public void addAll(ArrayList<Unit> inputUnits) {
        for (Unit unit : inputUnits) {
            addUnit(unit);
        }
    }

    /**
     * Removes unit from army
     *
     * @param unit unit to be removed from army
     */
    public void remove(Unit unit) {
        units.remove(unit);
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
     * @return unit
     */
    public Unit getRandom() {
        double randomNumber = Math.random() * units.size();
        int unitIndex = (int) Math.floor(randomNumber);
        return units.get(unitIndex);
    }

    /**
     * Gets the collective health of all units in army.
     * Used to make the healthBar of each army
     * @return the army health
     */
    public int getArmyHealth() {
        int armyHealth = 0;
        for (Unit unit: getAllUnits()) {
            armyHealth += unit.getHealth();
        }
        return armyHealth;
    }

    /**
     * Method that creates a copy of the army.
     * Used for restarting the battle without having to import or make the army again.
     * @return the copied army
     */
    public Army copy(){
        if(name!=null) {
            String armyNameCopy = name;
            ArrayList<Unit> copyUnits = new ArrayList<>();
            for (Unit unit : getInfantryUnits()) {
                copyUnits.add(new InfantryUnit(unit.getName(), unit.getHealth()));
            }
            for (Unit unit : getRangedUnits()) {
                copyUnits.add(new RangedUnit(unit.getName(), unit.getHealth()));
            }
            for (Unit unit : getCavalryUnits()) {
                copyUnits.add(new CavalryUnit(unit.getName(), unit.getHealth()));
            }
            for (Unit unit : getCommanderUnits()) {
                copyUnits.add(new CommanderUnit(unit.getName(), unit.getHealth()));
            }
            return new Army(armyNameCopy, copyUnits);
        }else throw new NullPointerException();
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
