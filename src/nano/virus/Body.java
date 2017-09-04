package nano.virus;

import java.util.ArrayList;
import java.util.Collection;

//represents the body on which the nano virus is injected into
public class Body {

	private CellMaintainer cellStructure; //structure that stores and maintains the cells in the body
	private  NanoVirus virus; //virus which gets injected into the body
	
	public Body() {
		cellStructure = new CellMaintainer(100);
	}
	
	//injects the virus and begins the cycle
	public void injectVirus() {
		
		//injects the nano virus
		virus = new NanoVirus(cellStructure.getCellOfType(CellType.RED_BLOOD_CELL));
		int i=0;
		App.println("Nano virus started at " + virus.currentCell());
		while(cellStructure.totalCellsOfType(CellType.TUMOROUS_CELL) > 0 &&
				cellStructure.totalCellsOfType(CellType.TUMOROUS_CELL) < cellStructure.getNumOfCells()) {
			App.println("Cycle " + i);
			
			boolean moved=false; 
			int prevId = virus.currentCell().getId();
			do {//do while the virus hasn't moved to a valid location as yet
				moved = virus.move(cellStructure.getCell());
			} while (!moved);
			
			if(prevId==virus.currentCell().getId())
				App.println("Nano virus did nothing...");
			else
				App.println("Nano virus moved to  " + virus.currentCell());
			
			if(virus.currentCell().getType()==CellType.TUMOROUS_CELL) {
				App.println("Nano virus removed..." + virus.currentCell());
				cellStructure.removeCell(virus.currentCell());
			}
			
			i++;
			if(i%5==0) {//Tumorous Cell infects neighbouring cells
				App.println("");
				Collection<Cell> tumorousCells = cellStructure.getCellsOfType(CellType.TUMOROUS_CELL).values();
				if(!tumorousCells.isEmpty())
					App.println("Infected Cells");
				for (Cell cell : tumorousCells) {
					ArrayList<Cell> neighbourCells = new ArrayList<>();	//stores an ordered list of the closest cells to the current tumorous cell
					int numOfTum = cellStructure.totalCellsOfType(CellType.TUMOROUS_CELL);
					int numOfRed = cellStructure.totalCellsOfType(CellType.RED_BLOOD_CELL);
					
					if(numOfTum > numOfRed) {//both red and white blood cells are considered 
						if(numOfRed>0)
							neighbourCells = cellStructure.getClosestNeighboursOf(cell, CellType.RED_BLOOD_CELL);
						neighbourCells.addAll(cellStructure.getClosestNeighboursOf(cell, CellType.WHITE_BLOOD_CELL));
					}
					else {//only red blood cells are considered
						neighbourCells = cellStructure.getClosestNeighboursOf(cell, CellType.RED_BLOOD_CELL);
					}	
				
					for (int j = 0; j < neighbourCells.size(); j++) {//goes through the closest neighbours
						CellType type = neighbourCells.get(j).getType(); 
						if(type!=CellType.TUMOROUS_CELL) {//infects the first neighbour that isn't a tumorous cell
							App.println(neighbourCells.get(j) + "  infected by  " + cell);
							cellStructure.convertTo(neighbourCells.get(j), CellType.TUMOROUS_CELL);//infects the neighbour by changing it's type
							break;
						}
					}
				}
				cellStructure.addInfectedCells();//adds infected cells to the existing list of tumorous cells
			}
			cellStructure.displayCells();
		}
		int totalTum = cellStructure.totalCellsOfType(CellType.TUMOROUS_CELL);
		if(totalTum >= cellStructure.getNumOfCells()) {
			App.println("Only tumorous cells remain...");
		}
		else if(totalTum==0) {
			App.println("All tumorous cells are destroyed...");
		}
		//end of cycle
	}
	
}
