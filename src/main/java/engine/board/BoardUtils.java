package engine.board;

import java.util.*;

public class BoardUtils {
	
	public static final int NUM_TILES = 100;
	public static final int TILES_PER_ROW = 10;
	public static final int TILES_PER_COL = 10;
	
	public static Map.Entry<Integer, Integer> coordinate(int row, int col) {
		
		return new AbstractMap.SimpleEntry<>(row, col);
	}
}
