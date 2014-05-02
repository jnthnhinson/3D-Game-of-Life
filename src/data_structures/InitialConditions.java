package src.data_structures;

public enum InitialConditions {
	GRID{

		@Override
		void initialize(CellManager cm) {
			Cell c;
			for (int x = 0; x < cm.getSize(); ++x ){
				for (int y = 0; y < cm.getSize(); y++){
					for (int z = 0; z < cm.getSize(); z++){
						c = cm.getCell(x, y, z);
						System.out.println(c);

						if (y % 2 == 0 || z % 2 != 0 ) {
							c.setAlive(true);
						}
						
					}
				}
			}
		}
	};

					abstract void initialize(CellManager cm);

				}
