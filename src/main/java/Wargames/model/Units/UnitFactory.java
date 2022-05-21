package Wargames.model.Units;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Unit factory. Create amounts of units of each type
 */
public class UnitFactory {
    /**
     * The Unit types.
     */
    static String[] unitTypes = {"InfantryUnit","RangedUnit","CavalryUnit","CommanderUnit"};

    /**
     * Create unit.
     *
     * @param unitType the unit type
     * @param name     the name
     * @param health   the health
     * @return the unit
     */
    public static Unit createUnit(String unitType, String name, int health) {
        if (unitType.equalsIgnoreCase("InfantryUnit")) {
            return new InfantryUnit(name, health);
        } else if (unitType.equalsIgnoreCase("RangedUnit")) {
            return new RangedUnit(name, health);
        } else if (unitType.equalsIgnoreCase("CavalryUnit")) {
            return new CavalryUnit(name, health);
        } else if (unitType.equalsIgnoreCase("CommanderUnit")) {
            return new CommanderUnit(name, health);
        } else throw new IllegalArgumentException("Unit Type is not available");
    }

    /**
     * Creates an Array of units.
     *
     * @param unitType the unit type cannot be null
     * @param name     the name
     * @param health   the health
     * @param amount   the amount cannot be less than 0 or more than 1000
     * @return the array list
     */
    public static ArrayList<Unit> createUnits(String unitType, String name, int health, int amount) {
        ArrayList<Unit> listOfUnits = new ArrayList<>();
        if(unitType != null) {
            if (Arrays.stream(unitTypes).anyMatch(e -> unitType.equals(e))) {
                if (amount > 0 && amount <1001) {
                    for (int i = 0; i < amount; i++) {
                        listOfUnits.add(createUnit(unitType, name, health));
                    }
                    if (listOfUnits.size() != 0) {
                        return listOfUnits;
                    } else {
                        throw new IllegalArgumentException("Units could not be created");
                    }
                } else {
                    throw new IllegalArgumentException("Amount must be over 0 and 1000 or less");
                }
            } else {
                throw new IllegalArgumentException("Select unit type");
            }
        } else{
            throw new NullPointerException("Select unit type");
        }
    }
}
