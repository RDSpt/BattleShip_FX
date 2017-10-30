package engine.ships;

import engine.player.Alliance;

public class Destroyer extends Ship{
	
	private static Destroyer allyInstance = null;
	private static Destroyer naziInstance = null;
	
	public static Destroyer getInstance(int[] shipPosition, Alliance shipAlliance, Boolean vertical) {
		
		if(allyInstance== null && shipAlliance.isAlly()){
			allyInstance = new Destroyer(shipPosition, shipAlliance, vertical);
			return allyInstance;
		}
		if(naziInstance== null && shipAlliance.isNazi()){
			naziInstance = new Destroyer(shipPosition, shipAlliance, vertical);
			return naziInstance;
		}
		return shipAlliance.isAlly() ? allyInstance : naziInstance;
	}
	
	private Destroyer(int[] initialCoordinate, Alliance shipAlliance, Boolean vertical) {
		super(ShipType.DESTROYER, initialCoordinate, shipAlliance, vertical);
	}
	
}
