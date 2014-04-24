package src.graphics;

import com.threed.jpct.*;

@SuppressWarnings("serial")
public class GameCamera extends Camera {
	
	
	public GameCamera() {
		
	}
	
	public void moveCamera(String dir) {
		if(dir == "L"){moveCamera(Camera.CAMERA_MOVELEFT, 1);}
		else if(dir == "R"){moveCamera(Camera.CAMERA_MOVERIGHT, 1);}
		else if(dir == "F"){moveCamera(Camera.CAMERA_MOVEIN, 1);}
		else if(dir == "B"){moveCamera(Camera.CAMERA_MOVEOUT, 1);}
		else if(dir == "U"){moveCamera(Camera.CAMERA_MOVEUP, 1);}
		else if(dir == "D"){moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
	}

	public void tiltCamera(String dir){
		if(dir == "L"){
			rotateCameraZ((float)-.03);
		} else if(dir == "R"){
			rotateCameraZ((float).03);
		}
	}

	public void rotateView(int dx, int dy){	
		if (dx !=0 || dy != 0){
			SimpleVector line =new SimpleVector(dx, 0, dy);
			Matrix m = line.normalize().getRotationMatrix();

			m.rotateAxis(m.getXAxis(), (float) -Math.PI/2f);
			rotateAxis(m.invert3x3().getXAxis(), line.length() / 500f);
		}
	}
	
}
