package motor;

import exceptions.*;
import efficiency.*;

import static motor.Motors.polesRange;
import static motor.Motors.powerRange;

/**
 *  The class describes the Induction motor's properties for energy efficiency calculation purposes.
 *  Contains public fields: power, poles, efficiency, and efficiency classes.
 *
 *  <p>The class contains three constructors as well as getters and setters for the manual setup
 *  of the parameters and several supporting methods.
 *
 *  <p> Any time when sets new efficiency (or efficiency class) efficiency class (or efficiency) recalculates
 *  automatically while setting of the new poles or power does not influence on efficiency or efficiency class
 *  changing. One of them must be set manually after poles or power has been changed.
 */
public class Motor {

    /* Efficiency table initialization */
    static { EffTable.setTables(); }

    private double power;
    private int poles;
    private double efficiency;
    private EffClass effClass;

    private int powerIndex = -1;
    private int polesIndex = -1;

    /**
     * Empty constructor is aimed for a manual set up of power, poles, efficiency and efficiency class.
     * Power and poles must be set up first. If efficiency setups afterwards the efficiency class will be
     * set up automatically. Another opportunity is to set up an efficiency class after the power and poles.
     * In this case motor efficiency will be set up automatically as the lowest one for the selected efficiency
     * class.
     */
    public Motor() {    }

     /**
     * This constructor is aimed for setup power, poles and efficiency during new object creation.
     * Efficiency class calculates automatically.
     *
     * @param power power value set up
     * @param poles poles value set up
     * @param efficiency efficiency value set up
     * @throws PowerValueException      if try to set up incorrect power value
     * @throws PolesValueException      if try to set up incorrect poles value
     * @throws EfficiencyValueException if try to set up incorrect efficiency value
     */
    public Motor(double power, int poles, double efficiency) throws
            PowerValueException, PolesValueException, EfficiencyValueException {

        setPower(power);
        setPoles(poles);
        setEfficiency(efficiency);
        setEffClass();
    }

    /**
     * This constructor is aimed for setup power, poles and efficiency class during new object creation.
     * Efficiency calculates automatically as the lowest value for this efficiency class.
     *
     * @param power power value set up
     * @param poles poles value set up
     * @param effClass efficiency class set up
     * @throws PowerValueException      if try to set up incorrect power value
     * @throws PolesValueException      if try to set up incorrect poles value
     */
    public Motor(double power, int poles, EffClass effClass) throws
            PowerValueException, PolesValueException {

        setPower(power);
        setPoles(poles);
        setEffClass(effClass);
        setEfficiency(this, effClass);
    }

    /** Get power value */
    public double getPower()        { return power;      }
    /** Get poles value */
    public int getPoles()           { return poles;      }
    /** Get efficiency value */
    public double getEfficiency()   { return efficiency; }
    /** Get efficiency class */
    public EffClass getEffClass()   { return effClass;   }

    /**
     * Set up power value
     * @param power power value set up
     * @throws PowerValueException if try to set up incorrect power
     */
    public void setPower(double power) throws PowerValueException {
        if (!Motors.powerIsCorrect(power)) throw new PowerValueException(power);
        this.power = power;
        setPowerIndex(power);
    }
    /**
     * Set up poles value
     * @param poles poles value set up
     * @throws PolesValueException if try to set up incorrect poles
     */
    public void setPoles(int poles) throws PolesValueException {
        if (!Motors.polesIsCorrect(poles)) throw new PolesValueException(poles);
        this.poles = poles;
        setPolesIndex(poles);
    }
    /**
     * Set up efficiency.
     * Efficiency class calculated or recalculated afterwards.
     * Power and poles must be set before efficiency setup.
     *
     * @param efficiency efficiency value set up
     * @throws PowerValueException if object has incorrect power value
     * @throws PolesValueException if object has incorrect poles value
     * @throws EfficiencyValueException if try to set up incorrect efficiency value
     */
    public void setEfficiency(double efficiency) throws
            EfficiencyValueException, PolesValueException, PowerValueException {

        if (!Motors.powerIsCorrect(this.power))           throw new PowerValueException(power);
        if (!Motors.polesIsCorrect(this.poles))           throw new PolesValueException(poles);
        if (!Motors.efficiencyIsCorrect(efficiency))      throw new EfficiencyValueException(efficiency);

        this.efficiency = efficiency;
        setEffClass();
    }
    /**
     * Set up efficiency class.
     * Efficiency calculated or recalculated afterwards.
     * Power and poles must be set before efficiency class setup.
     *
     * @param effClass efficiency class set up
     * @throws PowerValueException if object has incorrect power value
     * @throws PolesValueException if object has incorrect poles value
     */
    public void setEffClass(EffClass effClass) throws
            PolesValueException, PowerValueException {

        if (!Motors.powerIsCorrect(this.power))           throw new PowerValueException(power);
        if (!Motors.polesIsCorrect(this.poles))           throw new PolesValueException(poles);

        this.effClass = effClass;
        setEfficiency(this, effClass);
    }

    /* Set up power index and poles index for efficiency and efficiency classes calculations purposes */
    private void setPowerIndex(double power) {
        for (int i = 0; i < powerRange.length; i++)
            if (powerRange[i] == power) this.powerIndex = i;
    }
    private void setPolesIndex(int poles) {
        for (int i = 0; i < polesRange.length; i++)
            if (polesRange[i] == poles) this.polesIndex = i;
    }

    /* Methods for efficiency and efficiency classes determining */
    private void setEfficiency(Motor motor, EffClass effClass) {
        if (effClass == EffClass.IE1) motor.efficiency = EffTable.getIe1()[motor.polesIndex][motor.powerIndex];
        else if (effClass == EffClass.IE2) motor.efficiency = EffTable.getIe2()[motor.polesIndex][motor.powerIndex];
        else if (effClass == EffClass.IE3) motor.efficiency = EffTable.getIe3()[motor.polesIndex][motor.powerIndex];
        else motor.efficiency = EffTable.getIe4()[motor.polesIndex][motor.powerIndex];
    }
    private void setEffClass()  {
        if (this.efficiency < EffTable.getIe1()[this.polesIndex][this.powerIndex])
            this.effClass = EffClass.NOCLASS;
        if (this.efficiency >= EffTable.getIe1()[this.polesIndex][this.powerIndex] &&
                this.efficiency < EffTable.getIe2()[this.polesIndex][this.powerIndex])
            this.effClass = EffClass.IE1;
        if (this.efficiency >= EffTable.getIe2()[this.polesIndex][this.powerIndex] &&
                this.efficiency < EffTable.getIe3()[this.polesIndex][this.powerIndex])
            this.effClass = EffClass.IE2;
        if (this.efficiency>= EffTable.getIe3()[this.polesIndex][this.powerIndex] &&
                this.efficiency < EffTable.getIe4()[this.polesIndex][this.powerIndex])
            this.effClass = EffClass.IE3;
        if (this.efficiency>= EffTable.getIe4()[this.polesIndex][this.powerIndex])
            this.effClass = EffClass.IE4;
    }
}