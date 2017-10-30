package engine.ships;

import engine.player.Alliance;

import java.util.ArrayList;

public abstract class Ship {
	
	protected ShipType shipType;
	protected ArrayList<Integer> shipPosition = new ArrayList<>();
	protected Alliance shipAlliance;
	protected Boolean vertical;
	
	public Ship(final ShipType shipType,
	            final int[] initialCoordinate,
	            final Alliance shipAlliance,
	            final boolean vertical) {
		
		this.shipType = shipType;
		int j = 0;
		for (int i = 0; i < shipType.shipSize; i++) {
			if (vertical) {
				this.shipPosition.add(initialCoordinate[0] + j);
				this.shipPosition.add(initialCoordinate[1]);
			}
			else{
				this.shipPosition.add(initialCoordinate[0]);
				this.shipPosition.add(initialCoordinate[1] + j);
			}
			j++;
		}
		this.vertical = vertical;
		this.shipAlliance = shipAlliance;
	}
	
	public ArrayList<Integer> getShipPosition() {
		
		return shipPosition;
	}
	
	public Alliance getShipAlliance() {
		
		return shipAlliance;
	}
	
	public Boolean getVertical() {
		
		return vertical;
	}
	
	public Ship getShip() {
		
		return this;
	}
	
	public boolean isDestroyed() {
		
		return shipType.shipHealth == 0;
	}
	
	public boolean shipPartHit() {
		
		return true;/*TODO*/
	}
	
	public void shot() {
		
		if (shipPartHit()) {
			setShipHealth(shipType.shipHealth - 1);
		}
	}
	
	public void setShipHealth(int newValue) {
		
		this.shipType.shipHealth = newValue;
	}
	
	public int getShipSize() {
		
		return this.shipType.shipSize;
	}
	
	public int getShipHealth() {
		
		return this.shipType.shipHealth;
	}
	
	public ShipType getShipType(){
		return this.shipType;
	}
	
	@Override
	public String toString() {
		
		return shipAlliance == Alliance.ALLIES ? shipType.shipName.toUpperCase() : shipType.shipName.toLowerCase();
	}
	
	public enum ShipType {
		CARRIER("C", 5, 5),
		BATTLESHIP("B", 4, 4),
		CRUISE("U", 3, 3),
		SUBMARINE("S", 3, 3),
		DESTROYER("D", 2, 2);
		
		private String shipName;
		private int shipSize;
		private int shipHealth;
		
		ShipType(final String shipName, final int shipSize, final int shipHealth) {
			
			this.shipName = shipName;
			this.shipSize = shipSize;
			this.shipHealth = shipHealth;
		}
		
		public int getShipSize() {
			
			return this.shipSize;
		}
		
		public int getShipHealth() {
			
			return this.shipHealth;
		}
		
		
		@Override
		public String toString() {
			
			return this.shipName;
		}
	}
}
