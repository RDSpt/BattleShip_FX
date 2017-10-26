package engine.board;

import com.google.common.collect.*;
import com.google.common.collect.Table;
import engine.ships.Ship;

import java.util.*;

public abstract class Tile {
	
	// ===== Variables ===== //
	private static final Table<Integer,Integer,EmptyTile> EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
	protected int row;
	protected int col;
	protected int[] tileCoordinate;
	// ===== Constructor ===== //
	
	public Tile(final int row, final int col) {
		
		this.row = row;
		this.col = col;
		setTileCoordinate(row, col);
	}
	
	private void setTileCoordinate(int row, int col) {
		
		this.tileCoordinate = new int[]{row, col};
	}
	
	public int[] getTileCoordinate() {
		
		return tileCoordinate;
	}
	
	// ===== Methods ===== //
	private static Table<Integer,Integer,EmptyTile> createAllPossibleEmptyTiles() {
		
		final Table<Integer,Integer,EmptyTile> emptyTileList = null;
		for (int row = 0; row < engine.board.BoardUtils.TILES_PER_ROW; row++) {
			for (int col = 0; col < engine.board.BoardUtils.TILES_PER_COL; col++) {
				emptyTileList.put(row,col,new EmptyTile(row, col));
			}
		}
		return ImmutableTable.copyOf(emptyTileList);
	}
	
	public static Tile createTile(final int row, final int col, final Ship ship) {
		
		return ship != null ? new OccupiedTile(row, col, ship) : EMPTY_TILE_CACHE.get(row, col);
	}
	
	public abstract boolean isTileOccupied();
	
	public abstract Ship getShip();
	// ===== EmptyTile CLASS ===== //
	
	private static class EmptyTile extends Tile {
		
		public EmptyTile(final int row, final int col) {
			
			super(row, col);
			
		}
		
		public boolean isTileOccupied() {
			
			return false;
		}
		
		public Ship getShip() {
			
			return null;
		}
	}
	// ===== OccupiedTile CLASS ===== //
	
	private static class OccupiedTile extends Tile {
		
		final Ship shipOnTile;
		
		public OccupiedTile(final int row, final int col, final Ship ship) {
			
			super(row, col);
			this.shipOnTile = ship;
		}
		
		public boolean isTileOccupied() {
			
			return false;
		}
		
		public Ship getShip() {
			
			return shipOnTile;
		}
	}
	
}
