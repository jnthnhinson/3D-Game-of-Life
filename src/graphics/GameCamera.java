package src.graphics;

import com.threed.jpct.*;

@SuppressWarnings("serial")
public class GameCamera extends Camera {
	private final static float COLLISION_SPHERE_RADIUS = 8f;
	private final static float PLAYER_HEIGHT = 30f;
	private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float MOVE_SPEED = 2.5f;
	private final static float TURN_SPEED = 0.02f;
	private String id = "";
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((playerDirection == null) ? 0 : playerDirection.hashCode());
		result = prime * result
				+ ((tempVector == null) ? 0 : tempVector.hashCode());
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameCamera other = (GameCamera) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (playerDirection == null) {
			if (other.playerDirection != null)
				return false;
		} else if (!playerDirection.equals(other.playerDirection))
			return false;
		if (tempVector == null) {
			if (other.tempVector != null)
				return false;
		} else if (!tempVector.equals(other.tempVector))
			return false;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		return true;
	}

	protected World world;
	private Matrix playerDirection;
	private SimpleVector tempVector=new SimpleVector();
	
	
	public GameCamera(World world) {
		this.world = world;
		this.playerDirection = new Matrix();
	}
	
	public void tiltCamera(String dir){}

	public void rotateView(int dx, int dy){	
		if (dx !=0 || dy != 0){
			SimpleVector line =new SimpleVector(dx, 0, dy);
			Matrix m = line.normalize().getRotationMatrix();

			m.rotateAxis(m.getXAxis(), (float) -Math.PI/2f);
			rotateAxis(m.invert3x3().getXAxis(), line.length() / 500f);
		}
	}
	
	
	public void performMovement() {}
	public void updateStatus(String status) {}

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
