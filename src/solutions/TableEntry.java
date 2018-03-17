package solutions;

public class TableEntry {
	private int day;
	private int gasPurchased;
	private int cost;
	private TableEntry parent;
	
	public TableEntry(int day, int gasPurchased, int cost, TableEntry parent) {
		this.day = day;
		this.gasPurchased = gasPurchased;
		this.cost = cost;
		this.parent = parent;
	}

	public int getDay() {
		return day;
	}
	
	public int getGasPurchased() {
		return gasPurchased;
	}
	
	public int getCost() {
		return cost;
	}
	
	public TableEntry getParent() {
		return parent;
	}
}
