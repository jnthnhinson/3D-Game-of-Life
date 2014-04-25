package src.graphics;

import com.threed.jpct.Camera;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

@SuppressWarnings("serial")
public class GodCamera extends GameCamera{
	//private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float PLAYER_HEIGHT = 30f;	

	public GodCamera(World world) {
		super(world);
	}

	public void updateStatus(String dir) {
		if(dir == "L"){this.moveCamera(Camera.CAMERA_MOVELEFT, 1);}
		else if(dir == "R"){this.moveCamera(Camera.CAMERA_MOVERIGHT, 1);}
		else if(dir == "F"){this.moveCamera(Camera.CAMERA_MOVEIN, 1);}
		else if(dir == "B"){this.moveCamera(Camera.CAMERA_MOVEOUT, 1);}
		else if(dir == "U"){this.moveCamera(Camera.CAMERA_MOVEUP, 1);}
		else if(dir == "D"){this.moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
		else if(dir == "TL"){tiltCamera("TL");}
		else if(dir == "TR"){tiltCamera("TR");}
	}
	
	public void tiltCamera(String dir){
		if(dir == "TL"){
			rotateCameraZ((float)-.03);
		} else if(dir == "TR"){
			rotateCameraZ((float).03);
		}
	}
}
