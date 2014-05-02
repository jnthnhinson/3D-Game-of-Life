package src.data_structures;

public enum Rules {
	
	THREEDGROWTH{

		@Override
		public boolean apply(CellManager cm, Cell c) {
			if (c.numNeighbors() <= 1 && c.isAlive()) {
				c.setAlive(false);
				return true;
			}
			else if (c.numNeighbors() >= 5 && c.numNeighbors() < 8) {
				c.setAlive(true);
				return true;
			}
			else if (c.numNeighbors() >= 8) {
				c.setAlive(false);
				return true;
			}
//			else if (cm.numFlatNeighbors(c) > 4) {
//				c.setAlive(false);
//				return true;
//			} 
			return false;
		}
		
	};
	
	public abstract boolean apply(CellManager cm, Cell c);

}
