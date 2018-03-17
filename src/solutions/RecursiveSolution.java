package solutions;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class RecursiveSolution {

	private int[] gasSoldPerDay;
	private int tankCapacity;
	private int deliveryCost;
	private int storageCost;
	private int numDays;
	
	public RecursiveSolution(String inputFileName) {
		try {
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
			input = scan.nextLine();
			split = new String[numDays];
			split = input.split(" ");
			for (int i = 0; i < numDays; i++) {
				gasSoldPerDay[i] = Integer.parseInt(split[i]);
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
	
	public static void main(String[] args) {
		
	}
}
