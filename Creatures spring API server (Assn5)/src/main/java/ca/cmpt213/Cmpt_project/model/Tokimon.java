package ca.cmpt213.Cmpt_project.model;

/**
 * Tokimon class models the information about a
 * single tokimon. Data includes id, name, weight
 * height, fire, electro, fly, freeze abilities,
 * strength, color and row and column.
 */
public class Tokimon {
    private static long total = 0;
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

    // Constructor
    public Tokimon(String name, double weight, double height, int abilityFly, int abilityFire, int abilityElctrify, int abilityFreeze, int strength, String color) {
        this.id = total;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.abilityFly = abilityFly;
        this.abilityFire = abilityFire;
        this.abilityElctrify = abilityElctrify;
        this.abilityFreeze = abilityFreeze;
        this.strength = strength;
        this.color = color;
        total++;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setAbilityFly(int abilityFly) {
        this.abilityFly = abilityFly;
    }

    public void setAbilityFire(int abilityFire) {
        this.abilityFire = abilityFire;
    }

    public void setAbilityElctrify(int abilityElctrify) {
        this.abilityElctrify = abilityElctrify;
    }

    public void setAbilityFreeze(int abilityFreeze) {
        this.abilityFreeze = abilityFreeze;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
