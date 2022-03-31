import Units.Unit;

/**
 * Battle class. Simulates a battle between 2 armies.
 */
public class Battle {
    private final Army armyOne;
    private final Army armyTwo;

    /**
     * The Battle Constructor
     * @param armyOne the Army that strikes first in a simulated battle
     * @param armyTwo the second army to strike in a simulated battle
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
    }

    /**
     * Simulates a battle between the two armies. Initiates Fight()s as long as both armies has Units left
     * @return army (the army that has units left when the battle is over, aka Winner)
     */
    public Army simulate() {
        while (armyOne.hasUnits() && armyTwo.hasUnits()) {
            Fight(armyOne, armyTwo);
            if (armyTwo.hasUnits()) {
            Fight(armyTwo, armyOne);
            }
        }
        if(armyOne.hasUnits())
        return armyOne;
        else return armyTwo;
    }

    /**
     * Method where one random unit from the first army strikes a random unit from the other
     * If the defending unit has health equal to zero, it is removed from the army
     * @param attackerArmy the army that strikes
     * @param defenderArmy the army that defends
     */
    public void Fight(Army attackerArmy, Army defenderArmy){
        Unit Contestant1 = attackerArmy.getRandom();
        Unit Contestant2 = defenderArmy.getRandom();
        Contestant1.attack(Contestant2);
        if(Contestant2.getHealth()==0){
            defenderArmy.remove(Contestant2);
        }
    }
    @Override
    public String toString() {
        return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
    }
}
