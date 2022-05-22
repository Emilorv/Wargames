package Wargames.Units;

import Wargames.model.Units.CommanderUnit;
import Wargames.model.Units.Unit;
import Wargames.model.Units.UnitFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class UnitFactoryTest {
    @Test
    @DisplayName("Create units using UnitFactory")
    public void createCommanderUnit(){
        Unit unit = UnitFactory.createUnit("CommanderUnit","Commander",50);
        Assertions.assertTrue(unit instanceof CommanderUnit);
    }

    @Test
    @DisplayName("Create Units with illegal type")
    public void createIllegalUnitType(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> UnitFactory.createUnit("Infantry", "Swordman", 50));
    }

    @Test
    @DisplayName("Create multiple units")
    public void createMultipleUnits(){
        ArrayList<Unit> listOfUnits;
        listOfUnits = UnitFactory.createUnits("InfantryUnit","Swordman",50,50);
        Assertions.assertEquals(50,listOfUnits.size());
    }

    @Test
    @DisplayName("Create units negative amount of times")
    public void createUnitsNegativeAmountOfTimes(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> UnitFactory.createUnits("InfantryUnit", "Swordman", 50,-50));
    }
}
