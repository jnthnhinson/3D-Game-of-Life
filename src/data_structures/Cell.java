package src.data_structures;

import java.awt.Color;
import java.util.Random;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;

/*Currently the only constructor is a copy constructor
 *Create one object at instantiation and just use the copy constructor to clone it as many times as needed?
 *I'm not sure how the coordinate system works in JPCT
 */
@SuppressWarnings("serial")
public class Cell extends Object3D{
	private boolean isAlive;
	private int pop;
	private int[] coordinates = {0, 0, 0};

	public Cell(int size) {
		super(Primitives.getCube(size), true);
		isAlive = false;
		this.initSelf();
	}
	
	private void initSelf(){
		Random rand = new Random();
		this.setSpecularLighting(true);
		this.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
		this.setTransparency(0);
		this.setAdditionalColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
		this.rotateY((float)(Math.PI*45)/180);
		this.compile();
	}
	
	
	public void incPop(boolean pol){
		if(pol){pop++;
		}else{pop--;}
	}
	
	public void setCoordinates(int x, int y, int z){
		coordinates[0] = x;
		coordinates[1] = y;
		coordinates[2] = z;
	}
	
	public void printCoordinates(){
		System.out.println(coordinates[0] + " : " + coordinates[1] + " : " + coordinates[2]);
	}
	
	public void birthCell(Cell c){
		c.isAlive = true;
	}
	
	public void murderCell(Cell c) {
		c.isAlive = false;
	}
	
	public boolean isDead(Cell c){
		return c.isAlive;
	}
	
	public boolean isAlive(Cell c) {
		if (c.isAlive = true){
			return true;
		}
		else {return false;}
	}
	
	public int[] getCoordinates(){
		return coordinates;
	}
}