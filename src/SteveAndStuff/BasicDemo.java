package SteveAndStuff;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;

import com.threed.jpct.*;
import com.threed.jpct.util.KeyMapper;


public class BasicDemo {
	private final static SimpleVector STARTING_POS=new SimpleVector(800, -120, -400);
	private final static float COLLISION_SPHERE_RADIUS=8f;
	private final static float PLAYER_HEIGHT=30f;
	private final static SimpleVector ELLIPSOID_RADIUS=new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float GRAVITY=4f;
	private final static float MOVE_SPEED=2.5f;
	private final static float TURN_SPEED=0.06f;
	private final static int SWITCH_RENDERER=35;
	
	private boolean isIdle;
	private int switchMode, totalFps, fps, lastFps;
	private World world;
	private TextureManager textureManager;
	private Camera camera;
	
	private BasicDemo() {

		isIdle = false;
		switchMode = 0;
		totalFps = 0;
		fps = 0;
		lastFps = 0;

		world = new World();
		textureManager = TextureManager.getInstance();
		setLightAndFog();
		world.addObject(new Object3D(0));
		camera = world.getCamera();
		camera.setPosition(STARTING_POS);
		world.buildAllObjects();
		Config.collideOffset = 250;
		Config.tuneForOutdoor();
		initializeFrame();

		gameLoop();
	}
	
	private void gameLoop() {
		// TODO Auto-generated method stub
		
	}

	private void initializeFrame() {
		boolean fullscreen = false;
		GameDevice device;
		Frame frame;
		BufferStrategy bufferStrategy;
		int titleBarHeight;
		int leftBorderWidth;
		Graphics gFrame;
		int width = 640;
		int height = 480;
		
		if (fullscreen) {
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			device = env.getDefaultScreenDevice();
			GraphicsConfiguration gc = device.getDefaultConfiguration();
			frame = new Frame(gc);
			frame.setUndecorated(true);
			frame.setIgnoreRepaint(true);
			device.setFullScreenWindow(frame);
			
			if (device.isDisplayChangeSupported()) {device.setDisplayMode(new DisplayMode(width, height, 32, 0));}
			
			frame.createBufferStrategy(2);
			bufferStrategy = frame.getBufferStrategy();
			Graphics g = bufferStrategy.getDrawGraphics();
			bufferStrategy.show();
			g.dispose();
			
		} else {
			frame = new Frame();
			frame.setTitle("jPCT "+Config.getVersion());
			frame.pack();
			Insets insets  =  frame.getInsets();
			titleBarHeight = insets.top;
			leftBorderWidth = insets.left;
			frame.setSize(width+leftBorderWidth+insets.right, height+titleBarHeight+insets.bottom);
			frame.setResizable(false);
			frame.show();
			gFrame = frame.getGraphics();
		}

		/**
		 * The listeners are bound to the AWT frame...they are useless in OpenGL mode.
		 */
		frame.addWindowListener(new WindowEvents());
		keyMapper = new KeyMapper(frame);
	}
	
	private void setLightAndFog() {
		Config.fadeoutLight = true;
		Config.linearDiv = 100;
		Config.lightDiscardDistance = 350;
		
		world.getLights().setOverbrightLighting(Lights.OVERBRIGHT_LIGHTING_DISABLED);
		world.getLights().setRGBScale(Lights.RGB_SCALE_2X);
		world.setAmbientLight(10, 15, 15);

		world.setFogging(World.FOGGING_ENABLED);
		world.setFogParameters(500, 0, 0, 0);
		Config.farPlane = 500;
	}
	
}
