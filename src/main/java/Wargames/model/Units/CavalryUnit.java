package Wargames.model.Units;

import Wargames.model.Terrain;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * CavalryUnit class. Unit that excels in melee combat, with a charged attack bonus. Extends Unit class
 */
public class CavalryUnit extends Unit {

    /**
     * The image representing CavalryUnit.
     */
    Image unitImage = new Image(Objects.requireNonNull(InfantryUnit.class.getResourceAsStream("/images/Units/CavalryUnit.png")));

    /**
     * Constructor of CavalryUnit
     *
     * @param name     name of CavalryUnit
     * @param health   health of CavalryUnit
     * @param attack   attack of CavalryUnit
     * @param armor    armor of CavalryUnit
     * @param nAttacks number of times unit has attacked
     * @param nBlocked number of times unit has blocked
     */
    public CavalryUnit(String name, int health, int attack, int armor, int nAttacks, int nBlocked) {
        super(name, health, attack, armor, nAttacks, nBlocked);
    }

    /**
     * Instantiates a new Cavalry unit.
     *
     * @param name   the name
     * @param health the health
     */
    public CavalryUnit(String name, int health){
        super(name,health, 20,12, 0, 0);
    }

    /**
     * Attack bonus for the first attack (6) then 2
     * Bonus damage when in Plains-terrain
     * @return 2
     */
    @Override
    public int getAttackBonus(Terrain terrain) {
        int attackBonus = 2;
        if(getNAttacks()<2){
            attackBonus+=4;
        }
        if (terrain.equals(Terrain.PLAINS)) {
            attackBonus+=2;
        }
        return attackBonus;
    }

    /**
     * Resist bonus small resist-bonus
     * 0 resist bonus when in Forest-terrain
     * @return 1
     */

    @Override
    public int getResistBonus(Terrain terrain) {
        if (terrain.equals(Terrain.FOREST)) {
            return 0;
        }
        return 1;
    }

    @Override
    public Image getUnitImage() {
        return unitImage;
    }
}

