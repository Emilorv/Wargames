import Units.CavalryUnit;
import Units.CommanderUnit;
import Units.InfantryUnit;
import Units.RangedUnit;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


public class UnitTest {
    /**
     * The attack applies a damage of 15(attack) + 2(attackBonus) - 10(opponentArmor) -1(opponentResistBonus) = 6
     * Opponents health goes from 30 to 24 and then 18
     */
    @Test
    public void InfantryAttacks(){
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        InfantryUnit infantry2 = new InfantryUnit("Hans", 30);
        infantry1.attack(infantry2);
        Assertions.assertEquals(24, infantry2.getHealth());
        infantry1.attack(infantry2);
        Assertions.assertEquals(18, infantry2.getHealth());
    }

    /**
     * Tests RangedUnits BonusResist on the first 2 attacks
     * The attack applies a damage of 15(attack) +3(attackBonus) -8(opponentArmor) -6then4then2(opponentResistBonus) = 4then6then8
     * Opponents health goes from 30 to 26 to 20 then 12
     */
    @Test
    public void RangedBonusResist(){
        RangedUnit ranged1 = new RangedUnit("Geir", 30);
        RangedUnit ranged2 = new RangedUnit("Hans", 30);
        ranged1.attack(ranged2);
        Assertions.assertEquals(26, ranged2.getHealth());
        ranged1.attack(ranged2);
        Assertions.assertEquals(20, ranged2.getHealth());
        ranged1.attack(ranged2);
        Assertions.assertEquals(12, ranged2.getHealth());
        ranged1.attack(ranged2);
        Assertions.assertEquals(4, ranged2.getHealth());
    }
    /**
     *Tests CavalryUnits chargedAttacks the first time it attacks.
     * The attack applies a damage of 20(attack) + 6then2(attackBonus) -12(opponentArmor) -1(opponentResistBonus) = 13then9
     * Opponents health goes from 30 to 17 then 8
     */
    @Test
    public void CavalryChargeAttacks(){
        CavalryUnit cavalry1 = new CavalryUnit("Geir", 30);
        CavalryUnit cavalry2 = new CavalryUnit("Hans", 30);
        cavalry1.attack(cavalry2);
        Assertions.assertEquals(17, cavalry2.getHealth());
        cavalry1.attack(cavalry2);
        Assertions.assertEquals(8, cavalry2.getHealth());
    }

    /**
     * Tests CommanderUnit attacks. Same bonuses as CavalryUnit
     * The attack applies a damage of 25(attack) + 6then2(attackbonus) -15(opponentArmor) -1(opponentResistBonus) = 15then11
     * Opponents health goes from 30 to 15 then 4
     */
    @Test
    public void CommanderUnitAttack(){
        CommanderUnit commander1 = new CommanderUnit("Geir", 30);
        CommanderUnit commander2 = new CommanderUnit("Hans", 30);
        commander1.attack(commander2);
        Assertions.assertEquals(15, commander2.getHealth());
        commander1.attack(commander2);
        Assertions.assertEquals(4, commander2.getHealth());
    }
}
