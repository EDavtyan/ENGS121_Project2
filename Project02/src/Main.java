import org.xml.sax.helpers.AttributesImpl;
import java.lang.Math; // importing java.lang package
import java.util.*;


import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Main {
    static double M1 = 0;
    static double[] InitialPositionM1 = {0, 0};
    static double M2 = 0;
    static double[] InitialPositionM2 = {0, 0};
    static double M3 = 0;
    static double[] InitialPositionM3 = {0, 0};
    static double myu1 = 0;
    static double myu2 = 0;
    static double myu3 = 0;
    static double F = 0;
    static double t = 0;
    final static double g = 9.8;
    static double[][] TimeAndForce;

    public static void Input() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the time and force pairs. Please input at least 2 pairs");
        int n = scanner.nextInt();
        double[][] force_time_pairs = new double[n][2];
        TimeAndForce = force_time_pairs;
        for (int i = 0; i < n; i++) {
            System.out.println("Input the time moment in seconds ");
            t = scanner.nextDouble();
            TimeAndForce[i][0] = t;
            System.out.println("Input the respective force (in Newtons) of time moment " + t + " .");
            System.out.println("The force needs to be between -300N and 300N");
            F = scanner.nextDouble();
            while (F < -300 || F > 300) {
                System.out.println("The input must be from the specified range");
                System.out.println("The force needs to be between -300N and 300N");
                F = scanner.nextDouble();
            }
            TimeAndForce[i][1] = F;
        }
        System.out.println("Prepare to insert the masses of the objects as well as their initial positions.");
        System.out.println("Insert the mass for M1. It has to be between 0 and 10");
        M1 = scanner.nextDouble();
        while (M1 < 0 || M1 > 10) {
            System.out.println("Invalid mass for M1 was inserted");
            System.out.println("Insert the mass. It has to be between 0 and 10");
            M1 = scanner.nextDouble();
        }
        System.out.println("Insert the initial position of the M1.");
        System.out.println("x coordinate");
        double x = scanner.nextDouble();
        System.out.println("y coordinate");
        double y = scanner.nextDouble();
        InitialPositionM1[0] = x;
        InitialPositionM1[1] = y;
        System.out.println("Insert the mass for M2. It has to be between 0 and 10");
        M2 = scanner.nextDouble();
        while (M2 < 0 || M2 > 10) {
            System.out.println("Invalid mass for M2 was inserted");
            System.out.println("Insert the mass. It has to be between 0 and 10");
            M2 = scanner.nextDouble();
        }
        System.out.println("Insert the initial position of the M2.");
        System.out.println("x coordinate");
        x = scanner.nextDouble();
        System.out.println("y coordinate");
        y = scanner.nextDouble();
        InitialPositionM2[0] = x;
        InitialPositionM2[1] = y;
        System.out.println("Insert the mass for M3. It has to be between 0 and 10");
        M3 = scanner.nextDouble();
        while (M3 < 0 || M3 > 10) {
            System.out.println("Invalid mass for M3 was inserted");
            System.out.println("Insert the mass. It has to be between 0 and 10");
            M3 = scanner.nextDouble();
        }
        System.out.println("Insert the initial position of the M3.");
        System.out.println("x coordinate");
        x = scanner.nextDouble();
        System.out.println("y coordinate");
        y = scanner.nextDouble();
        InitialPositionM3[0] = x;
        InitialPositionM3[1] = y;
        System.out.println("Prepare to insert the friction values");
        System.out.println("the values must be between 0 and 0.5");
        System.out.println("Please input friction myu1");
        myu1 = scanner.nextDouble();
        while (myu1 < 0 || myu1 > 0.5) {
            System.out.println("Invalid friction was inserted");
            System.out.println("the values must be between 0 and 0.5");
            myu1 = scanner.nextDouble();
        }
        System.out.println("Please input friction myu2");
        myu2 = scanner.nextDouble();
        while (myu2 < 0 || myu2 > 0.5) {
            System.out.println("Invalid friction was inserted");
            System.out.println("the values must be between 0 and 0.5");
            myu2 = scanner.nextDouble();
        }
        System.out.println("Please input friction myu3");
        myu3 = scanner.nextDouble();
        while (myu3 < 0 || myu3 > 0.5) {
            System.out.println("Invalid friction was inserted");
            System.out.println("the values must be between 0 and 0.5");
            myu3 = scanner.nextDouble();
        }
        System.out.println("Prepare to insert the time moment of the dispostition for the objects M1, M2, and M3.");
        t = scanner.nextDouble();
        Disposition(t);
        scanner.close();
    }

    public static double Interpolate(double value) {
        double result = 0;
        int n = TimeAndForce.length;
        java.util.Arrays.sort(TimeAndForce, (a, b) -> Double.compare(a[0], b[0]));
        if (value < TimeAndForce[0][0]) {
            result = TimeAndForce[0][1] + (value - TimeAndForce[0][0]) * (TimeAndForce[1][1] - TimeAndForce[0][1]) / (TimeAndForce[1][0] - TimeAndForce[0][0]);
        } else if (value > TimeAndForce[n - 1][0]) {
            result = TimeAndForce[n - 2][1] + (value - TimeAndForce[n - 2][0]) * (TimeAndForce[n - 1][1] - TimeAndForce[n - 2][1]) / (TimeAndForce[n - 1][0] - TimeAndForce[n - 2][0]);
        } else {
            for (int i = 0; i < n - 1; i++) {
                if (value >= TimeAndForce[i][0] && value <= TimeAndForce[i + 1][0]) {
                    result = TimeAndForce[i][1] + (value - TimeAndForce[i][0]) * (TimeAndForce[i + 1][1] - TimeAndForce[i][1]) / (TimeAndForce[i + 1][0] - TimeAndForce[i][0]);
                } else {
                    System.out.println();
                }
            }
        }
        return result;
    }

    public static void Disposition(double t1) {
        double a2 = (-(M1 + M3) * (myu2 * M2 * g - M3 * g + 2 * myu3 * Interpolate(t1)) + M3 * (myu2 * M2 * g - myu1 * M1 * g + myu1 * myu2 * M2 * g)) / (M2 * (M1 + M3) - M3 * (-M2 + myu1 * M2 - M1 - M3));
        double a1 = (-M2 * a2 + myu2 * M2 * g - myu1 * M1 * g + myu1 * M2 * a2 + myu1 * myu2 * M2 * g) / (M1 + M2);
        InitialPositionM1[0] = InitialPositionM1[0] + 0.5 * a1 * Math.pow(t1, 2);
        InitialPositionM2[0] = InitialPositionM2[0] + 0.5 * a2 * Math.pow(t1, 2);
        InitialPositionM2[1] = InitialPositionM2[1] + 0.5 * a2 * Math.pow(t1, 2);
        InitialPositionM3[1] = InitialPositionM3[1] + 0.5 * a2 * Math.pow(t1, 2);
        System.out.println("M1 is at position " + "[ " + InitialPositionM1[0] + " ; " + InitialPositionM1[1] + " ]" + " at time " + t1);
        System.out.println("M2 is at position " + "[ " + InitialPositionM2[0] + " ; " + InitialPositionM2[1] + " ]" + " at time " + t1);
        System.out.println("M3 is at position " + "[ " + InitialPositionM3[0] + " ; " + InitialPositionM3[1] + " ]" + " at time " + t1);
    }

    public static void main(String[] args) {
        Input();
    }
}
