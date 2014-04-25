package src.data_structures;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.threed.jpct.World;

public class DataTest {

	CellManager testy;
	World world;
	
	@Before
	public void setUp() throws Exception {
		world = new World();
		testy = new CellManager(EdgeLength.TEST.length(), world);
	}

	@Test
	public void test() {
		Cell cell = testy.getCell(5, 5, 5);
		assertTrue(cell != null);
		
		
	}

}
