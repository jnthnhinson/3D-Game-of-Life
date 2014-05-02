package src.data_structures;

public enum Rules {
	
	THREEDGROWTH{

		@Override
		public boolean apply(CellManager cm, Cell c) {
			if (cm.totalNeighbors(c) <= 1 && c.isAlive()) {
				c.setAlive(false);
				return true;
			}
			else if (cm.totalNeighbors(c) >= 5 && cm.totalNeighbors(c) < 8) {
				c.setAlive(true);
				return true;
			}
			else if (cm.totalNeighbors(c) >= 8) {
				c.setAlive(false);
				return true;
			}
			else if (cm.numFlatNeighbors(c) > 4) {
				c.setAlive(false);
				return true;
			} return false;
		}
		
	};
	
	public abstract boolean apply(CellManager cm, Cell c);

}
