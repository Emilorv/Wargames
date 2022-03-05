package Units;

public class RangedUnit extends Unit {
    /**
     * Constructor of RangedUnit
     * @param name name of RangedUnit
     * @param health health of RangedUnit
     * @param attack attack of RangedUnit
     * @param armor armor of RangedUnit
     * @param nAttacks number of times unit has attacked
     * @param nBlocked number of times unit has blocked
     */
    public RangedUnit(String name, int health, int attack, int armor, int nAttacks, int nBlocked) {
        super(name, health, attack, armor, nAttacks, nBlocked);
    }

    public RangedUnit(String name, int health) {
        super(name, health, 15, 8, 0, 0);
    }

    /**
     * Attackbonus for ranged-range
     * @return 3
     */
    @Override
    public int getAttackBonus() {
        return 3;
    }

    /**
     * Resistbonus that decreases with the number of times blocked
     * @return 6, then 4, then 2
     */
    @Override
    public int getResistBonus() {
        if ( getNBlocked()<2){
            return 6;
        } else if (getNBlocked()<3){
            return 4;
        }
        return 2;
    }
}
