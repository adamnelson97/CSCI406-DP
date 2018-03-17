package solutions;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Solver {

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
	
	public Solver(String inputFileName) {
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
	
	public int getTotalCost() {
		return totalCost;
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
	
	public int solve(int[] gasToSell, int days) {
		
		// In the case of selling gas for a single day, you need to buy the amount of gas that you are going to sell that day.
		if(days == 1) return deliveryCost;
		
		// If not, we we need between 0 gallons (and make up for it during previous days) and however many gallons we need to 
		// sell that day
		int[] cost = new int[gasToSell[days - 1] + 1];
		int smallestPurchase = 0;
		// it's only possible to store so much fuel so we have to order a minimum amount sometimes
		if(gasToSell[days - 1] > tankCapacity) smallestPurchase = gasToSell[days - 1] - tankCapacity;
		
		// for every amount of fuel we could choose to buy today...
		for(int i = smallestPurchase; i < gasToSell[days - 1] + 1; i++) {
			// we will need to know the least expensive way to have purchased fuel in the previous days.
			int[] upToToday = new int[days - 1];
			// copy the the fuel purchases from previous day into a new array...
			for(int j = 0; j < days - 1; j++) upToToday[j] = gasToSell[j];
			// and modify it's last day to be such that you run out of fuel today given the fuel that you stored yesterday
			upToToday[days - 2] += gasToSell[days - 1] - i;
			// this is the cost of what it took to buy fuel through yesterday, store fuel from yesterday, and order more today
			cost[i] = solve(upToToday, days - 1) + deliveryCost + storageCost * (gasToSell[days - 1] - i);
		}
		// if we don't buy any fuel, we don't pay the delivery cost though
		cost[0] -= deliveryCost;
		int minCost = cost[smallestPurchase];
		for(int i = smallestPurchase; i < gasToSell[days - 1] + 1; i++) {
			if(cost[i] < minCost) minCost = cost[i];
		}
		totalCost = minCost;
		return minCost;
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
		
		//RecursiveSolution solver = new RecursiveSolution("input.txt");
		Solver solver = new Solver("smallInput.txt");
		solver.solve(solver.getGasSoldPerDay(), solver.getNumDays());
		System.out.println(solver.getTotalCost());
		solver.outputSolution();
		
	}
}
