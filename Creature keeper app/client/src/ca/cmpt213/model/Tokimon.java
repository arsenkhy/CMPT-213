package ca.cmpt213.model;

/**
 * Tokimon class models the information about a
 * single tokimon. Data includes id, name, weight
 * height, fire, electro, fly, freeze abilities,
 * strength, color and row and column.
 */
public class Tokimon {
    private long id;
    private String name;
    private double weight;
    private double height;
    private int abilityFly;             // [0,100]
    private int abilityFire;            // [0,100]
    private int abilityElctrify;        // [0,100]
    private int abilityFreeze;          // [0,100]
    private int strength;
    private String color;
    private int row;
    private int column;

    public Tokimon() {
    }

    // Constructor
    public Tokimon(long id, String name, double weight, double height, int abilityFly, int abilityFire, int abilityElctrify, int abilityFreeze, int strength, String color) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.abilityFly = abilityFly;
        this.abilityFire = abilityFire;
        this.abilityElctrify = abilityElctrify;
        this.abilityFreeze = abilityFreeze;
        this.strength = strength;
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public int getAbilityFly() {
        return abilityFly;
    }

    public int getAbilityFire() {
        return abilityFire;
    }

    public int getAbilityElctrify() {
        return abilityElctrify;
    }

    public int getAbilityFreeze() {
        return abilityFreeze;
    }

    public int getStrength() {
        return strength;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Tokimon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", abilityFly=" + abilityFly +
                ", abilityFire=" + abilityFire +
                ", abilityElctrify=" + abilityElctrify +
                ", abilityFreeze=" + abilityFreeze +
                ", strength=" + strength +
                ", color='" + color + '\'' +
                '}';
    }
}

