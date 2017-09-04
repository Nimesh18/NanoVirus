package nano.virus;

//represents a Cell
public class Cell implements Comparable<Cell>{
	
	private static final int MAX_RANGE = 5000; //max coordinate
	private static final int MIN_RANGE = 1; //min coordinate
	private Point location; //location of the cell
	private CellType cellType; //type of cell
	private double dist; //the distance from a particular point or cell under consideration
	private int id; //unique identifier for the cell

	public Cell(CellType cellType, int i) {
		this.location = randomizedLocation();
		this.cellType = cellType;
		this.id = i;
	}

	//returns a randomised Point within the Min and Max Range
	private Point randomizedLocation() {
		int[]points = new int[3];
		for(int i=0; i < points.length; i++) {
			points[i] = App.rnd.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE; 
		}
		return new Point(points[0],points[1],points[2]);
	}
	
	public Point getLocation() {
		return location;
	}
	
	public void setCellType(CellType cellType) {
		this.cellType = cellType;
	}

	public CellType getType() {
		return cellType;
	}

	public double getDist() {
		return dist;
	}

	public void setDist(double dist) {
		this.dist = dist;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return String.format("Cell %2d %-18s %s", id,location,cellType);
	}

	@Override
	public int compareTo(Cell o) {
		if(getDist()>o.getDist())
			return 1;
		else if (getDist()<o.getDist()) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
}
