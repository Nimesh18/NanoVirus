package nano.virus;

//represents the types that a cell can be 
public enum CellType {
	TUMOROUS_CELL(0.05), RED_BLOOD_CELL(0.7), WHITE_BLOOD_CELL(0.25);
	
	private double prob; //probability of occurrence
	
	private CellType(double prob) {
		this.prob = prob;
	}
	
	public double prob() {
		return prob;
	}
	
	@Override
	public String toString() {
		String name = super.toString().toLowerCase().replaceAll("_", " ");
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
}
