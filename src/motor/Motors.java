package motor;

/* Determines limit values of power, poles and efficiency */
class Motors {

    protected static final double[] powerRange =
            {0.12, 0.18, 0.20, 0.25, 0.37, 0.40, 0.55, 0.75, 1.1, 1.5, 2.2, 3, 4, 5.5, 7.5, 11, 15, 18.5,
                    22, 37, 45, 55, 75, 90, 110, 132, 160, 200, 250, 315, 355, 400, 450, 500, 560, 630, 710, 800, 900, 1000};

    protected static final int[] polesRange = {2, 4, 6, 8};

    protected static boolean powerIsCorrect(double putPower) {
        for (double power : powerRange) if (putPower == power) return true;
        return false;
    }

    protected static boolean polesIsCorrect(int putPoles) {
        for (int poles : polesRange) if (putPoles == poles) return true;
        return false;
    }

    protected static boolean efficiencyIsCorrect(double putEfficiency) {
        return putEfficiency > 0 && putEfficiency < 100;
    }
}