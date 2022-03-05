package Units;
import Units.InfantryUnit;
import Units.Unit;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InfantryUnitTest {
    @Test
    public void attackInfantryUnit(){
        InfantryUnit infantry1 = new InfantryUnit("Hey", 10);
        InfantryUnit infantry2 = new InfantryUnit("Geir", 10);
        assertTrue(infantry2.getHealth()==10);
        infantry1.attack(infantry2);
        assertTrue(infantry2.getHealth()==8);
    }
    public void getHealthOfUnit(){
        InfantryUnit infantry1 = new InfantryUnit("Hey",10);

    }
}