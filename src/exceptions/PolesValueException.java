package exceptions;

/**
 * Throws exception if poles are not 2 or 4 or 6 or 8.
 */
public class PolesValueException extends Exception {

    private double poles;

    public PolesValueException( double poles) {
        this.poles = poles;
    }

    public String toString() {
        return "Incorrect poles value: " + poles;
    }
}

