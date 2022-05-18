package Wargames.model;

import Wargames.model.Units.Unit;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * model.Battle class. Simulates a battle between 2 armies.
 */
public class Battle {
    private Terrain terrain;
    private final Army armyOne;
    private final Army armyTwo;
    private Fight fight;

    /**
     * The model.Battle Constructor
     * @param armyOne the model.Army that strikes first in a simulated battle
     * @param armyTwo the second army to strike in a simulated battle
     */
    public Battle(Army armyOne, Army armyTwo) {
        this.armyOne = armyOne;
        this.armyTwo = armyTwo;
        this.terrain = Terrain.DEFAULT;
    }

    /**
     * Instantiates a new model.Battle with terrain.
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
     * Simulates a battle between the two armies. Initiates Fight()s as long as both armies has model.Units left
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
     * @param attackerArmy the army that strikes
     * @param defenderArmy the army that defends
     */
    public void Fight(Army attackerArmy, Army defenderArmy, Terrain terrain){
        Unit attackingUnit = attackerArmy.getRandom();
        Unit defendingUnit = defenderArmy.getRandom();
        fight = new Fight(attackingUnit, defendingUnit, terrain);
        if(fight.isKilled()){
            defenderArmy.remove(defendingUnit);
        }
    }

    public Fight getFight() {
        return fight;
    }

    @Override
    public String toString() {
        return "Battle{" + "armyOne=" + armyOne + ", armyTwo=" + armyTwo + '}';
    }
}
