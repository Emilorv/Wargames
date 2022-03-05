package Units;

public class CommanderUnit extends CavalryUnit{
        /**
         * Constructor of CavalryUnit
         * @param name name of CavalryUnit
         * @param health health of CavalryUnit
         * @param attack attack of CavalryUnit
         * @param armor armor of CavalryUnit
         */
        public CommanderUnit(String name, int health, int attack, int armor, int nAttacks, int nAttacked) {
            super(name, health, attack, armor, nAttacked, nAttacked);
        }
        public CommanderUnit(String name, int health){
            super(name,health, 25,15, 0, 0);
        }
    }



