package Wargames.model;

import Wargames.model.Units.Unit;

public class Fight {
    Unit attacker;
    Unit defender;
    Terrain terrain;
    int damageDone;

    public Fight(Unit attacker, Unit defender, Terrain terrain){
    this.attacker = attacker;
    this.defender = defender;
    this.terrain = terrain;
    this.damageDone = attacker.attack(defender,terrain);
    }

    public boolean isKilled() {
        if (defender.getHealth() == 0) {
            return true;
        }
        return false;
    }

    public Unit getAttacker() {
        return attacker;
    }

    public Unit getDefender() {
        return defender;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public int getDamageDone() {
        return damageDone;
    }


}
