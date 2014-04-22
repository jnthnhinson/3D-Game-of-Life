package src.SteveAndStuff;
import org.lwjgl.input.Mouse;
import com.threed.jpct.FrameBuffer;

// Taken from http://www.jpct.net/wiki/index.php/Advanced_example#Key-_and_mouse_mapping

public class MouseMapper {

	private boolean hidden = false;

	private int height = 0;

	public MouseMapper(FrameBuffer buffer) {
		height = buffer.getOutputHeight();
		init();
	}

	public void hide() {
		if (!hidden) {
			Mouse.setGrabbed(true);
			hidden = true;
		}
	}

	//.....

	public int getDeltaY() {
		if (Mouse.isGrabbed()) {
			return Mouse.getDY();
		} else {
			return 0;
		}
	}

	private void init() {
		try {
			if (!Mouse.isCreated()) {
				Mouse.create();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}