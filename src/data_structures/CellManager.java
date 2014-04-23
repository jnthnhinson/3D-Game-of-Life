package src.data_structures;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.threed.jpct.Object3D;

public class CellManager {
	
	private ArrayList<ArrayList<ArrayList<Cell>>> grid;
	private Stack<Cell> updates;
	private Object3D template;
	private Random rand;
	private int size;
	
	public CellManager(int size){
		grid = new ArrayList<ArrayList<ArrayList<Cell>>>();
		updates = new Stack<Cell>();
		rand = new Random();
		this.size = size;
		
		populateGrid();
		initializePopulation();
	}
	
	private void getNeighbors(Cell cell){
		int[] coor = cell.getCoordinates();
		for(int x = coor[0] - 1; x <= coor[0] + 1; x++){
			for(int y = coor[1] - 1; y <= coor[1] + 1; y++){
				for(int z = coor[2] - 1; z <= coor[2] + 1; z++){
					int[] curCoor = {x, y, z};
					if(inRange(curCoor) && !coorEquivalence(coor, curCoor)){
						updates.push(getCell(curCoor));
					}
				}
			}
		}
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
	
	private boolean coorEquivalence(int[] x, int[] y){
		for(int n = 0; n < x.length; n++){
			if(x[n] != y[n]){return false;}
		} return true;
	}
	
	private int generateIndex(){
			return (int) Math.floor(rand.nextDouble()*size);
		}
}
