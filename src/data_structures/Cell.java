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
	private int pop, stage;
	private Color col;
	private Stage s;
	private int[] coordinates = {0, 0, 0};

	public Cell(int size) {
		super(Primitives.getCube(size), false);
		this.initSelf();
	}
	
	private void initSelf(){
		Random rand = new Random();
		stage = 0;
		this.setSpecularLighting(true);
		this.setTexture("RESOURCES.textures.dirt.png");
		this.setCollisionMode(Object3D.COLLISION_CHECK_OTHERS);
		this.setColor(new Color(rand.nextInt(256/2), rand.nextInt(256/2), rand.nextInt(256/2)));
		this.rotateY((float)(Math.PI*45)/180);
		this.compile();
		this.setVisibility(false);
	}
	
	private void setColor(Color col){
		this.col = col;
		this.setAdditionalColor(col);
	}
	
	
	public void incPop(boolean pol){
		if(!pol && pop > 0){pop--;
		}else{pop++;}
//		printCoordinates();
//		System.out.println(": " + pop);
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
		System.out.print(coordinates[0] + " : " + coordinates[1] + " : " + coordinates[2]);
	}
	
	public boolean isAlive(){
		if(s == Stage.LIVING){
			return true;
		}
		return false;
	}
	
	public boolean getAlive(){
		return isAlive;
	}
	
	public void setAlive(boolean bool){
		isAlive = bool;
	}
	
	public void turnOn(){
		s = Stage.SPAWNING;
		s.apply(this);
	}
	
	public void turnOff(){
		s = Stage.DYING1;
		s.apply(this);
	}
	
	public void alive(boolean bool){
		
	}
	
	public Stage getStage(){
		return s;
	}
	
	public int[] getCoordinates(){
		return coordinates;
	}
	
	public void nextStage(){
		if(s != null){
			s = s.nextStage(this);
			if(s != null){s.apply(this);}
		}
		
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