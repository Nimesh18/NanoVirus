package nano.virus;

//represents the nano virus which kills tumorous cells
public class NanoVirus {

	private static final double MAX_DIST = 5000; //max distance nano virus can move
	private Cell currentCell; //the Cell on which the virus is located
	
	//nano virus is placed on an initial Cell
	public NanoVirus(Cell startingPoint) {
		this.currentCell = startingPoint;
	}
	
	//moves the nano virus to a cell, if within MAX_DIST and returns true, false otherwise
	public boolean move(Cell cell) {
		
		Point newLocation = cell.getLocation();
		if(distance(currentCell.getLocation(),newLocation)<=MAX_DIST) {
			currentCell(cell); //set new current Cell
			return true;
		}
		else {
			return false;
		}
	}
	
	//calculates and returns the distance between two points
	public static double distance(Point currenLocation, Point newLocation) {
		double x = currenLocation.getX() - newLocation.getX(),
				y = currenLocation.getY() - newLocation.getY(),
				z = currenLocation.getZ() - newLocation.getZ();
		return Math.sqrt((x*x) + (y*y) + (z*z));
	}

	public Cell currentCell() {
		return currentCell;
	}

	public void currentCell(Cell currentCell) {
		this.currentCell = currentCell;
	}
	
}
