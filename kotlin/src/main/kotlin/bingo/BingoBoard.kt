package bingo

class BingoBoard(width: Int, height: Int) {
    private val cells = Array(width) { arrayOfNulls<String>(height) }
    private val marked = Array(width) { BooleanArray(height) }

    fun defineCell(x: Int, y: Int, value: String) {
        check(cells[x][y] == null) { "cell already defined" }
        for (c in cells.indices) {
            for (r in cells[c].indices) {
                check(value != cells[c][r]) { "$value already present at $c,$r" }
            }
        }
        cells[x][y] = value
    }

    fun markCell(x: Int, y: Int) {
        check(isInitialized) { "board not initialized" }
        marked[x][y] = true
    }

    fun isMarked(x: Int, y: Int): Boolean {
        return marked[x][y]
    }

    val isInitialized: Boolean
        get() {
            for (row in cells) {
                for (col in row) {
                    if (col == null) return false
                }
            }
            return true
        }
}
