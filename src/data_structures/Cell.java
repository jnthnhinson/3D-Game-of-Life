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
		setAlive(false);
		this.initSelf();
	}
	
	private void initSelf(){
		Random rand = new Random();
		this.setSpecularLighting(true);
		this.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
//		this.setTransparency(0);
		this.setAdditionalColor(new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
		this.rotateY((float)(Math.PI*45)/180);
		this.compile();
	}
	
	
	public void incPop(boolean pol){
		if(pol){pop++;
		}else{pop--;}
	}
	
	public int numNeighbors(){
		return pop;
	}
	
	public void setCoordinates(int x, int y, int z){
		coordinates[0] = x;
		coordinates[1] = y;
		coordinates[2] = z;
	}
	
	public void printCoordinates(){
		System.out.println(coordinates[0] + " : " + coordinates[1] + " : " + coordinates[2]);
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public void setAlive(boolean bool){
		if(bool != isAlive){
			isAlive = bool;
			setVisibility(bool);
		}
	}
	
	public int[] getCoordinates(){
		return coordinates;
	}

	public boolean toggle() {
		if(isAlive){
			isAlive = !isAlive;
			setVisibility(false);
		} else {
			isAlive = !isAlive;
			setVisibility(true);
		}
		return isAlive;
	}
}