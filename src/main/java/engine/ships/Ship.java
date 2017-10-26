package engine.ships;

import engine.player.Alliance;

public abstract class Ship {
	
	protected ShipType shipType;
	protected int[] shipPosition = new int[2];
	protected Alliance shipAlliance;
	
	public Ship(final ShipType shipType,
	            final int[] shipPosition,
	            final Alliance shipAlliance) {
		
		this.shipType = shipType;
		this.shipPosition = shipPosition;
		this.shipAlliance = shipAlliance;
	}
	
	public int[] getShipPosition() {
		
		return shipPosition;
	}
	
	public Alliance getShipAlliance() {
		
		return shipAlliance;
	}
	
	public Ship getShip() {
		
		return this;
	}
	
	public enum ShipType {
		CARRIER("CA"),
		BATTLESHIP("B"),
		CRUISE("CR"),
		SUBMARINE("S"),
		DESTROYER("D");
		
		private String shipName;
		
		ShipType(final String shipName) {
			
			this.shipName = shipName;
		}
		
		@Override
		public String toString() {
			
			return this.shipName;
		}
	}
}
