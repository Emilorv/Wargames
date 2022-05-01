package Wargames.model.Units;

/**
 * Commander Unit. Special kind of CavalryUnit with better basic health and armor stats. Extends CavalryUnit
 */
public class CommanderUnit extends CavalryUnit{
        /**
         * Constructor of CommanderUnit
         * @param name name of CommanderUnit
         * @param health health of CommanderUnit
         * @param attack attack of CommanderUnit
         * @param armor armor of CommanderUnit
         * @param nAttacks number of times unit has attacked
         * @param nBlocked number of times unit has blocked
         */
        public CommanderUnit(String name, int health, int attack, int armor, int nAttacks, int nBlocked) {
            super(name, health, attack, armor, nAttacks, nBlocked);
        }
        public CommanderUnit(String name, int health){
            super(name,health, 25,15, 0, 0);
        }
    }



