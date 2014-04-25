package src.graphics;
import src.data_structures.Cell;
import src.data_structures.CellManager;

import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.Light;

public class WorldBuilder {
	private World world;
	private CellManager cellManager;
	private Perspective view;


	public WorldBuilder() throws Exception {
		world = new World();
		world.setAmbientLight(150, 150, 150);

		cellManager = new CellManager(10, world);

		boolean steveMode = true;
		view = new Perspective(this, world, cellManager, steveMode);

		Light light = new Light(world);
		light.setPosition(new SimpleVector(0, -80, 0));
		light.setIntensity(40, 25, 22);

		view.loop();

	}
	
	public void addBlockRight(Object3D selectedObject){
		Cell cube = new Cell();
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
		Cell cube = new Cell();
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
		Cell cube = new Cell();
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
		Cell cube = new Cell();
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