package src.graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import src.data_structures.Cell;
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
	private PauseMenu pauseMenu;
	private GameCamera camera;
	private boolean showCursor;
	private boolean paused;
	private MouseHandler mh;
	private KeyHandler kh;
	private FrameBuffer buffer;
	private World world;
	private Cell selectedObject;
	private final int windowx = 1280;
	private final int windowy = 720;


	public Perspective(WorldBuilder wb, World world, CellManager cellManager, boolean isSteveMode) {
		setSize(windowx, windowy);
		setVisible(true);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getFocusableWindowState();
		
		this.wb = wb;
		this.world = world;
		this.pauseMenu = new PauseMenu(this, wb);
		this.add(pauseMenu);
		this.pauseMenu.setVisible(false);
		this.paused = false;

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
		buffer = new FrameBuffer(windowx, windowy, FrameBuffer.SAMPLINGMODE_NORMAL																																																																																																																																																																											);
		buffer.optimizeBufferAccess();
		buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
		buffer.enableRenderer(IRenderer.RENDERER_OPENGL);
//		this.setVisible(false);
		
		while (!org.lwjgl.opengl.Display.isCloseRequested()) {
			if(!paused){
				buffer.clear(java.awt.Color.BLACK);
				world.renderScene(buffer);
				world.draw(buffer);

				camera.performMovement();
				buffer.update();
				this.paint(buffer.getOutputBuffer());
//				buffer.displayGLOnly();
//				buffer.display(getGraphics());
//				Thread.sleep(10);
			}
		}
		buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
		buffer.dispose();
		dispose();
		System.exit(0);
	}

	
	public void paint(Image image){
		
	}

	public void togglePause(){
		toggleCursor();
		if(paused == true){
			paused = false;
			pauseMenu.setVisible(false);
		} else {
			paused = true;
			pauseMenu.setVisible(true);
		}
	}


	public void toggleCursor(){
		System.out.println("Toggled Cursor");
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

	public void selectPointedObject(){
		SimpleVector ray = Interact2D.reproject2D3DWS(camera, buffer, this.getWidth()/2, this.getHeight()/2).normalize(); 
		Object[] res = world.calcMinDistanceAndObject3D(camera.getPosition(), ray, 10000F);
		if (res==null || res[1] == null || res[0] == (Object)Object3D.RAY_MISSES_BOX) { 
			selectedObject = null;
		}
		selectedObject = (Cell)res[1];
		if (selectedObject != null) {selectedObject.setAdditionalColor(Color.blue);}
	}
	public Object3D getSelectedObject() {return selectedObject;}

}