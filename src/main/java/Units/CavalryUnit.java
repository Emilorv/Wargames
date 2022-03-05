package Units;

public class CavalryUnit extends Unit {
    /**
     * Constructor of CavalryUnit
     * @param name name of CavalryUnit
     * @param health health of CavalryUnit
     * @param attack attack of CavalryUnit
     * @param armor armor of CavalryUnit
     */
    public CavalryUnit(String name, int health, int attack, int armor, int nAttacks, int nAttacked) {
        super(name, health, attack, armor, nAttacks, nAttacked);
    }
    public CavalryUnit(String name, int health){
        super(name,health, 20,12, 0, 0);
    }

    /**
     * Attackbonus for melee-range
     * @return 2
     */
    @Override
    public int getAttackBonus() {
        if(getnAttacks()<2){
            return 6;
        }
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

