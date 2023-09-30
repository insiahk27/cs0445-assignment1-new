/** Driver program for the extra credit part of Assignment 1.
 * @author Insiah Kizilbash
 * This program just runs and tests the multiply() in the ReallyLongInt class 
 * through creating new ReallyLongInt objects and printing out the values returned
 * from the multiply().
 */
import java.util.*;

public class RLITest2 {
	public static void main (String [] args){
		
        System.out.println("\nTesting the multiply():");
        ReallyLongInt R1 = new ReallyLongInt("1232");
        ReallyLongInt R2 = new ReallyLongInt("10045");

		ReallyLongInt R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3 + "\n");

        R1 = new ReallyLongInt("1324");
        R2 = new ReallyLongInt("205");
        R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3 + "\n");

        R1 = new ReallyLongInt("14");
        R2 = new ReallyLongInt("4");
        R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3 + "\n");

        R1 = new ReallyLongInt("345");
        R2 = new ReallyLongInt("73");
        R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3 + "\n");

        R1 = new ReallyLongInt("0");
        R2 = new ReallyLongInt("130");
        R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3 + "\n");

        R1 = new ReallyLongInt("512");
        R2 = new ReallyLongInt("0");
        R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3 + "\n");

        R1 = new ReallyLongInt("134533");
        R2 = new ReallyLongInt("568356");
        R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3 + "\n");

        R1 = new ReallyLongInt("6789199793");
        R2 = new ReallyLongInt("4985754355");
        R3 = R1.multiply(R2);
		System.out.println(R1 + " * " + R2 + " = " + R3 + "\n");

    }
}
