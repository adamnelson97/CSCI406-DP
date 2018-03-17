package solutions;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RecursiveSolution {

	private int[] gasSoldPerDay;
	private int tankCapacity;
	private int deliveryCost;
	private int storageCost;
	private int numDays;
	private int totalCost;
	private int numDaysPurchased;
	private int[] gasOrderedPerDay;
	private boolean[] deliveryDays;
	private String inputFile;
	
	public RecursiveSolution(String inputFileName) {
		try {
			inputFile = new String(inputFileName);
			String input = new String();
			FileReader reader;
			reader = new FileReader(inputFileName);
			Scanner scan = new Scanner(reader);
			input = scan.nextLine();
			String[] split = new String[4];
			split = input.split(" ");
			numDays = Integer.parseInt(split[0]);
			tankCapacity = Integer.parseInt(split[1]);
			deliveryCost = Integer.parseInt(split[2]);
			storageCost = Integer.parseInt(split[3]);
			gasSoldPerDay = new int[numDays];
			gasOrderedPerDay = new int[numDays];
			deliveryDays = new boolean[numDays];
			input = scan.nextLine();
			split = new String[numDays];
			split = input.split(" ");
			for (int i = 0; i < numDays; i++) {
				gasSoldPerDay[i] = Integer.parseInt(split[i]);
				deliveryDays[i] = false;
			}
			scan.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int getTankCapacity() {
		return tankCapacity;
	}

	public int getDeliveryCost() {
		return deliveryCost;
	}

	public int getStorageCost() {
		return storageCost;
	}

	public int getNumDays() {
		return numDays;
	}
	
	public int[] getGasSoldPerDay() {
		return gasSoldPerDay;
	}
	
	public void solve(int daysToConsider) {
		
	}
	
	public void outputSolution() {
		try {
			PrintWriter writer = new PrintWriter("output_" + inputFile);
			writer.println(totalCost);
			writer.println(numDaysPurchased);
			for (int i = 0; i < numDays; i++) {
				if (deliveryDays[i] == true) {
					writer.println((i + 1) + " " + gasOrderedPerDay[i]);
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		RecursiveSolution solver = new RecursiveSolution("input.txt");
		solver.solve(solver.getNumDays());
		solver.outputSolution();
		
	}
}
