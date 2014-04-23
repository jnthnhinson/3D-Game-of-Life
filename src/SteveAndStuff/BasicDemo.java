// Code modified from jpct's fps example.


package src.SteveAndStuff;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.io.File;

import com.threed.jpct.*;
import com.threed.jpct.util.KeyMapper;
import com.threed.jpct.util.KeyState;


public class BasicDemo {
	private final static SimpleVector STARTING_POS = new SimpleVector(800, -120, -400);
	private final static float COLLISION_SPHERE_RADIUS = 8f;
	private final static float PLAYER_HEIGHT = 30f;
	private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float GRAVITY = 4f;
	private final static float MOVE_SPEED = 2.5f;
	private final static float TURN_SPEED = 0.06f;
	private final static int SWITCH_RENDERER = 35;

	private boolean isIdle;
	private int switchMode, totalFps, fps, lastFps;
	private World world;
	private TextureManager textureManager;
	private Camera camera;
	private KeyMapper keyMapper;

	boolean fullscreen = false;
	GraphicsDevice device;
	Frame frame;
	BufferStrategy bufferStrategy;
	int titleBarHeight;
	int leftBorderWidth;
	Graphics gFrame;
	int width = 640;
	int height = 480;
	FrameBuffer buffer;
	boolean exit = false;
	int pps;
	int lastPps;
	boolean wireframe = false;

	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean forward = false;
	private boolean back = false;
	private boolean jumping = false;
	private boolean strafeLeft = false;
	private boolean strafeRight = false;

	private boolean openGL = false;
	private Texture numbers = null;

	private SimpleVector tempVector=new SimpleVector();
	private Matrix playerDirection=new Matrix();

	public static void main(String[] args) {
		new BasicDemo();
	}

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
		World.setDefaultThread(Thread.currentThread());

		buffer = new FrameBuffer(width, height, FrameBuffer.SAMPLINGMODE_NORMAL);
		buffer.enableRenderer(IRenderer.RENDERER_SOFTWARE);
		buffer.setBoundingBoxMode(FrameBuffer.BOUNDINGBOX_NOT_USED);

		buffer.optimizeBufferAccess();

		Timer timer = new Timer(25);
		timer.start();

		Timer fpsTimer = new Timer(1000);
		fpsTimer.start();

		long timerTicks = 0;

