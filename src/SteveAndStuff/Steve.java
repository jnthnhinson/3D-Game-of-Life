package src.SteveAndStuff;
import com.threed.jpct.Camera;

public class Steve extends Camera{
	float xAngle;
	int dx;
	int dy;

	public Steve(){
		this.xAngle = 0;
		this.dx = 0;
		this.dy = 0;
	}
	

	//int dx = <mouse delta x>;
	//int dy = <mouse delta y>;

	private void updateCamera() {
		if (dx!=0) {
			rotateAxis(getYAxis(), dx / 500f);
		}

		if ((dy > 0 && xAngle < Math.PI / 4.2) || (dy < 0 && xAngle > -Math.PI / 4.2)) {
			float t=dy/500f;
			rotateX(t);
			xAngle += t;
		}
	}
}