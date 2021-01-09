package ca.cmpt213.ModelToki;

/**
 * Tokimon class models the information about the
 * tokimon. Data includes name, ability type, height
 * weight and strength.
 */
public class Tokimon {
    private String name;
    private String type;
    private double height;          // Measured in m
    private double weight;          // Measured in kg
    private int strength = 0;       // Default value of strength. MAX: 100

    public Tokimon(String name, String type, double height, double weight) {
        this.name = name;
        this.type = type;
        this.height = height;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    /* toString() method for printing Tokimon in order
       to avoid printing meaningless output and
       having visual representation of an object */
    public String toString() {
        return getClass().getName() + "{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", strength=" + strength +
                '}';
    }
}
