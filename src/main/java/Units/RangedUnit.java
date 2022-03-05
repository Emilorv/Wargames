package Units;

public class RangedUnit extends Unit {
    /**
     * Constructor of RangedUnit
     * @param name Name of RangedUnit
     * @param health Health of RangedUnit
     * @param attack Attack of RangedUnit
     * @param armor Armor of RangedUnit
     */
    public RangedUnit(String name, int health, int attack, int armor, int nAttacks, int nAttacked) {
        super(name, health, attack, armor, nAttacks, nAttacked);
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
     * Resistbonus that decreases with the number of times attacked
     * @return 6, then 4, then 2
     */
    @Override
    public int getResistBonus() {
        if ( getnAttacked()<2){
            return 6;
        } else if (getnAttacked()<3){
            return 4;
        }
        return 2;
    }
}
