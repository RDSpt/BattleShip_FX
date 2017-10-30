package engine.board;

import com.google.common.collect.*;
import com.google.common.collect.Table;
import engine.ships.Ship;

import java.util.*;

public abstract class Tile {
	
	// ===== Variables ===== //
	public static final Map<Map.Entry<Integer, Integer>, EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
	protected int row;
	protected int col;
	protected int[] tileCoordinate;
	protected boolean shootted;
	// ===== Constructor ===== //
	
	public Tile(final int row, final int col, final boolean shootted) {
		
		this.row = row;
		this.col = col;
		this.shootted = shootted;
		setTileCoordinate(row, col);
	}
	
	private void setTileCoordinate(int row, int col) {
		
		this.tileCoordinate = new int[]{row, col};
	}
	
	public int[] getTileCoordinate() {
		
		return tileCoordinate;
	}
	
	// ===== Methods ===== //
	private static Map<Map.Entry<Integer, Integer>, EmptyTile> createAllPossibleEmptyTiles() {
		
		final Map<Map.Entry<Integer, Integer>, EmptyTile> emptyTileList = new HashMap<>();
		for (int row = 0; row < engine.board.BoardUtils.TILES_PER_ROW; row++) {
			for (int col = 0; col < engine.board.BoardUtils.TILES_PER_COL; col++) {
				Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry(row, col);
				emptyTileList.put(coordinate, new EmptyTile(row, col));
			}
		}
		return ImmutableMap.copyOf(emptyTileList);
	}
	
	public static Tile createTile(final int row, final int col, final Ship ship) {
		
		Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry(row, col);
		return ship != null ? new OccupiedTile(row, col, ship) : EMPTY_TILE_CACHE.get(coordinate);
	}
	
	public abstract boolean isTileOccupied();
	
	public abstract Ship getShip();
	
	public boolean isShootted() {
		
		return shootted;
	}
	
	public abstract boolean missed();
	// ===== EmptyTile CLASS ===== //
	
	private static class EmptyTile extends Tile {
		
		public EmptyTile(final int row, final int col) {
			
			super(row, col, false);
			
		}
		
		public boolean isTileOccupied() {
			
			return false;
		}
		
		public Ship getShip() {
			
			return null;
		}
		
		@Override
		public boolean missed() {
			
			return true;
		}
		
		@Override
		public String toString() {
			
			return "~";
		}
	}
	// ===== OccupiedTile CLASS ===== //
	
	private static class OccupiedTile extends Tile {
		
		final Ship shipOnTile;
		
		public OccupiedTile(final int row, final int col, final Ship ship) {
			
			super(row, col, false);
			this.shipOnTile = ship;
		}
		
		public boolean isTileOccupied() {
			
			return true;
		}
		
		public Ship getShip() {
			
			return shipOnTile;
		}
		
		@Override
		public boolean missed() {
			
			return false;
		}
		
		@Override
		public String toString() {
			
			return getShip().toString();
		}
	}
	
}
