package nano.virus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellMaintainer {

	private int numOfCells;//total number of existing cells
	private final CellType[] types = CellType.values();//the types of cells
	private final List<Map<Integer, Cell>> cells;//list of maps that contains the cells, each map stores a specific type of cell
	private List<Cell> tempStorage = new ArrayList<>();//used to temporarily store cells for later addition into cells
	
	public CellMaintainer(int numOfCells) {
		
		this.numOfCells = numOfCells;
		cells = new ArrayList<>(types.length);
		for (int n=0; n < types.length; n++) {
			cells.add(new HashMap<>());
		}
		
		//chooses a cell type based on its probability of occurrence
		for(int i=0; i < numOfCells; i++) {
			double rand = App.rnd.nextDouble();
			CellType cellType;
			if(rand<CellType.TUMOROUS_CELL.prob()) {
				cellType = CellType.TUMOROUS_CELL;
			}
			else if (rand<CellType.TUMOROUS_CELL.prob() + CellType.WHITE_BLOOD_CELL.prob()) {
				cellType = CellType.WHITE_BLOOD_CELL;
			}
			else {
				cellType = CellType.RED_BLOOD_CELL;
			}
			
			Cell cell = new Cell(cellType,i);//creates the cell of CellType cellType at a random location
			cells.get(cellType.ordinal()).put(i, cell);//adds the cell in the appropriate map
		}
	}
	
	//removes a cell from its corresponding map
	public void removeCell(Cell cell) {
		cells.get(cell.getType().ordinal()).remove(cell.getId());
		numOfCells--;
	}
	
	//changes the CellType of the cell to a new CellType newType
	public void convertTo(Cell cell, CellType newType) {
		CellType currentType = cell.getType();
		cell.setCellType(newType); //set the new type of the cell
		tempStorage.add(cell); //add cell to temporary storage to insert after current cycle is complete
		cells.get(currentType.ordinal()).remove(cell.getId()); //remove from old map
	}
	
	//returns a map which stores cells of CellType type
	public Map<Integer, Cell> getCellsOfType(CellType type) {
		return cells.get(type.ordinal());
	}
	
	//returns the number of cells of CellType type
	public int totalCellsOfType(CellType type) {
		return cells.get(type.ordinal()).size();
	}
	
	//returns a random cell of CellType type
	public Cell getCellOfType(CellType type) {
		Map<Integer, Cell> map = cells.get(type.ordinal());
		Collection<Cell> collection = map.values();
		int rand = App.rnd.nextInt(collection.size());
		return (Cell) collection.toArray()[rand];
	}
	
	//returns all neighbours of cell that are of CellType neighbourType
	public ArrayList<Cell> getClosestNeighboursOf(Cell cell, CellType neighbourType){
		
		Map<Integer, Cell> neighbours = cells.get(neighbourType.ordinal());
		
		for (Map.Entry<Integer, Cell> entry : neighbours.entrySet()) {
			double dist = NanoVirus.distance(cell.getLocation(), entry.getValue().getLocation());
			entry.getValue().setDist(dist);
		}
		
		Collection<Cell> collection = neighbours.values();
		ArrayList<Cell> list = new ArrayList<>(collection);
		Collections.sort(list);
		return list;
	}
	
	//returns a random cell 
	public Cell getCell() {
		Collection<Cell> allCells = new ArrayList<>();
		for (Map<Integer, Cell> map : cells) {
			allCells.addAll(map.values());
		}
		int rand = App.rnd.nextInt(allCells.size());
		return (Cell) allCells.toArray()[rand];
	}

	public int getNumOfCells() {
		return numOfCells;
	}
	
	//adds cells from tempStorage into appropriate map
	public void addInfectedCells() {
		for(Cell newCell:tempStorage) {
			cells.get(newCell.getType().ordinal()).put(newCell.getId(), newCell);
		}
		tempStorage.clear();
	}
	
	public void displayCells() {
		int i=0;
		for (Map<Integer, Cell> map : cells) {
			App.println("");
			if(i<types.length)
				App.print(types[i++]+"'s    ");
			App.println("Number of Cells: " + map.size());
			for (Map.Entry<Integer, Cell> entry : map.entrySet())
			{
			    App.println(entry.getValue().toString());
			}
		}
		App.println("-----------------------------------------------------------------------------" +
				"-----------------------------------------------------------------------------------------");
	}
	
}
