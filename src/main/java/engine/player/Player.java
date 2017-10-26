package engine.player;

import engine.board.Board;

public abstract class Player {
	
	protected final Board board;
	
	public Player(Board board) {
		
		this.board = board;
	}
}
