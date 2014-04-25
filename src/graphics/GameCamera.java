package src.graphics;

import com.threed.jpct.*;

@SuppressWarnings("serial")
public class GameCamera extends Camera {
	private final static float COLLISION_SPHERE_RADIUS = 8f;
	private final static float PLAYER_HEIGHT = 30f;
	private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float MOVE_SPEED = 2.5f;
	private final static float TURN_SPEED = 0.06f;
	
	protected World world;
	private Matrix playerDirection;
	private SimpleVector tempVector=new SimpleVector();
	
	
	public GameCamera(World world) {
		this.world = world;
		this.playerDirection = new Matrix();
	}
	
	public void tiltCamera(String dir){	}

	public void rotateView(int dx, int dy){	
		if (dx !=0 || dy != 0){
			SimpleVector line =new SimpleVector(dx, 0, dy);
			Matrix m = line.normalize().getRotationMatrix();

			m.rotateAxis(m.getXAxis(), (float) -Math.PI/2f);
			rotateAxis(m.invert3x3().getXAxis(), line.length() / 500f);
		}
	}
	
	
	public void performMovement() {printDoom();}
	public void updateStatus(String death) {printDoom();}
	public void printDoom() {System.out.println("If this prints, then something has gone horribly, horribly wrong.");}

	protected void walk(boolean isForward) {
		moveCamera(new SimpleVector(0,1,0), PLAYER_HEIGHT/2f);
		tempVector = playerDirection.getZAxis();
		if (!isForward) {tempVector.scalarMul(-1f);}
		world.checkCameraCollisionEllipsoid(tempVector, ELLIPSOID_RADIUS, MOVE_SPEED, 5);
	}

	protected void tilt(boolean isUp) {
		float turnSpeed = TURN_SPEED;
		if (!isUp) {turnSpeed *= -1;}
		rotateX(turnSpeed);
	}

	protected void turn(boolean isRight) {
		float turnSpeed = TURN_SPEED;
		if (!isRight) {turnSpeed *= -1;}
		rotateAxis(getBack().getYAxis(), turnSpeed);
		playerDirection.rotateY(turnSpeed);
	}

	protected void strafe(boolean isRight) {
		moveCamera(new SimpleVector(0,1,0), PLAYER_HEIGHT/2f);
		tempVector=playerDirection.getXAxis();
		if (!isRight) {tempVector.scalarMul(-1f);}
		world.checkCameraCollisionEllipsoid(tempVector, ELLIPSOID_RADIUS, MOVE_SPEED, 5);
	}
	
}
