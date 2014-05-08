package src.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import listeners.KeyHandler;
import listeners.MouseHandler;

import org.lwjgl.opengl.Display;

import src.data_structures.Cell;
import src.data_structures.CellManager;
import cameras.GameCamera;
import cameras.GodCamera;
import cameras.SteveCamera;

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
	private GameCamera steveCamera, godCamera, camera;
	private boolean showCursor, paused;
	private MouseHandler mh;
	private KeyHandler kh;
	private FrameBuffer buffer;
	private World world;
	private Canvas canvas;
	private Cell selectedObject;
	private long startTime, delay;
	private final int windowx = 1280, windowy = 720;


	public Perspective(WorldBuilder wb, World world, CellManager cellManager) {
		setSize(windowx, windowy);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("The Game of Life - 3D");
		getFocusableWindowState();
		
		this.wb = wb;
		this.world = world;
		
		startTime = System.currentTimeMillis();
		delay = 2000;
		
		initBuffer();
		initCanvas();
		initPauseMenu();
		initCamera();
		initListeners();
		hideCursor();

		try {
			loop();
		} catch (Exception e) {
			e.printStackTrace();
			// What problems could occur and how should we communicate this?
		}
	}
	
	private void initBuffer(){
		buffer = new FrameBuffer(windowx, windowy, FrameBuffer.SAMPLINGMODE_HARDWARE_ONLY																																																																																																																																																																											);
		buffer.optimizeBufferAccess();
		buffer.disableRenderer(IRenderer.RENDERER_SOFTWARE);
		buffer.enableRenderer(IRenderer.RENDERER_OPENGL);
	}

	private void initCanvas(){
		canvas = new Canvas();
		canvas.setVisible(true);
		canvas = buffer.enableGLCanvasRenderer();
		this.add(canvas);
	}
	
	private void initPauseMenu(){
		this.pauseMenu = new PauseMenu(this);
		this.pauseMenu.setVisible(true);
		this.paused = false;
		this.add(pauseMenu);
	}
	
	private void initCamera() {
		this.steveCamera = new SteveCamera(world);
		this.godCamera = new GodCamera(world);

		this.godCamera.setPosition(100, -75, 100);
		this.steveCamera.setPosition(100, -75, 100);
		
		this.camera = godCamera;
		world.setCameraTo(camera);
	}
	
	private void initListeners(){
		kh = new KeyHandler(this, camera);
		mh = new MouseHandler(this, camera);

		canvas.addKeyListener(kh);
		canvas.addMouseListener(mh);
		canvas.addMouseMotionListener(mh);
		this.addKeyListener(kh);
		this.addMouseListener(mh);
		this.addMouseMotionListener(mh);
	}
	
	protected void loop() throws Exception {
		while (isShowing()) {
			if(!paused){
				buffer.clear(java.awt.Color.BLACK);
				world.renderScene(buffer);
				world.draw(buffer);

				camera.performMovement();
				buffer.update();
				buffer.display(canvas.getGraphics());
				canvas.repaint();
				
				if(System.currentTimeMillis() - startTime >= delay){
					startTime = System.currentTimeMillis();
					wb.update();
				}
			}
			this.requestFocus();
		}
		buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
		buffer.dispose();
		dispose();
		System.exit(0);
	}

	public void changeSpeed(int speed){
		this.delay = speed;
	}
	
	public void toggleCamera(){
		if(camera.equals(godCamera)){
			world.setCameraTo(steveCamera);
			camera = steveCamera;
		} else {
			world.setCameraTo(godCamera);
			camera = godCamera;
		}
		initListeners();
	}
	
	public void togglePause(){
		toggleCursor();
		paused = !paused;
		canvas.setVisible(!canvas.isVisible());
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
		if (selectedObject != null) {wb.toggle(selectedObject);}
	}
	public Object3D getSelectedObject() {return selectedObject;}

}
