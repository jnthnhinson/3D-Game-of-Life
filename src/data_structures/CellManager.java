package src.data_structures;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.World;

public class CellManager {
	
	private ArrayList<ArrayList<ArrayList<Cell>>> grid;
	private Stack<Cell> updates;
	private Object3D template;
	private World world;
	private Random rand;
	private Cell testCell;
	private int size;
	
	public CellManager(int size, World w){
		grid = new ArrayList<ArrayList<ArrayList<Cell>>>();
		updates = new Stack<Cell>();
		rand = new Random();
		this.size = size;
		this.world = w;
		
//		populateGrid();
		populateWorld();
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
	
	private void populateWorld(){
		int count = 0;
		int sx = 5; int sy = 5; int sz = 5;
		float newx = 0; float newy = 0; float newz = 0;
		for(int x = 0; x < size; x++){
			newx = (float) sx*x;
			for(int y = 0; y < size; y++){
				newy = (float) sy*y;
				for(int z = 0; z < size; z++){
					newz = (float) sz*z;
					count++;
					System.out.println(count);
					Matrix m = new Matrix();
					m.translate(newx, newy, -newz);
					
//					Cell c = grid.get(x).get(y).get(z);
					Cell c = new Cell();
					c.setTranslationMatrix(m);
					world.addObject(c);
				}
			}
		}
		testCell = new Cell();
		world.addObject(testCell);
		world.buildAllObjects();
	}
	
	private void populateGrid(){
		for(int x = 0; x < size; x++){
			grid.add(new ArrayList<ArrayList<Cell>>());
			for(int y = 0; y < size; y++){
				grid.get(x).add(new ArrayList<Cell>());
				for(int z = 0; y < size; z++){
					Cell c = new Cell();
					grid.get(x).get(y).add(c);
					c.setCoordinates(x, y, z);
				}
			}
		}
	}
	
	public Cell getRootCell(){
//		return grid.get(0).get(0).get(0);
		return testCell;
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
