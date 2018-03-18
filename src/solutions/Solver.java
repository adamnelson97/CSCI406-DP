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
	
	public int recursiveSolve(int[] gasToSell, int days) {
		
		// In the case of selling gas for a single day, you need to buy the amount of gas that you are going to sell that day.
		if(days == 1) return deliveryCost;
		
		// If not, we we need between 0 gallons (and make up for it during previous days) and however many gallons we need to 
		// sell that day
		int[] cost = new int[gasToSell[days - 1] + 1];
		int smallestPurchase = 0;
		// there's a maximum amount of fuel which we would ever want to store in the tank
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
			cost[i] = recursiveSolve(upToToday, days - 1) + deliveryCost + storageCost * (gasToSell[days - 1] - i);
		}
		
		// if we don't buy any fuel, we don't pay the delivery cost though
		cost[0] -= deliveryCost;
		
		// find the smallest cost option and return the cost
		int minCost = cost[smallestPurchase];
		for(int i = smallestPurchase; i < gasToSell[days - 1] + 1; i++) {
			if(cost[i] < minCost) minCost = cost[i];
		}
		totalCost = minCost;
		return minCost;
	}
	
	public void dyProSolve() {
		// there's a maximum amount of fuel which we would ever want to store in the tank
		int maxStorage;
		if (tankCapacity*storageCost <= deliveryCost) maxStorage = tankCapacity;
		else maxStorage = (deliveryCost - (deliveryCost % storageCost)) / storageCost;
		
		// this is our lookup table
		TableEntry[][] bestCosts = new TableEntry[maxStorage + 1][numDays];
		
		// for the first day, we have to pay a flat delivery rate regardless of the amount of gas purchased.
		// we also set all the parent pointers to null to mark these days don't have parent cells
		for(int gasLeftOver = 0; gasLeftOver < maxStorage + 1; gasLeftOver++) {
			TableEntry newCost = new TableEntry(0, gasSoldPerDay[0] + gasLeftOver, deliveryCost, null);
			bestCosts[gasLeftOver][0] = newCost;
		}
		
		// for every day..
		for(int today = 1; today < numDays; today++) {
			//and for every amount of gas which we want to have left over on that day...
			for(int gasLeftOver = 0; gasLeftOver <= maxStorage; gasLeftOver++) {
				// we need to know the cheapest way to reach that state.
				int[] costs = new int[gasSoldPerDay[today] + gasLeftOver + 1];
				// we can only store so much in the tank, so in certain cases we must purchase at least a minimum amount.
				int smallestPurchase = 0;
				if((gasSoldPerDay[today] + gasLeftOver) > maxStorage) smallestPurchase = gasSoldPerDay[today] + gasLeftOver - maxStorage;
				// for every amount of gas we could choose to order today...
				for(int gasBoughtToday = smallestPurchase; gasBoughtToday <= gasSoldPerDay[today] + gasLeftOver; gasBoughtToday++) {
					// we need to know how much it would cost given the amount of gas we have left over from yesterday
					costs[gasBoughtToday] = bestCosts[gasSoldPerDay[today] + gasLeftOver - gasBoughtToday][today - 1].getCost(); 
					costs[gasBoughtToday] += deliveryCost;
					costs[gasBoughtToday] += storageCost * (gasSoldPerDay[today] + gasLeftOver - gasBoughtToday);
				}
				// if we don't purchase gas, we don't pay the delivery cost.
				costs[0] -= deliveryCost;
				
				// now we find the minimum cost that gives us the desired amount of gas left over.
				int minCost = costs[smallestPurchase];
				int amountPurchased = smallestPurchase;
				int amountFromYesterday = gasSoldPerDay[today] + gasLeftOver - smallestPurchase;
				for(int gasBoughtToday = smallestPurchase; gasBoughtToday <= gasSoldPerDay[today] + gasLeftOver; gasBoughtToday++) {
					if(costs[gasBoughtToday] < minCost) {
						minCost = costs[gasBoughtToday];
						amountPurchased = gasBoughtToday;
						amountFromYesterday = gasSoldPerDay[today] + gasLeftOver - gasBoughtToday;
					}
				}
				// now we store the best option in the table (the day, the amount we bought today, the cost so far, and the state we were in yesterday).
				TableEntry newCost = new TableEntry(today, amountPurchased, minCost, bestCosts[amountFromYesterday][today-1]); 
				bestCosts[gasLeftOver][today] = newCost;
			}
		}
		
		// this is the cost of the solution.
		totalCost = bestCosts[0][numDays - 1].getCost();
		// now we need to look back through the table and see how much we bought on each day.
		TableEntry startCell = bestCosts[0][numDays - 1];
		while(startCell != null){
			if( startCell.getGasPurchased() != 0) {
				numDaysPurchased++;
				deliveryDays[startCell.getDay()] = true;
			}
			gasOrderedPerDay[startCell.getDay()] = startCell.getGasPurchased(); 
			startCell = startCell.getParent();
		}
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
		
		Solver solver = new Solver("input.txt");
		solver.dyProSolve();
		solver.outputSolution();
		System.out.println("done");
	}
}
