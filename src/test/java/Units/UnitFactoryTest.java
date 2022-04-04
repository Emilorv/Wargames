package Units;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class UnitFactoryTest {
    @Test
    public void createCommanderUnit(){
        Unit unit = UnitFactory.createUnit("CommanderUnit","Commander",50);
        Assertions.assertTrue(unit instanceof CommanderUnit);
    }

    @Test
    public void createIllegalUnitType(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> UnitFactory.createUnit("Infantry", "Swordman", 50));
    }

    @Test
    public void createMultipleUnits(){
        ArrayList<Unit> listOfUnits = new ArrayList<>();
        listOfUnits = UnitFactory.createUnits("InfantryUnit","Swordman",50,50);
        Assertions.assertEquals(50,listOfUnits.size());
    }

    @Test
    public void createUnitsNegativeAmountOfTimes(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> UnitFactory.createUnits("InfantryUnit", "Swordman", 50,-50));
    }
}
