package src.data_structures;

public enum Rules {
	
	THREEDGROWTH{

		@Override
		public boolean apply(CellManager cm, Cell c) {
			if (cm.numNeighbors(c) <= 1 && c.isAlive()) {
				c.turnOff();
			}
			else if (cm.numNeighbors(c) >= 5 && cm.numNeighbors(c) < 8 && c.getStage() == null) {
				c.turnOn();
			}
			else if (cm.numNeighbors(c) >= 8 && c.isAlive()) {
				c.turnOff();
			}
//			else if (cm.numFlatNeighbors(c) > 4) {
//				c.turnOff();
//				return true;
//			} 
			return false;
		}
		
	};
	
	public abstract boolean apply(CellManager cm, Cell c);

}
