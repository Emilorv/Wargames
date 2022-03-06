import Units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;

public class BattleTest {
    /**
     * Simulates a fight between 2 armies where army1 is the attacker, and army 2 is defender.
     * The Units in each army both have health adjusted so that they will be removed if attacked
     * The fight is successful if the unit in army2 is removed
     */
    @Test
    public void testFight() {
        Army army1 = new Army("FirstArmy");
        Army army2 = new Army("SecondArmy");
        army1.addUnit( new InfantryUnit("Swordsman", 6));
        army2.addUnit( new InfantryUnit("Swordsman", 6));
        Battle battle = new Battle(army1,army2);
        battle.Fight(army1, army2);
        Assertions.assertFalse(army2.hasUnits());
    }

    /**
     * Simulates a battle between 2 armies with 200 units each.
     * The test is successful if the test returns a winner that is either army1 or army2
     */
    @Test
    public void testSimulate() {
        Army army1 = new Army("FirstArmy");
        Army army2 = new Army("SecondArmy");
        for(int i =0; i<100; i++){
            army1.addUnit( new InfantryUnit("Swordsman", 30));
            army1.addUnit(new CommanderUnit("Commander", 20));
        }
        for(int i =0; i<100; i++){
            army2.addUnit( new InfantryUnit("Swordsman", 30));
            army2.addUnit(new CommanderUnit("Commander", 20));
        }
        Battle battle = new Battle(army1,army2);
        Army Winner = battle.simulate();
        Assertions.assertTrue(Winner==army1 || Winner ==army2);


    }
}