package bingo;

public class BingoBoard {

	private final String[][] cell;
	private final boolean[][] marked;

	public BingoBoard(int width, int height) {
		this.cell = new String[width][height];
		this.marked = new boolean[width][height];
	}

	public void defineCell(int x, int y, String value) {
		if (cell[x][y] != null) {
			throw new IllegalStateException("cell already defined");
		}
		for (int c = 0; c < cell.length; c++) {
			for (int r = 0; r < cell[c].length; r++) {
				if (value.equals(cell[c][r]))
					throw new IllegalStateException(value + " already present at " + c + "," + r);
			}
		}
		cell[x][y] = value;
	}

	public void markCell(int x, int y) {
		if (!isInitialzed()) {
			throw new IllegalStateException("board not initialized");
		}
		marked[x][y] = true;
	}

	public boolean isMarked(int x, int y) {
		return marked[x][y];
	}

	public boolean isInitialzed() {
		for (String[] row : cell) {
			for (String col : row) {
				if (col == null)
					return false;
			}
		}
		return true;
	}

}