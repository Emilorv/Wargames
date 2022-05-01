package Wargames;

import Wargames.model.Units.CavalryUnit;
import Wargames.model.Units.CommanderUnit;
import Wargames.model.Units.InfantryUnit;
import Wargames.model.Units.RangedUnit;
import Wargames.model.Army;
import Wargames.model.Battle;

/**
 * Client class for Wargames. Creates 2 armies and simulates a battle
 */
public class Client {
    public Army createArmy1(){
        Army humanArmy = new Army("Human model.Army");
        for (int i = 0; i < 500; i++) {
        humanArmy.addUnit(new InfantryUnit("Footman", 100));
        }
        for (int i = 0; i <200 ; i++) {
            humanArmy.addUnit(new RangedUnit("Archer", 100));
        }
        for (int i = 0; i < 100 ; i++) {
            humanArmy.addUnit(new CavalryUnit("Knight", 100));
        }
        humanArmy.addUnit(new CommanderUnit("Mountain King", 200));
        return humanArmy;
    }
    public Army createArmy2(){
        Army orcishHorde = new Army("Orcish Horde");
        for (int i = 0; i < 500; i++) {
            orcishHorde.addUnit(new InfantryUnit("Grunt", 100));
        }
        for (int i = 0; i <200 ; i++) {
            orcishHorde.addUnit(new RangedUnit("Spearman", 100));
        }
        for (int i = 0; i < 100 ; i++) {
            orcishHorde.addUnit(new CavalryUnit("Raider", 100));
        }
        orcishHorde.addUnit(new CommanderUnit("Gul'dan", 200));
        return orcishHorde;
    }
    public Army simulate(){
        Battle battle = new Battle(createArmy1(), createArmy2());
        return battle.simulate();
    }
    public static void main(String[] args) {
        Client client = new Client();
        System.out.println(client.simulate());
    }
}
