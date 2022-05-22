package Wargames.model;

import Wargames.model.Units.Unit;

/**
 * The Fight between two units in the battle.
 */
public class Fight {
    /**
     * The Attacker unit.
     */
    Unit attacker;
    /**
     * The Defender unit.
     */
    Unit defender;
    /**
     * The Terrain.
     */
    Terrain terrain;
    /**
     * The Damage done to the defender.
     */
    int damageDone;

    /**
     * Instantiates a new Fight.
     *
     * @param attacker the attacker
     * @param defender the defender
     * @param terrain  the terrain
     */
    public Fight(Unit attacker, Unit defender, Terrain terrain){
    this.attacker = attacker;
    this.defender = defender;
    this.terrain = terrain;
    this.damageDone = attacker.attack(defender,terrain);
    }

    /**
     * Is killed boolean. If the defending unit has 0 health, it returns true
     *
     * @return true if 0 health, false if not.
     */
    public boolean isKilled() {
        return defender.getHealth() == 0;
    }

    /**
     * Gets attacker.
     *
     * @return the attacker
     */
    public Unit getAttacker() {
        return attacker;
    }

    /**
     * Gets defender.
     *
     * @return the defender
     */
    public Unit getDefender() {
        return defender;
    }

    /**
     * Gets terrain.
     *
     * @return the terrain
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Gets damage done to defending unit.
     *
     * @return the damage done
     */
    public int getDamageDone() {
        return damageDone;
    }


}
