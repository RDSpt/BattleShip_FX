package engine.ships;

import engine.player.Alliance;

public class Battleship extends Ship{ //Double Singleton depending the alliance
	
	private static Battleship allyInstance = null;
	private static Battleship naziInstance = null;
	
	public static Battleship getInstance(int[] shipPosition, Alliance shipAlliance, Boolean vertical) {
		
		if(allyInstance== null && shipAlliance.isAlly()){
			allyInstance = new Battleship(shipPosition, shipAlliance, vertical);
			return allyInstance;
		}
		if(naziInstance== null && shipAlliance.isNazi()){
			naziInstance = new Battleship(shipPosition, shipAlliance, vertical);
			return naziInstance;
		}
		return shipAlliance.isAlly() ? allyInstance : naziInstance;
	}
	
	private Battleship(int[] initialCoordinate, Alliance shipAlliance, Boolean vertical) {
		super(ShipType.BATTLESHIP, initialCoordinate, shipAlliance, vertical);
	}
}
