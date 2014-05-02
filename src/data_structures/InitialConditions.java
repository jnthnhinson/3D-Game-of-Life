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
							c.turnOn();
							c.nextStage();

						}

					}
				}
			}
		}
	}, SOLID{

		@Override
		void initialize(CellManager cm) {
			Cell c;
			for (int x = 0; x < cm.getSize(); ++x ){
				for (int y = 0; y < cm.getSize(); y++){
					for (int z = 0; z < cm.getSize(); z++){
						c = cm.getCell(x, y, z);
						System.out.println(c);


						c.turnOn();
						c.nextStage();

					}
				}
			}
		}
	}, RANDOM{

		@Override
		void initialize(CellManager cm) {
			for(int x = 0; x < Math.pow(cm.getSize(), 3) * .5; x++){
				int[] coor = cm.generateCoor();
				Cell c = cm.getCell(coor[0], coor[1], coor[2]);
				c.turnOn();
				c.nextStage();
			}
			
		}
	}, SAMSINIT{

		@Override
		void initialize(CellManager cm) {
			Cell c;
			for (int x = 0; x < cm.getSize(); ++x ){
				for (int y = 0; y < cm.getSize(); y++){
					for (int z = 0; z < cm.getSize(); z++){
						c = cm.getCell(x, y, z);
						
						if (Math.round(Math.random()*20)==1) {
							System.out.println("made cell alive");
                            c.turnOn();
                            c.nextStage();
                        }
						
					}
				}
			}
		}
		
	};

	abstract void initialize(CellManager cm);

}
