package bingo;

public class BingoBoard {

	private final String[][] cells;
	private final boolean[][] marked;

	public BingoBoard(int width, int height) {
		this.cells = new String[width][height];
		this.marked = new boolean[width][height];
	}

	public void defineCell(int x, int y, String value) {
		if (cells[x][y] != null) {
			throw new IllegalStateException("cell already defined");
		}
		for (int c = 0; c < cells.length; c++) {
			for (int r = 0; r < cells[c].length; r++) {
				if (value.equals(cells[c][r]))
					throw new IllegalStateException(value + " already present at " + c + "," + r);
			}
		}
		cells[x][y] = value;
	}

	public void markCell(int x, int y) {
		if (!isInitialized()) {
			throw new IllegalStateException("board not initialized");
		}
		marked[x][y] = true;
	}

	public boolean isMarked(int x, int y) {
		return marked[x][y];
	}

	public boolean isInitialized() {
		for (String[] row : cells) {
			for (String col : row) {
				if (col == null)
					return false;
			}
		}
		return true;
	}

}