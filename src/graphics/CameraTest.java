package src.graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import com.threed.jpct.*;
import com.threed.jpct.util.Light;

import javax.swing.*;

@SuppressWarnings("serial")
public class CameraTest extends JFrame{
	private World world;
	private FrameBuffer buffer;
	private MouseHandler mh;
	private KeyHandler kh;
	private Camera camera;
	private Object3D cube1;
	private ArrayList<Object3D> cubes;
	private boolean showCursor = false;

	public CameraTest() throws Exception {
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		world = new World();
		world.setAmbientLight(150, 150, 150);

		cubes = new ArrayList<Object3D>();
		cube1 = createCube();
		
		camera = world.getCamera();
		camera.moveCamera(Camera.CAMERA_MOVEOUT, 10);

		Light light = new Light(world);
		light.setPosition(new SimpleVector(0, -80, 0));
		light.setIntensity(40, 25, 22);

		world.addObject(cube1);
		world.buildAllObjects();

		camera.lookAt(cube1.getTransformedCenter());

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

	public void addBlockRight(){
		Object3D cube = this.createCube();
		SimpleVector x = cube.getXAxis();
		SimpleVector z = cube.getZAxis();
		x.scalarMul((float)6);
		z.scalarMul((float)6);
		cube.translate(x);
		cube.translate(z);
		cubes.add(cube);
		world.addObject(cube);
	}

	public void addBlockLeft(){
		Object3D cube = this.createCube();
		SimpleVector x = cube.getXAxis();
		SimpleVector z = cube.getZAxis();
		x.scalarMul((float)-6);
		z.scalarMul((float)-6);
		cube.translate(x);
		cube.translate(z);
		cubes.add(cube);
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
		buffer = new FrameBuffer(800, 600, FrameBuffer.SAMPLINGMODE_NORMAL);
		buffer.enableRenderer(IRenderer.RENDERER_SOFTWARE);
		while (isShowing()) {
			buffer.clear(java.awt.Color.BLUE);
			world.renderScene(buffer);
			world.draw(buffer);
			buffer.update();
			buffer.display(getGraphics());
			Thread.sleep(1);
		}
		buffer.disableRenderer(IRenderer.RENDERER_OPENGL);
		buffer.dispose();
		dispose();
		System.exit(0);
	}

	public Object3D createCube(){
		Object3D cube = Primitives.getCube(4);
		cube.setAdditionalColor(Color.RED);
		cube.setSpecularLighting(true);
		return cube;
	}


	public void moveCamera(String dir) {
		if(dir == "L"){camera.moveCamera(Camera.CAMERA_MOVELEFT, 1);}
		else if(dir == "R"){camera.moveCamera(Camera.CAMERA_MOVERIGHT, 1);}
		else if(dir == "F"){camera.moveCamera(Camera.CAMERA_MOVEIN, 1);}
		else if(dir == "B"){camera.moveCamera(Camera.CAMERA_MOVEOUT, 1);}
		else if(dir == "U"){camera.moveCamera(Camera.CAMERA_MOVEUP, 1);}
		else if(dir == "D"){camera.moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
	}

	public void rotateView(int dx, int dy){	
		if (dx !=0 || dy != 0){
			SimpleVector line =new SimpleVector(dx, 0, dy);
			Matrix m = line.normalize().getRotationMatrix();

			m.rotateAxis(m.getXAxis(), (float) -Math.PI/2f);
			camera.rotateAxis(m.invert3x3().getXAxis(), line.length() / 500f);
		}
	}

	public static void main(String[] args) throws Exception {
		CameraTest H = new CameraTest();
		H.loop();
	}

}