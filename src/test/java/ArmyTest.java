import Units.InfantryUnit;
import Units.RangedUnit;
import Units.Unit;
import junit.framework.TestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ArmyTest extends TestCase {
    @Test
    public void testAddUnitAndHasUnits() {
        Army army = new Army("First Army");
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        Assertions.assertFalse(army.hasUnits());
        army.addUnit(infantry1);
        Assertions.assertTrue(army.hasUnits());
    }
    @Test
    public void testAddAllAndGetAllUnits() {
        Army army = new Army("First Army");
        ArrayList<Unit> inputUnits = new ArrayList<>();
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        RangedUnit ranged1 = new RangedUnit("Hans", 30);
        inputUnits.add(infantry1);
        inputUnits.add(ranged1);
        Assertions.assertEquals(0, army.getAllUnits().size());
        army.addAll(inputUnits);
        Assertions.assertEquals(2, army.getAllUnits().size());
    }
    @Test
    public void testRemove() {
        Army army = new Army("First Army");
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        army.addUnit(infantry1);
        Assertions.assertTrue(army.hasUnits());
        army.remove(infantry1);
        Assertions.assertFalse(army.hasUnits());
    }

    @Test
    public void testGetRandom() {
        InfantryUnit infantry1 = new InfantryUnit("Geir", 30);
        ArrayList<Unit> inputUnits = new ArrayList<>();
        inputUnits.add(infantry1);
        Army army = new Army("First Army", inputUnits);
        Assertions.assertEquals(1, army.getAllUnits().size());
        Assertions.assertSame(army.getRandom(), infantry1);
        RangedUnit ranged1 = new RangedUnit("Hans", 30);
        army.addUnit(ranged1);
        for (int i=0; i<100; i++){
            Unit randomUnit = army.getRandom();
            Assertions.assertTrue(randomUnit == infantry1 || randomUnit == ranged1);
        }

    }
}