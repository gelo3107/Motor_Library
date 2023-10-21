package efficiency;

import java.io.*;
import java.util.Objects;

/**
 * Class sets up energy efficiency tables to determine energy efficiency by efficiency class and wise verse.
 * */
public class EffTable {

    private static final double[][] ie1 = new double[4][41];
    private static final double[][] ie2 = new double[4][41];
    private static final double[][] ie3 = new double[4][41];
    private static final double[][] ie4 = new double[4][41];

    /** Get IE1 table*/
    public static double[][] getIe1() {
        return ie1;
    }
    /** Get IE2 table*/
    public static double[][] getIe2() {
        return ie2;
    }
    /** Get IE3 table*/
    public static double[][] getIe3() {
        return ie3;
    }
    /** Get IE4 table*/
    public static double[][] getIe4() {
        return ie4;
    }

    /**
     * Set resources for IE tables creation
     * */
    public static void setTables() {
        setIE("ie1_table.txt", ie1);
        setIE("ie2_table.txt", ie2);
        setIE("ie3_table.txt", ie3);
        setIE("ie4_table.txt", ie4);
    }

    /* Reading efficiency values from file and write into table*/
    private static void setIE(String fileName, double[][] table) {
        File file = new File(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String powersLine;
            String [] powersets;
            int powerRow =0;
            while ((powersLine = bufferedReader.readLine()) != null) {
                powersets = powersLine.split(" ");
                for (int i = 0; i < 4; i++) {
                    table[i][powerRow] = Double.parseDouble(powersets[i]);
                }
                powerRow++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}