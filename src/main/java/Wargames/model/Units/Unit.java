package Wargames.model.Units;

import Wargames.model.Terrain;
import javafx.scene.image.Image;

/**
 * Abstract Unit class. Model for units that will be part of Armies.
 */
public abstract class Unit {
    private final String name;
    private final String type;
    private int health;
    private final int attack;
    private final int armor;
    /**
     * The number of times attacked.
     */
    protected int nAttacks;
    /**
     * The number of times blocked.
     */
    protected int nBlocked;

    /**
     * Unit constructor
     *
     * @param name     name of unit
     * @param health   health of unit
     * @param attack   attack of unit
     * @param armor    armor of unit
     * @param nAttacks number of times attacking
     * @param nBlocked number of times blocking an attack
     * @throws IllegalArgumentException the illegal argument exception
     */
    public Unit(String name, int health, int attack, int armor, int nAttacks, int nBlocked) throws IllegalArgumentException{
        if(name !=null && !name.equals("")) {
            this.name = name;
        }else {
            throw new IllegalArgumentException("Name cannot be empty or null");
        }
        if (health>0){
            this.health = health;
        } else{
            throw new IllegalArgumentException("Health cannot be 0 or less");
        }
        this.attack = attack;
        this.armor = armor;
        this.nBlocked = nBlocked;
        this.nAttacks = nAttacks;
        this.type = this.getClass().getSimpleName();
    }

    /**
     * Attack-method. Changes health of opponent
     * if opponent armor is greater than attackers attack, nothing happens.
     *
     * @param opponent opponent
     */
    public void attack(Unit opponent){
        setNAttacks(getNAttacks()+1);
        opponent.setNBlocked(opponent.getNBlocked()+1);
        if(this.getAttack()+this.getAttackBonus(Terrain.DEFAULT)> opponent.getArmor()+opponent.getResistBonus(Terrain.DEFAULT)){

            int newHealth = opponent.getHealth() - this.getAttack()-this.getAttackBonus(Terrain.DEFAULT) + opponent.armor+opponent.getResistBonus(Terrain.DEFAULT);
            if( newHealth<0){
                newHealth = 0;
            }
            opponent.setHealth(newHealth);
        }
    }

    /**
     * Attack with terrain param. Used to calculate Attack bonus and resist bonus if the attack happens in terrain.
     *
     * @param opponent the opponent
     * @param terrain  the terrain
     * @return damageDone to opponent
     */
    public int attack(Unit opponent, Terrain terrain){
        setNAttacks(getNAttacks()+1);
        opponent.setNBlocked(opponent.getNBlocked()+1);
        if(this.getAttack()+this.getAttackBonus(terrain)> opponent.getArmor()+opponent.getResistBonus(terrain)){

            int newHealth = opponent.getHealth() - this.getAttack()-this.getAttackBonus(terrain) + opponent.armor+opponent.getResistBonus(terrain);
            if( newHealth<0){
                newHealth = 0;
            }
            int opponentHealth = opponent.getHealth();
            opponent.setHealth(newHealth);
            return opponentHealth-newHealth;
        } else{
            return 0;
        }
    }

    /**
     * Returns name of unit
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns health of unit
     *
     * @return health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns attack of unit
     *
     * @return attack
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Returns armor of unit
     *
     * @return armor armor
     */
    public int getArmor() {
        return armor;
    }

    /**
     * modifies health of unit. If new health value is less than 0, set it to 0.
     *
     * @param health new health value
     */
    public void setHealth(int health) {
        this.health = Math.max(health, 0);
    }

    /**
     * Gets type of unit.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Returns number of times the unit has attacked
     *
     * @return nAttacks number of times attacked
     */
    public int getNAttacks() {
        return nAttacks;
    }

    /**
     * Modifies number of times attacked
     *
     * @param nAttacks number of times attacked
     */
    public void setNAttacks(int nAttacks) {
        this.nAttacks = nAttacks;
    }

    /**
     * Returns number of times the unit has been blocked an attack
     *
     * @return nBlocked number of times blocked
     */
    public int getNBlocked() {
        return nBlocked;
    }

    /**
     * Modifies number of times blocked
     *
     * @param nBlocked number of times blocked
     */
    public void setNBlocked(int nBlocked) {
        this.nBlocked = nBlocked;
    }

    /**
     * ToString
     * @return String
     */
    @Override
    public String toString() {
        return getClass().getSimpleName()+","+getName()+","+getHealth();
    }

    /**
     * Abstract attack-bonus method
     *
     * @param terrain the terrain
     * @return the attack bonus
     */
    public abstract int getAttackBonus(Terrain terrain);

    /**
     * Abstract resist-bonus method
     *
     * @param terrain the terrain
     * @return the resist bonus
     */
    public abstract int getResistBonus(Terrain terrain);

    /**
     * Gets unit image.
     *
     * @return the unit image
     */
    public abstract Image getUnitImage();
}
