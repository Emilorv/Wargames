public abstract class Unit {
    private String name;
    private int health;
    private int attack;
    private int armor;

    /**
     * Unit constructor
     * @param name name of unit
     * @param health health of unit
     * @param attack attack of unit
     * @param armor armor of unit
     */
    public Unit(String name, int health, int attack, int armor) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.armor = armor;
    }

    /**
     *Attack-method. Changes health of opponent
     * @param opponent opponent
     */
    public void attack(Unit opponent){
        int newHealth = opponent.getHealth() - this.getAttack()+this.getAttackBonus() + opponent.armor+opponent.getResistBonus();
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
     * Returns health of Unit
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns attack of Unit
     * @return attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns armor of Unit
     * @return armor
     */
    public int getArmor() {
        return armor;
    }

    /**
     * modifies health of Unit
     * @param health new health value
     */
    public void setHealth(int health) {
        this.health = health;
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
