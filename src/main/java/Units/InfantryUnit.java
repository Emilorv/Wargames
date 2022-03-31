package Units;

/**
 * InfantryUnit class. Basic melee unit. Extends Unit class
 */
public class InfantryUnit extends Unit {
    /**
     * Constructor of InfantryUnit
     * @param name name of InfantryUnit
     * @param health health of InfantryUnit
     * @param attack attack of InfantryUnit
     * @param armor armor of InfantryUnit
     * @param nAttacks number of times unit has attacked
     * @param nBlocked number of times unit has blocked
     */
    public InfantryUnit(String name, int health, int attack, int armor, int nAttacks, int nBlocked) {
        super(name, health, attack, armor, nAttacks, nBlocked);
    }
    public InfantryUnit(String name, int health){
       super(name,health, 15,10, 0, 0);
    }

    /**
     * Attackbonus for melee-range
     * @return 2
     */
    @Override
    public int getAttackBonus() {
        return 2;
    }

    /**
     * Resistbonus small resist-bonus
     * @return 1
     */
    @Override
    public int getResistBonus() {
        return 1;
    }
}
