package Wargames.model;

/**
 * The enum Terrain. Used for calculating Attack and resist bonuses for different units
 */
public enum Terrain {
    /**
     * Default terrain. For testing and not affecting any bonuses.
     */
    DEFAULT,
    /**
     * Hill terrain.
     * Bonus-damage for RangedUnits
     */
    HILL,
    /**
     * Plains terrain.
     * Damage-bonus for Commander and CavalryUnits
     */
    PLAINS,
    /**
     * Forest terrain.
     * Damage and Resist-bonus for InfantryUnits
     * Damage-debuff for RangedUnits
     * 0 resist-bonus for Commander and CavalryUnits
     */
    FOREST
}

