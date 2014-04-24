package src.graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import com.threed.jpct.*;
import com.threed.jpct.util.Light;

import javax.swing.*;

import src.data_structures.Cell;
import src.data_structures.CellManager;

@SuppressWarnings("serial")
public class CameraTest extends JFrame{
	private World world;
	private FrameBuffer buffer;
	private MouseHandler mh;
	private KeyHandler kh;
	private Camera camera;
	private Object3D selectedObject;
	private CellManager cellManager;
	private boolean showCursor = false;

	public CameraTest() throws Exception {
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		world = new World();
		world.setAmbientLight(150, 150, 150);

		cellManager = new CellManager(10, world);
		
		camera = world.getCamera();
		camera.moveCamera(Camera.CAMERA_MOVEOUT, 100);

		Light light = new Light(world);
		light.setPosition(new SimpleVector(0, -80, 0));
		light.setIntensity(10, 10, 10);

		world.buildAllObjects();
		
		camera.lookAt(cellManager.getCell(0, 0, 0).getTransformedCenter());

		initListeners();
		hideCursor();
	}
	
	private void initListeners(){
		kh = new KeyHandler(this);
		mh = new MouseHandler(this);
		
		this.addKeyListener(kh);
		this.addMouseListener(mh);
		this.addMouseMotionListener(mh);
	}

	//test methods
	public void addBlockLeft(){
		Cell cube = new Cell();
		Matrix m = this.selectedObject.getTranslationMatrix().cloneMatrix();
		m.translate(-10, 0, -10);
		cube.setTranslationMatrix(m);
		world.addObject(cube);
	}
	
	public void addBlockBelow(){
		Cell cube = new Cell();
		Matrix m = this.selectedObject.getTranslationMatrix().cloneMatrix();
		m.translate(0, -10, 0);
		cube.setTranslationMatrix(m);
		world.addObject(cube);		
	}

	
	public void toggleCursor(){
		if(this.showCursor == false){
			showCursor = true;
			showCursor();		
		}
		else if(this.showCursor == true){
			showCursor = false;
			hideCursor();
		}
		mh.toggleListener();
	}
	public void hideCursor(){
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		this.getContentPane().setCursor(blankCursor);
	}
	public void showCursor(){
		this.getContentPane().setCursor(Cursor.getDefaultCursor());
	}

	private void loop() throws Exception {
		buffer = new FrameBuffer(1000, 800, FrameBuffer.SAMPLINGMODE_HARDWARE_ONLY);
		buffer.optimizeBufferAccess();
		System.out.println("problem after Gl \n\n\n");
		
		while (isShowing()) {
			buffer.clear(java.awt.Color.BLACK);
			world.renderScene(buffer);
			world.draw(buffer);
			buffer.update();
			buffer.display(getGraphics());
			Thread.sleep(10);
		}
		buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
		buffer.dispose();
		dispose();
		System.exit(0);
	}

	public void moveCamera(String dir) {
		if(dir == "L"){camera.moveCamera(Camera.CAMERA_MOVELEFT, 1);}
		else if(dir == "R"){camera.moveCamera(Camera.CAMERA_MOVERIGHT, 1);}
		else if(dir == "F"){camera.moveCamera(Camera.CAMERA_MOVEIN, 1);}
		else if(dir == "B"){camera.moveCamera(Camera.CAMERA_MOVEOUT, 1);}
		else if(dir == "U"){camera.moveCamera(Camera.CAMERA_MOVEUP, 1);}
		else if(dir == "D"){camera.moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
	}
	
	public void tiltCamera(String dir){
		if(dir == "L"){
			camera.rotateCameraZ((float)-.03);
		} else if(dir == "R"){
			camera.rotateCameraZ((float).03);
		}
	}

	public void rotateView(int dx, int dy){	
		if (dx !=0 || dy != 0){
			SimpleVector line =new SimpleVector(dx, 0, dy);
			Matrix m = line.normalize().getRotationMatrix();

			m.rotateAxis(m.getXAxis(), (float) -Math.PI/2f);
			camera.rotateAxis(m.invert3x3().getXAxis(), line.length() / 500f);
		}
	}

    public void selectPointedObject(){
    	SimpleVector ray = Interact2D.reproject2D3DWS(camera, buffer, this.getWidth()/2, this.getHeight()/2).normalize(); 
    	Object[] res = world.calcMinDistanceAndObject3D(camera.getPosition(), ray, 10000F);
    	if (res==null || res[1] == null || res[0] == (Object)Object3D.RAY_MISSES_BOX) { 
    		System.out.println("Did not click an object");
    		selectedObject = null;
    	}
    	selectedObject = (Object3D)res[1];
    	selectedObject.setAdditionalColor(Color.blue);
    }
	
	public static void main(String[] args) throws Exception {
		CameraTest H = new CameraTest();
		H.loop();
	}
}