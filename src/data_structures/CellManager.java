package src.data_structures;

import java.util.ArrayList;
import java.util.Random;

import com.threed.jpct.Object3D;

public class CellManager {
	
	private ArrayList<ArrayList<ArrayList<Cell>>> grid;
	private Object3D template;
	private Random rand;
	private int size;
	
	public CellManager(int size){
		grid = new ArrayList<ArrayList<ArrayList<Cell>>>();
		rand = new Random();
		this.size = size;
		
		populateGrid();
		initializePopulation();
	}
	
	private ArrayList<Cell> getNeighbors(Cell cell){
		ArrayList<Cell> ret = new ArrayList<Cell>();
		int[] coor = cell.getCoordinates();
		for(int x = coor[0] - 1; x <= coor[0] + 1; x++){
			for(int y = coor[1] - 1; y <= coor[1] + 1; y++){
				for(int z = coor[2] - 1; z <= coor[2] + 1; z++){
					int[] curCoor = {x, y, z};
					if(inRange(curCoor))ret.add(getCell(curCoor));
				}
			}
		}
		return ret;
	}
	
	private void initializePopulation(){
		//don't know appropriate density
	}
	
	private void populateGrid(){
		for(int x = 0; x < size; x++){
			grid.add(new ArrayList<ArrayList<Cell>>());
			for(int y = 0; y < size; y++){
				grid.get(x).add(new ArrayList<Cell>());
				for(int z = 0; y < size; z++){
					grid.get(x).get(y).add(new Cell(template));
					grid.get(x).get(y).get(z).setCoordinates(x, y, z);
				}
			}
		}
	}
	
	private boolean inRange(int[] coordinates){
		for(int n : coordinates){
			if(n < 0 || n >= size){return false;}
		} return true;
	}
	
	private Cell getCell(int[] coordinates){
		return grid.get(coordinates[0]).get(coordinates[1]).get(coordinates[2]);
	}
	
	private int generateIndex(){
			return (int) Math.floor(rand.nextDouble()*size);
		}
}
