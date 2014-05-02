package src.graphics;
import java.util.Timer;
import java.util.TimerTask;

import src.data_structures.Cell;
import src.data_structures.CellManager;
import src.data_structures.CellSize;
import src.data_structures.EdgeLength;

import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.Light;

public class WorldBuilder {
	private World world;
	private CellManager cellManager;
	private Perspective perspective;
	private Timer time;
	private int cellSize;

	public WorldBuilder() throws Exception {
		this.initWorld();
		
		cellManager = new CellManager(EdgeLength.SMALL.length(), world);
		cellSize = cellManager.getCellSize();
//		runRandom();

		perspective = new Perspective(this, world, cellManager);
		initLight();

		perspective.loop();
	}
	
	private void initWorld(){
		world = new World();
		world.setAmbientLight(150, 150, 150);
		world.setClippingPlanes(0, 5000);
		
	}

	private void initLight(){
		Light light = new Light(world);
		light.setPosition(new SimpleVector(0, -80, 0));
		light.setIntensity(40, 25, 22);		
	}
	
	public void runParty() {
		time = new Timer();
		time.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				cellManager.seizurePlease();
			}
		},100,9000);
	}
	public void stopParty(){
		time = new Timer();
	}
	
	public void addBlockRight(Object3D selectedObject){
		Cell cube = new Cell(cellSize);
		if (selectedObject != null) {
			Matrix m = selectedObject.getTranslationMatrix();
			if (m != null) {
				m = m.cloneMatrix();
				m.translate(10, 0, 10);
				cube.setTranslationMatrix(m);
				world.addObject(cube);
			}
		}
	}

	public void addBlockLeft(Object3D selectedObject){
		Cell cube = new Cell(cellSize);
		if (selectedObject != null) {
			Matrix m = selectedObject.getTranslationMatrix();
			if (m != null) {
				m = m.cloneMatrix();
				m.translate(-10, 0, -10);
				cube.setTranslationMatrix(m);
				world.addObject(cube);
			}
		}
	}

	public void addBlockAbove(Object3D selectedObject){
		Cell cube = new Cell(cellSize);
		if (selectedObject != null) {
			Matrix m = selectedObject.getTranslationMatrix();
			if (m != null) {
				m = m.cloneMatrix();
				m.translate(0, 10, 0);
				cube.setTranslationMatrix(m);
				world.addObject(cube);
			}
		}
	}

	public void addBlockBelow(Object3D selectedObject){
		Cell cube = new Cell(cellSize);
		if (selectedObject != null) {
			Matrix m = selectedObject.getTranslationMatrix();
			if (m != null) {
				m = m.cloneMatrix();
				m.translate(0, -10, 0);
				cube.setTranslationMatrix(m);
				world.addObject(cube);
			}
		}
	}
	
	public void update(){
		cellManager.update();
	}
	
	public void toggle(Cell c){
		cellManager.toggle(c);
	}

	public static void main(String[] args) throws Exception {
		new WorldBuilder();
	}
}
