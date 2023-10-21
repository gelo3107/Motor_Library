package exceptions;

/**
 * Throws exception if power is out of the standard range of LV motors between 0.12 and 1000 kW.
 */
public class PowerValueException extends Exception {

    private double power;

    public PowerValueException( double power) {
        this.power = power;
    }

    public String toString() {
        return "Incorrect power value: " + power;
    }
}
