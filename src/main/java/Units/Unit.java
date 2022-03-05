package Units;
public abstract class Unit {
    private final String name;
    private int health;
    private final int attack;
    private final int armor;
    protected int nAttacks;
    protected int nAttacked;

    /**
     * Units.Unit constructor
     * @param name name of unit
     * @param health health of unit
     * @param attack attack of unit
     * @param armor armor of unit
     */
    public Unit(String name, int health, int attack, int armor, int nAttacks, int nAttacked) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
        this.nAttacked = nAttacked;
        this.nAttacks = nAttacks;
    }

    /**
     *Attack-method. Changes health of opponent
     * @param opponent opponent
     */
    public void attack(Unit opponent){
        setnAttacks(nAttacked++);
        opponent.setnAttacked(opponent.getnAttacked()+1);
        int newHealth = opponent.getHealth() - this.getAttack()-this.getAttackBonus() + opponent.armor+opponent.getResistBonus();
        opponent.setHealth(newHealth);
    }
    /**
     *Returns name of unit
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns health of Units.Unit
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns attack of Units.Unit
     * @return attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns armor of Units.Unit
     * @return armor
     */
    public int getArmor() {
        return armor;
    }

    /**
     * modifies health of Units.Unit
     * @param health new health value
     */
    public void setHealth(int health) {
        this.health = health;
    }

    public int getnAttacks() {
        return nAttacks;
    }

    public int getnAttacked() {
        return nAttacked;
    }

    public void setnAttacks(int nAttacks) {
        this.nAttacks = nAttacks;
    }

    public void setnAttacked(int nAttacked) {
        this.nAttacked = nAttacked;
    }

    /**
     * ToString
     * @return String
     */
    @Override
    public String toString() {
        return "{name=" + name + ", health=" + health + ", attack=" + attack + ", armor=" + armor +"}";
    }

    /**
     *Abstract attack-bonus method
     */
    public abstract int getAttackBonus();

    /**
     *Abstract resist-bonus method
     */
    public abstract int getResistBonus();
}
