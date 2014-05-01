package src.graphics;
import java.util.Timer;
import java.util.TimerTask;

import src.data_structures.Cell;
import src.data_structures.CellManager;
import src.data_structures.CellSize;

import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.Light;

public class WorldBuilder {
	private World world;
	private CellManager cellManager;
	private Perspective view;
	private Timer time;
	private boolean steveMode;

	public WorldBuilder() throws Exception {
		world = new World();
		world.setAmbientLight(150, 150, 150);
		world.setClippingPlanes(0, 5000);

		cellManager = new CellManager(10, world);
//		runRandom();

		steveMode = false;
		view = new Perspective(this, world, cellManager, steveMode);

		Light light = new Light(world);
		light.setPosition(new SimpleVector(0, -80, 0));
		light.setIntensity(40, 25, 22);

		view.loop();
	}
	
	public void setMode(String type){
		if (type == "god"){
			steveMode = false;
		}
		else if (type == "steve") {
			steveMode = true;
		}
	}
	
	private void runRandom() {
		time = new Timer();
		time.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				cellManager.seizurePlease();
			}
		},100,9000);
	}
	
	public void addBlockRight(Object3D selectedObject){
		Cell cube = new Cell(CellSize.MEDIUM.getSize());
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
		Cell cube = new Cell(CellSize.MEDIUM.getSize());
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
		Cell cube = new Cell(CellSize.MEDIUM.getSize());
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
		Cell cube = new Cell(CellSize.MEDIUM.getSize());
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

	public static void main(String[] args) throws Exception {
		new WorldBuilder();
	}
}
