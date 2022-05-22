package Wargames;

import Wargames.model.Units.*;
import Wargames.model.Army;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ArmyTest {
    ArrayList<Unit> units;
    @org.junit.Test
    public void testData(){
        units = new ArrayList<>();
        units.add(new InfantryUnit("Swordsman",15));
        units.add(new RangedUnit("Archer",10));
        units.add(new CavalryUnit("Knight",30));
        units.add(new CommanderUnit("Leader",30));
    }
    @Test
    public void namelessArmy(){
        ArrayList<Unit> units = new ArrayList<>();
        InfantryUnit infantry1 = new InfantryUnit("Swordsman",15);
        units.add(infantry1);
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () ->new Army("",units));
    }
    /**
     * This test Creates an army and a unit. The test is successful if the method army.hasUnits()
     * first returns false, and after adding the unit it returns true
     */
    @Test
    public void testAddUnitAndHasUnits() {
        Army army = new Army("ArmyAddUnitTest");
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        Assertions.assertFalse(army.hasUnits());
        army.addUnit(infantry1);
        Assertions.assertTrue(army.hasUnits());
    }

    /**
     * The test checks if the methods addAll() and getAll() works. The test is successful if
     * army.getAllUnits first returns false, and after using army.addAll() it returns true
     */
    @Test
    public void testAddAllAndGetAllUnits() {
        Army army = new Army("ArmyGetAddAndGetUnitsTest");
        ArrayList<Unit> inputUnits = new ArrayList<>();
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        RangedUnit ranged1 = new RangedUnit("Hans", 30);
        inputUnits.add(infantry1);
        inputUnits.add(ranged1);
        Assertions.assertEquals(0, army.getAllUnits().size());
        army.addAll(inputUnits);
        Assertions.assertEquals(2, army.getAllUnits().size());
    }

    /**
     * Test that checks if unit is removed from army. The test is successful if
     * army.hasUnits() first returns true, then after using army.remove() it returns false
     */
    @Test
    public void testRemove() {
        Army army = new Army("ArmyRemoveTest");
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        army.addUnit(infantry1);
        Assertions.assertTrue(army.hasUnits());
        army.remove(infantry1);
        Assertions.assertFalse(army.hasUnits());
    }

    /**
     * Test get random unit. First tests if the random unit in an army with one unit always gets the one, and then tests if
     * the random unit in an army with two units always will be one of the two
     */
    @Test
    public void testGetRandom() {
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        ArrayList<Unit> inputUnits = new ArrayList<>();
        inputUnits.add(infantry1);
        Army army = new Army("ArmyGetRandomTest", inputUnits);
        Assertions.assertEquals(1, army.getAllUnits().size());
        for (int i=0; i<100; i++){
            Assertions.assertSame(army.getRandom(), infantry1);
        }
        RangedUnit ranged1 = new RangedUnit("Hans", 30);
        army.addUnit(ranged1);
        for (int i=0; i<100; i++){
            Unit randomUnit = army.getRandom();
            Assertions.assertTrue(randomUnit == infantry1 || randomUnit == ranged1);
        }
    }

    /**
     * Get units of each type.
     */
    @Test
    public void getUnitsOfEachType(){
        ArrayList<Unit> infantryUnits = new ArrayList<>();
        infantryUnits.add(new InfantryUnit("Geir",30));
        infantryUnits.add( new InfantryUnit("Geir2", 30));

        ArrayList<Unit> rangedUnits = new ArrayList<>();
        rangedUnits.add(new RangedUnit("Archer", 30));
        rangedUnits.add(new RangedUnit("Gunslinger",30));
        rangedUnits.add(new RangedUnit("Birk",30));

        ArrayList<Unit> commanderUnits = new ArrayList<>();
        commanderUnits.add(new CommanderUnit("Com", 30));
        commanderUnits.add(new CommanderUnit("Gu",30));
        commanderUnits.add(new CommanderUnit("Bir",30));

        ArrayList<Unit> cavalryUnits = new ArrayList<>();
        cavalryUnits.add(new CavalryUnit("Knight", 50));

        ArrayList<Unit> inputUnits = new ArrayList<>();
        inputUnits.addAll(infantryUnits);
        inputUnits.addAll(rangedUnits);
        inputUnits.addAll(commanderUnits);
        inputUnits.addAll(cavalryUnits);

        Army army = new Army("ArmyGetUnitsOfEachTypeTest", inputUnits);
        Assertions.assertEquals(infantryUnits, army.getInfantryUnits());
        Assertions.assertEquals(rangedUnits,army.getRangedUnits());
        Assertions.assertEquals(commanderUnits,army.getCommanderUnits());
        Assertions.assertEquals(cavalryUnits,army.getCavalryUnits());
    }

    @Test
    @DisplayName("Get Army Health")
    public void getArmyHealth(){
        testData();
        Army army = new Army("Army", units);
        Assertions.assertEquals(85,army.getArmyHealth());
    }

    @Test
    @DisplayName("Army copy test")
    public void armyCopy(){
        testData();
        Army army = new Army("Army", units);
            Assertions.assertEquals(army.getArmyHealth(), army.copy().getArmyHealth());
    }
}