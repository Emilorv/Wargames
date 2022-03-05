package Units;

public class CavalryUnit extends Unit {
    /**
     * Constructor of CavalryUnit
     * @param name name of CavalryUnit
     * @param health health of CavalryUnit
     * @param attack attack of CavalryUnit
     * @param armor armor of CavalryUnit
     * @param nAttacks number of times unit has attacked
     * @param nBlocked number of times unit has blocked
     */
    public CavalryUnit(String name, int health, int attack, int armor, int nAttacks, int nBlocked) {
        super(name, health, attack, armor, nAttacks, nBlocked);
    }
    public CavalryUnit(String name, int health){
        super(name,health, 20,12, 0, 0);
    }

    /**
     * Attackbonus for the first attack (6) then 2
     * @return 2
     */
    @Override
    public int getAttackBonus() {
        if(getNAttacks()<2){
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

