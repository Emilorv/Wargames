package Wargames.Units;
import Wargames.model.Units.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class UnitTest {
    @Test
    @DisplayName("Create a nameless unit")
    public void NamelessUnit(){
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()-> new InfantryUnit("",1));
    }

    @Test
    @DisplayName("Create unit with illegal charaters")
    public void illegalCharactersUnit(){
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()->new InfantryUnit("//Geir",20));
    }

    @Test
    @DisplayName("Create a unit without health")
    public void HealthLessUnit(){
        Assertions.assertThrowsExactly(IllegalArgumentException.class, ()-> new InfantryUnit("Swordsman",0));
    }
    /**
     * The attack applies damage of 15(attack) + 2(attackBonus) - 10(opponentArmor) -1(opponentResistBonus) = 6
     * Opponents health goes from 30 to 24 and then 18
     */
    @Test
    @DisplayName("Attack values from InfantryUnit")
    public void InfantryAttacks(){

        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        InfantryUnit infantry2 = new InfantryUnit("Hans", 30);
        infantry1.attack(infantry2);
        Assertions.assertEquals(24, infantry2.getHealth());
        infantry1.attack(infantry2);
        Assertions.assertEquals(18, infantry2.getHealth());
    }

    /**
     * Attack of Overpowered unit infantry2 will technically set infantry1's health to way less than 0
     */
    @Test
    @DisplayName("Unit health set to less than 0")
    public void UnitHealthSetToLessThanZero(){
        InfantryUnit infantry1 = new InfantryUnit("Swordsman", 1);
        InfantryUnit infantry2 = new InfantryUnit("Puncher",30,30,30,30,30 );
        infantry2.attack(infantry1);
        Assertions.assertEquals(0,infantry1.getHealth());
    }
    /**
     * Tests RangedUnits BonusResist on the first 2 attacks
     * The attack applies a damage of 15(attack) +3(attackBonus) -8(opponentArmor) -6then4then2(opponentResistBonus) = 4then6then8
     * Opponents health goes from 30 to 26 to 20 then 12
     */
    @Test
    @DisplayName("Resist bonus for ranged units")
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
    @DisplayName("Charge attacks from CavalryUnits")
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
     * The attack applies a damage of 25(attack) + 6then2(attack-bonus) -15(opponentArmor) -1(opponentResistBonus) = 15then11
     * Opponents health goes from 30 to 15 then 4
     */
    @Test
    @DisplayName("CommanderUnit attack values")
    public void CommanderUnitAttack(){
        CommanderUnit commander1 = new CommanderUnit("Geir", 30);
        CommanderUnit commander2 = new CommanderUnit("Hans", 30);
        commander1.attack(commander2);
        Assertions.assertEquals(15, commander2.getHealth());
        commander1.attack(commander2);
        Assertions.assertEquals(4, commander2.getHealth());
    }

    /**
     * Test to check if armor is greater than attack -> if True: nothing happens
     * Test is successful if the
     */
    @Test
    @DisplayName("When armor is greater than attack")
    public void ArmorGreaterThanAttack(){
        InfantryUnit infantry1 = new InfantryUnit("Swordsman", 10, 10, 20, 0, 0);
        InfantryUnit infantry2 = new InfantryUnit("Swordsman", 10, 10, 20, 0, 0);
        Assertions.assertEquals(10, infantry2.getHealth());
        infantry1.attack(infantry2);
        Assertions.assertEquals(10, infantry2.getHealth());
    }
}

