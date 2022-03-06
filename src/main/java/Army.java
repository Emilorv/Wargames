import Units.Unit;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Army {
    private String name;
    private ArrayList<Unit> units = new ArrayList<>();
    /**
     * Constructor of ArmyClass
     * @param name name of army
     * @param units array with units
     */
    public Army(String name, ArrayList<Unit> units) {
        this.name = name;
        this.units = units;
    }
    public Army(String name) {
        this.name = name;
    }

    /**
     * Get name of army
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds Unit to army
     * @param unit unit to be added
     * @return true if succesfull
     */
    public boolean addUnit(Unit unit){
        units.add(unit);
        return true;
    }

    /**
     * Adds all units in an inputarray to army
     * @param inputUnits an array of units that are to be added to army
     * @return true if succesfull
     */
    public  boolean addAll(ArrayList<Unit> inputUnits){
        for (Unit var : inputUnits) {
            addUnit(var);
        }
        return true;
    }

    /**
     * Removes unit from army
     * @param unit unit to be removed from army
     * @return true if succesfull
     */
    public boolean remove(Unit unit){
        units.remove(unit);
        return true;
    }

    /**
     * Checks if army has units
     * @return false if army is empty, true if it is not
     */
    public boolean hasUnits(){
        return units.size() != 0;
    }

    /**
     * Returns an array of units in the army
     * @return units
     */
    public ArrayList<Unit> getAllUnits(){
        return units;
    }

    /**
     * Returns a random unit in army
     * @return unit
     */
    public Unit getRandom(){
        double randomNumber = Math.random()*units.size();
        int unitIndex = (int) Math.floor(randomNumber);
        return units.get(unitIndex);
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
