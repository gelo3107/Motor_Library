package exceptions;

/**
 * Throws exception if efficiency value in the range (-INF...0] and [1 ... INF).
 */
public class EfficiencyValueException extends Exception{

    private double efficiency;

    public EfficiencyValueException( double efficiency) {
        this.efficiency = efficiency;
    }

    public String toString() {
        return "Incorrect efficiency value: " + efficiency;
    }

}
