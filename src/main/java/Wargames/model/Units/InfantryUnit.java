package Wargames.model.Units;

import Wargames.model.Terrain;
import javafx.scene.image.Image;

/**
 * InfantryUnit class. Basic melee unit. Extends Unit class
 */
public class InfantryUnit extends Unit {

    /**
     * The image representing InfantryUnit.
     */
    Image unitImage = new Image(InfantryUnit.class.getResourceAsStream("/images/Units/InfantryUnit.png"));

    /**
     * Constructor of InfantryUnit
     *
     * @param name     name of InfantryUnit
     * @param health   health of InfantryUnit
     * @param attack   attack of InfantryUnit
     * @param armor    armor of InfantryUnit
     * @param nAttacks number of times unit has attacked
     * @param nBlocked number of times unit has blocked
     */
    public InfantryUnit(String name, int health, int attack, int armor, int nAttacks, int nBlocked) {
        super(name, health, attack, armor, nAttacks, nBlocked);
    }

    /**
     * Instantiates a new Infantry unit.
     *
     * @param name   the name
     * @param health the health
     */
    public InfantryUnit(String name, int health){
       super(name,health, 15,10, 0, 0);
    }

    /**
     * Attackbonus for melee-range
     * Bonus damage in Forest-terrain
     * @return 4 or 2
     */
    @Override
    public int getAttackBonus(Terrain terrain) {
        if (terrain.equals(Terrain.FOREST)) {
            return 4;
        }
        return 2;
    }

    /**
     * Resistbonus small resist-bonus
     * Bonus resist in Forest-terrain
     * @return 2 or 1
     */
    @Override
    public int getResistBonus(Terrain terrain) {
        if (terrain.equals(Terrain.FOREST)) {
            return 2;
        }
        return 1;
    }

    @Override
    public Image getUnitImage() {
        return unitImage;
    }
}

