package Wargames.model;

import Wargames.model.Units.Unit;

/**
 * Battle class. Simulates a battle between 2 armies.
 */
public class Battle {
    private final Terrain terrain;
    private final Army armyOne;
    private final Army armyTwo;
    /**
     * A battle has many fights, this param contains the latest one
     */
    private Fight fight;

    /**
     * The Battle Constructor
     *
     * @param armyOne the Army that strikes first in a simulated battle
     * @param armyTwo the second army to strike in a simulated battle
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.terrain = Terrain.DEFAULT;
    }

    /**
     * Instantiates a new Battle with terrain.
     *
     * @param armyOne the army one
     * @param armyTwo the army two
     * @param terrain the terrain
     */
    public Battle(Army armyOne, Army armyTwo, Terrain terrain) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.terrain = terrain;
    }


    /**
     * Simulates a battle between the two armies. Initiates Fight()s as long as both armies has Units left
     *
     * @return army (the army that has units left when the battle is over, aka Winner)
     */
    public Army simulate() {
        while (armyOne.hasUnits() && armyTwo.hasUnits()) {
            Fight(armyOne, armyTwo, terrain);
            if (armyTwo.hasUnits()) {
            Fight(armyTwo, armyOne, terrain);
            }
        }
        if(armyOne.hasUnits())
        return armyOne;
        else return armyTwo;
    }

    /**
     * Method where one random unit from the first army strikes a random unit from the other
     * If the defending unit has health equal to zero, it is removed from the army
     *
     * @param attackerArmy the army that strikes
     * @param defenderArmy the army that defends
     * @param terrain      the terrain
     */
    public void Fight(Army attackerArmy, Army defenderArmy, Terrain terrain){
        Unit attackingUnit = attackerArmy.getRandom();
        Unit defendingUnit = defenderArmy.getRandom();
        fight = new Fight(attackingUnit, defendingUnit, terrain);
        if(fight.isKilled()){
            defenderArmy.remove(defendingUnit);
        }
    }

    /**
     * Gets the latest fight.
     *
     * @return the fight
     */
    public Fight getFight() {
        return fight;
    }

    @Override
    public String toString() {
        return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
    }
}
