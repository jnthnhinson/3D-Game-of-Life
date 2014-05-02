package src.data_structures;

import java.util.Random;
import java.util.Stack;

import com.threed.jpct.Matrix;
import com.threed.jpct.World;

public class CellManager {

	private Cell[][][] grid;
	private Stack<Cell> updates;
	private World world;
	private Random rand;
	private int size, cellSize;
	private RandomColor getColor;

	public CellManager(int size, World w){
		getColor = new RandomColor();
		grid = new Cell[size][size][size];
		updates = new Stack<Cell>();
		rand = new Random();
		this.size = size;
		this.cellSize = CellSize.LARGE.getSize();
		this.world = w;
		
		populateGrid();
		populateWorld();
		InitialConditions.GRID.initialize(this);
	}

	private void populateGrid(){
		for(int x = 0; x < size; x ++){
			for(int y = 0; y < size; y++){
				for(int z = 0; z < size; z++){
					Cell c = new Cell(cellSize);
					System.out.println(x + " : " + y + " : " + z);
					this.grid[x][y][z] = c;
					c.setCoordinates(x, y, z);
				}
			}
		}
	}

	private void populateWorld(){
		float dist = cellSize * (float)2;
		float newx = 0; float newy = 0; float newz = 0;
		Cell c;

		for(int z = 01; z < size-1; z++){
			newz = (float) dist*z;
			for(int y = 0; y < size-1; y++){
				newy = (float) dist*y;
				for(int x = 0; x < size-1; x++){
					newx = (float) dist*x;

					Matrix m = new Matrix();
					m.translate(newx, newy, newz);

					c = getCell(x, y, z);
					c.setTranslationMatrix(m);
					world.addObject(c);
				} 
			}
		}
	}

	private void handleNeighbors(Cell cell, boolean bool){
		int[] coor = cell.getCoordinates();
		for(int x = coor[0] - 1; x <= coor[0] + 1; x++){
			for(int y = coor[1] - 1; y <= coor[1] + 1; y++){
				for(int z = coor[2] - 1; z <= coor[2] + 1; z++){
					int[] curCoor = {x, y, z};
					if(inRange(curCoor) && !coorEquivalence(coor, curCoor) && !updates.contains(getCell(x, y, z))){
						Cell cur = getCell(x, y, z);
						if(bool){updates.push(cur);}
						cur.incPop(bool);
					}
				}
			}
		}
	}

	private int numCornerNeighbors(Cell cell){
		int[] coor = cell.getCoordinates();
		int num = 0;
		for(int x = coor[0] - 1; x <= coor[0] + 1; x++){
			for(int y = coor[1] - 1; y <= coor[1] + 1; y++){
				for(int z = coor[2] - 1; z <= coor[2] + 1; z++){
					int[] curCoor = {x, y, z};
					if(inRange(curCoor) && !coorEquivalence(coor, curCoor) && isCorner(coor, curCoor) 
							&& getCell(x, y, z).isAlive()){
						num++;
					}
				}
			}
		}
		return num;
	}

	int numFlatNeighbors(Cell cell){
		int[] coor = cell.getCoordinates();
		int num = 0;
		for(int x = coor[0] - 1; x <= coor[0] + 1; x++){
			for(int y = coor[1] - 1; y <= coor[1] + 1; y++){
				for(int z = coor[2] - 1; z <= coor[2] + 1; z++){
					int[] curCoor = {x, y, z};
					if(inRange(curCoor) && !coorEquivalence(coor, curCoor) && !isCorner(coor, curCoor) 
							&& getCell(x, y, z).isAlive()){
						num++;
					}
				}
			}
		}
		return num;
	}

	int totalNeighbors(Cell cell){
		return cell.numNeighbors();
	}


//	public void rules() {
//		Cell c;
//		while(true){
//			for (int x = 0; x < grid[0].length; ++x ){
//				for (int y = 0; y < grid[0].length; y++){
//					for (int z = 0; z < grid[0].length; z++){
//						c = getCell(x, y, z);
//						if (totalNeighbors(c) <= 1 && c.isDead(c)) {
//							c.murderCell(c);
//						}
//						else if (totalNeighbors(c) >= 5 && totalNeighbors(c) < 8) {
//							c.birthCell(c);
//						}
//						else if (totalNeighbors(c) >= 8) {
//							c.murderCell(c);
//						}
//					}
//				}
//			}
//		}
//	}

	private boolean isCorner(int[] ori, int[] cur){
		return (((ori[0] - cur[0]) * (ori[1] - cur[1]) * (ori[2] - cur[2])) % 2 != 0);
	}

	private boolean inRange(int[] coordinates){
		for(int n : coordinates){
			if(n < 0 || n >= size){return false;}
		} return true;
	}

	public Cell getCell(int x, int y, int z){
		return grid[x][y][z];
	}

	private boolean coorEquivalence(int[] x, int[] y){
		for(int n = 0; n < x.length; n++){
			if(x[n] != y[n]){return false;}
		} return true;
	}
	
	public int[] generateCoor(){
		int[] cur = {generateIndex(), generateIndex(), generateIndex()};
		return cur;
	}

	private int generateIndex() {
		return (int) Math.floor(rand.nextDouble()*size);
	}
	
	public void update(){
		Cell c;
		boolean cond;
		while(!updates.isEmpty()){
			c = updates.pop();
			cond = Rules.THREEDGROWTH.apply(this, c);
			handleNeighbors(c, cond);
		}
	}
	
	public int getSize(){
		return size;
	}

	public void seizurePlease() {
		Cell c;
		while(true){
			for (int x = 0; x < grid[0].length; ++x ){
				for (int y = 0; y < grid[0].length; y++){
					for (int z = 0; z < grid[0].length; z++){
						c = getCell(x, y, z);

						if (c.isAlive()) {
							c.setAdditionalColor(getColor.randomColor());	
						}
//						rules(c);
					}
				}
			}
		}
	}
}