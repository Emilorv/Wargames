package Wargames.model.Units;

import java.util.ArrayList;

/**
 * Unit factory. Creates units of each type
 */
public class UnitFactory {

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
        } else throw new IllegalArgumentException("Unit could not be created");
    }

    /**
     * Create list of amount units.
     *
     * @param unitType the unit type
     * @param name     the name
     * @param health   the health
     * @param amount   the amount
     * @return the array list
     */
    public static ArrayList<Unit> createUnits(String unitType, String name, int health, int amount){
       ArrayList<Unit> listOfUnits = new ArrayList<>();
       if(amount>0) {
           for (int i = 0; i < amount; i++) {
               listOfUnits.add(createUnit(unitType, name, health));
           }
           if (listOfUnits.size() != 0) {
               return listOfUnits;
           } else {
               throw new IllegalArgumentException("model.Units could not be created");
           }
       } else {
           throw new IllegalArgumentException("Amount must be over 0");
       }
    }
}