		while (!exit) {
			if (!isIdle) {
				long ticks = timer.getElapsedTicks();
				timerTicks += ticks;

				for (int i = 0; i<ticks; i++) {performMovement();}
				poll();
				buffer.clear();

				world.renderScene(buffer);

				if (!wireframe) {world.draw(buffer);}
				else {world.drawWireframe(buffer, Color.white);}

				buffer.update();
				display();

				fps++;
				pps += world.getVisibilityList().getSize();

				if (fpsTimer.getElapsedTicks()>0) {
					totalFps = (fps-lastFps);
					lastFps = fps;
					lastPps = pps;
					pps = 0;
				}

				Thread.yield();

			} else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
			}
		}

		buffer.dispose();
		if (!openGL && fullscreen) {
			device.setFullScreenWindow(null);
		}
		System.exit(0);
	}

	private void initializeFrame() {

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


		world.addLight(new SimpleVector(820, -150, -400), 5, 20, 15);
		world.addLight(new SimpleVector(850, -130, -580), 20, 18, 2);
		world.addLight(new SimpleVector(850, -130, -760), 15, 10, 15);
		world.addLight(new SimpleVector(1060, -170, -910), 20, 0, 0);
		world.addLight(new SimpleVector(760, -200, -990), 15, 10, 20);
		world.addLight(new SimpleVector(850, -230, -780), 0, 15, 25);
		world.addLight(new SimpleVector(600, -230, -770), 20, 25, 0);
		world.addLight(new SimpleVector(405, -230, -610), 18, 20, 25);
		world.addLight(new SimpleVector(340, -150, -370), 15, 20, 25);
		world.addLight(new SimpleVector(650, -170, -200), 15, 0, 0);
		world.addLight(new SimpleVector(870, -230, -190), 15, 20, 20);
		world.addLight(new SimpleVector(540, -190, -180), 15, 15, 15);

		world.setFogging(World.FOGGING_ENABLED);
		world.setFogParameters(500, 0, 0, 0);
		Config.farPlane = 500;
	}

	private class WindowEvents extends WindowAdapter {
		public void windowIconified(WindowEvent e) {isIdle=true;}
		public void windowDeiconified(WindowEvent e) {isIdle=false;}
	}

	private class Timer {
		private long ticks=0;
		private long granularity=0;

		public Timer(int granularity) {this.granularity=granularity;}

		public void start() {ticks=System.currentTimeMillis();}

		public void reset() {start();}

		public long getElapsedTicks() {
			long cur=System.currentTimeMillis();
			long l=cur-ticks;

			if (l>=granularity) {
				ticks=cur-(l%granularity);
				return l/granularity;
			}
			return 0;
		}
	}

	private void performMovement() {
		performGravity();
		performOtherMovement();
	}

	private void performGravity() {
		SimpleVector camPos = camera.getPosition();
		camPos.add(new SimpleVector(0, PLAYER_HEIGHT/2f, 0));
		//SimpleVector dir = new SimpleVector(0, GRAVITY, 0);
		SimpleVector dir;
		if (jumping) {dir = new SimpleVector(0, -GRAVITY, 0);}
		else {dir = new SimpleVector(0, GRAVITY, 0);}
		dir = world.checkCollisionEllipsoid(camPos, dir, ELLIPSOID_RADIUS, 1);
		camPos.add(new SimpleVector(0, -PLAYER_HEIGHT/2f, 0));
		dir.x = 0;
		dir.z = 0;
		camPos.add(dir);
		camera.setPosition(camPos);
	}

	private void performOtherMovement() {
		boolean cameraChanged = false;

		if (forward) {walk(true);cameraChanged = true;}
		else if (back) {walk(false);cameraChanged = true;}
		if (left) {turn(false);}
		if (right) {turn(true);}
		if (up) {tilt(true);}
		if (down) {tilt(false);}
		if (strafeLeft) {strafe(false); cameraChanged = true;}
		if (strafeRight) {strafe(true); cameraChanged = true;}
		if (cameraChanged) {camera.moveCamera(new SimpleVector(0, -1, 0), PLAYER_HEIGHT/2f);}
	}

	private void walk(boolean isForward) {
		camera.moveCamera(new SimpleVector(0,1,0), PLAYER_HEIGHT/2f);
		tempVector = playerDirection.getZAxis();
		if (!isForward) {tempVector.scalarMul(-1f);}
		world.checkCameraCollisionEllipsoid(tempVector, ELLIPSOID_RADIUS, MOVE_SPEED, 5);
	}

	private void tilt(boolean isUp) {
		float turnSpeed = TURN_SPEED;
		if (!isUp) {turnSpeed *= -1;}
		camera.rotateX(turnSpeed);
	}

	private void turn(boolean isRight) {
		float turnSpeed = TURN_SPEED;
		if (!isRight) {turnSpeed *= -1;}
		camera.rotateAxis(camera.getBack().getYAxis(), turnSpeed);
		playerDirection.rotateY(turnSpeed);
	}

	private void strafe(boolean isRight) {
		camera.moveCamera(new SimpleVector(0,1,0), PLAYER_HEIGHT/2f);
		tempVector=playerDirection.getXAxis();
		if (!isRight) {tempVector.scalarMul(-1f);}
		world.checkCameraCollisionEllipsoid(tempVector, ELLIPSOID_RADIUS, MOVE_SPEED, 5);
	}

	private void display() {
		blitNumber((int) totalFps, 5, 2);
		blitNumber((int) lastPps, 5, 12);

		if (!openGL) {
			if (!fullscreen) {
				buffer.display(gFrame, leftBorderWidth, titleBarHeight);
			} else {
				Graphics g = bufferStrategy.getDrawGraphics();
				g.drawImage(buffer.getOutputBuffer(), 0, 0, null);
				bufferStrategy.show();
				g.dispose();
			}
		} else {
			buffer.displayGLOnly();
		}
	}

	private void blitNumber(int number, int x, int y) {
		if (numbers !=null) {
			String sNum = Integer.toString(number);
			for (int i = 0; i<sNum.length(); i++) {
				char cNum = sNum.charAt(i);
				int iNum = cNum-48;
				buffer.blit(numbers, iNum*5, 0, x, y, 5, 9, FrameBuffer.TRANSPARENT_BLITTING);
				x += 5;
			}
		}
	}

	private void poll() {
		KeyState state = null;
		do {
			state = keyMapper.poll();
			if (state != KeyState.NONE) {keyAffected(state);}
		} while (state != KeyState.NONE);
	}

	private void keyAffected(KeyState state) {
		int code = state.getKeyCode();
		boolean event = state.getState();
		switch (code) {
		case (KeyEvent.VK_ESCAPE): {exit = event;break;}
		case (KeyEvent.VK_A): {strafeLeft = event;break;}
		case (KeyEvent.VK_D): {strafeRight = event;break;}
		case (KeyEvent.VK_UP): {up = event;break;}
		case (KeyEvent.VK_DOWN): {down = event;break;}
		case (KeyEvent.VK_W): {forward = event;break;}
		case (KeyEvent.VK_S): {back = event;break;}
		case (KeyEvent.VK_SPACE): {jumping = event;break;}
		}
	}

}
