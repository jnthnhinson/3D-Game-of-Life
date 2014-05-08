package src.data_structures;

public enum Rules {

	GROWTH{

		@Override
		public void apply(CellManager cm, Cell c) {
			if (cm.numNeighbors(c) <= 1 && c.isAlive()) {
				c.turnOff();
			}
			else if (cm.numNeighbors(c) >= 5 && cm.numNeighbors(c) < 8 && c.getStage() == null) {
				c.turnOn();
			}
			else if (cm.numNeighbors(c) >= 8 && c.isAlive()) {
				c.turnOff();
			}
			else if (cm.numFlatNeighbors(c) > 4  && c.isAlive()) {
				c.turnOff();
			}
		}

	},

	ZURG1{

		@Override
		public void apply(CellManager cm, Cell c) {
			if (cm.numNeighbors(c) <= 1 && c.isAlive()) {
				c.turnOff();
			}
			else if (cm.numNeighbors(c) >= 4 && cm.numNeighbors(c) <= 5 && c.getStage() == null) {
				c.turnOn();
			}
			else if (cm.numNeighbors(c) >= 5 && cm.numNeighbors(c) <= 5 && c.isAlive()) {
				c.turnOff();
			}
		}

	};


	public abstract void apply(CellManager cm, Cell c);

}
