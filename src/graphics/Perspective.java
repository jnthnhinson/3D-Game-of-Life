package src.graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import src.data_structures.CellManager;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.IRenderer;
import com.threed.jpct.Interact2D;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

@SuppressWarnings("serial")
public class Perspective extends JFrame{
	private WorldBuilder wb;
	private GameCamera camera;
	private boolean showCursor;
	private MouseHandler mh;
	private KeyHandler kh;
	private FrameBuffer buffer;
	private World world;
	private Object3D selectedObject;


	public Perspective(WorldBuilder wb, World world, CellManager cellManager, boolean isSteveMode) {
		setSize(1000, 800);
		setVisible(true);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.wb = wb;
		this.world = world;

		initCamera(isSteveMode, cellManager);
		initListeners();
		hideCursor();

		try {
			loop();
		} catch (Exception e) {
			e.printStackTrace();
			// What problems could occur and how should we communicate this?
		}
	}

	private void initCamera(boolean isSteveMode, CellManager cellManager) {
		if (isSteveMode) 	{this.camera = new SteveCamera(world);}
		else 			{this.camera = new GodCamera(world);}
		
		world.setCameraTo(camera);
		camera.moveCamera(Camera.CAMERA_MOVEOUT, 100);
		camera.lookAt(cellManager.getCell(0,0,0).getTransformedCenter());
	}
	
	private void initListeners(){
		kh = new KeyHandler(this, camera);
		mh = new MouseHandler(this, wb, camera);

		this.addKeyListener(kh);
		this.addMouseListener(mh);
		this.addMouseMotionListener(mh);
	}
	
	protected void loop() throws Exception {
		buffer = new FrameBuffer(1000, 800, FrameBuffer.SAMPLINGMODE_HARDWARE_ONLY);
		buffer.optimizeBufferAccess();
		System.out.println("problem after Gl \n\n\n");

		while (isShowing()) {
			buffer.clear(java.awt.Color.BLACK);
			world.renderScene(buffer);
			world.draw(buffer);
			
			camera.performMovement();
			buffer.update();
			buffer.display(getGraphics());
			Thread.sleep(10);
		}
		buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
		buffer.dispose();
		dispose();
		System.exit(0);
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
		getContentPane().setCursor(blankCursor);
	}
	public void showCursor(){
		getContentPane().setCursor(Cursor.getDefaultCursor());
	}
	
	public int selectPointedObject(){
		SimpleVector ray = Interact2D.reproject2D3DWS(camera, buffer, this.getWidth()/2, this.getHeight()/2).normalize(); 
		Object[] res = world.calcMinDistanceAndObject3D(camera.getPosition(), ray, 10000F);
		if (res==null || res[1] == null || res[0] == (Object)Object3D.RAY_MISSES_BOX) { 
			System.out.println("Did not click an object");
			selectedObject = null;
			return -1;
		}
		Object3D obj = (Object3D)res[1];
		obj.setAdditionalColor(Color.blue);
		System.out.println("SELECTED OBJECT: " + obj.getName());
		selectedObject = obj;		
		return obj.getID();
	}
	public Object3D getSelectedObject() {return selectedObject;}

}